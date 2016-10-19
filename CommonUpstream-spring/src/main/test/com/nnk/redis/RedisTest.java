package com.nnk.redis;

import com.nnk.interfacetemplate.common.MD5Util;
import com.nnk.upstream.entity.self.SlowIntProctol;
import com.nnk.upstream.service.RedisCacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/20
 * Time: 10:29
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:context/*.xml" })
public class RedisTest {
    private String REVERSE_KEY = "batchJob:task_";
    @Autowired
    private RedisCacheManager redisCacheManager;
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void testManager(){
//        redisCacheManager.add2List("liuy",new String[]{"dsjfdkjsfkd","dstedsafea"});
        redisCacheManager.add2List("等待",new SlowIntProctol("0 12743499449843 2000000008 200000000000013 2010052016304839 1 5000 105 广西 22 NA NA 13267191379 20160414163900 NA 成都"));
        System.out.println(redisCacheManager.getListValues("等待",1,10));
//        System.out.println(redisCacheManager.getAllListValues("liuy"));
//        System.out.println(redisCacheManager.getListsize("liuy"));
        System.out.println(redisCacheManager.popList("等待"));
//        System.out.println(redisCacheManager.getAllListValues("liuy"));
//        BoundListOperations listOperations = redisTemplate.boundListOps("liuy");
//        listOperations.leftPush("32433");
//        listOperations.leftPushAll(new String[]{"2313444","32323"});
//        System.out.println(listOperations.range(1, 10));

    }
    @Test
    public void testRedisTmplate(){
//        BoundListOperations listOperations = redisTemplate.boundListOps("liuy");
//        listOperations.leftPushAll("2","32","32433");
//        System.out.println(listOperations.range(1,10));
        String key = REVERSE_KEY+"_testMap";
//        Map<String, String> newMap = new HashMap<String, String>();
//        redisTemplate.boundHashOps(key).putAll(newMap);
        BoundHashOperations<String, Object, Object> testMap = redisTemplate.boundHashOps(key);
        testMap.put("user2", "213214132");
        testMap.persist();


    }


}
