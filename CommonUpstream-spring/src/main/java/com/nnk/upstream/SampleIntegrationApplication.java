/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nnk.upstream;

import com.nnk.upstream.core.ConfigContextManager;
import com.nnk.upstream.core.MsgSrvService;
import com.nnk.upstream.service.RedisCacheManager;
import com.nnk.upstream.util.DateUtil;
import com.nnk.upstream.util.SpringContextUtils;
import nnk.msgsrv.server.MsgSrvLongConnector;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
@ComponentScan("com.nnk.upstream")
@EnableAutoConfiguration
@ImportResource("classpath:/context/bean.xml")
@ServletComponentScan
public class SampleIntegrationApplication extends SpringBootServletInitializer {
    public static final Logger LOGGER = Logger.getLogger(SampleIntegrationApplication.class);
    public static String IntanceKey;
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SampleIntegrationApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleIntegrationApplication.class, args);
        //程序配置上下文
        ConfigContextManager configContextManager = (ConfigContextManager) SpringContextUtils.getBean("contextManager");
        configContextManager.start();
        MsgSrvLongConnector connector = new MsgSrvLongConnector("config/msgsrv.xml");
        connector.start();
        MsgSrvService.put("start", connector);

        RedisCacheManager redisCacheManager = (RedisCacheManager) SpringContextUtils.getBean("redisCacheManager");
        Long size = redisCacheManager.mapSize(RedisCacheManager.INSTANCEMAP);
        IntanceKey = "instance" + new Random().nextInt(1000) + size;
        LOGGER.info("程序已启动实例数：" + size + "份");
        String instanceValue = IntanceKey.concat(",").concat(DateUtil.format(new Date()));
        redisCacheManager.add2MapExpire(RedisCacheManager.INSTANCEMAP, IntanceKey, instanceValue,60,TimeUnit.SECONDS);
        LOGGER.info("程序当前启动实例编号:" + IntanceKey );
        InstancePluse instancePluse = new InstancePluse(redisCacheManager,IntanceKey);//用于对注册中心的维护
        instancePluse.start();
    }

    private static class InstancePluse implements Runnable{
        private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        private RedisCacheManager redisCacheManager;
        private String instance;
        private InstancePluse(RedisCacheManager redisCacheManager,String instance) {
            this.redisCacheManager = redisCacheManager;
            this.instance = instance;
        }

        @Override
        public void run() {
            try {
                String instanceValue = (String) redisCacheManager.mapGet(RedisCacheManager.INSTANCEMAP,instance);
                String content[] = instanceValue.split(",");
                String newInstanceValue = content[0].concat(",").concat(DateUtil.format(new Date()));
                redisCacheManager.add2MapExpire(RedisCacheManager.INSTANCEMAP,instance,newInstanceValue,45,TimeUnit.SECONDS);
                for(Object key: redisCacheManager.listKeys(RedisCacheManager.INSTANCEMAP)){
                    String value = (String) redisCacheManager.mapGet(RedisCacheManager.INSTANCEMAP, (String) key);
                    String[] valueContents = value.split(",");
                    Date inputDate= DateUtil.parse(valueContents[1],"yyyyMMddHHmmss");
                    if(DateUtil.isTimeout(inputDate,50)){
                        redisCacheManager.removeKeys(RedisCacheManager.INSTANCEMAP, new String[]{(String) key});
                        LOGGER.debug("当前实例：" + key +"失效");
                    }
                }
            }catch (Exception e){
            }
        }
        public void start(){
            executorService.scheduleAtFixedRate(this,0,30, TimeUnit.SECONDS);
        }
    }
}
