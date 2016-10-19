/*
 * Copyright 2012-2014 the original author or authors.
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

package com.nnk.upstream.controller;

import com.alibaba.fastjson.JSONObject;
import com.nnk.interfacetemplate.http.utils.StringUtil;
import com.nnk.upstream.core.ConfigContextManager;
import com.nnk.upstream.core.MsgSrvService;
import com.nnk.upstream.entity.self.PageResult;
import com.nnk.upstream.entity.self.ProtoclType;
import com.nnk.upstream.entity.self.SlowIntProctol;
import com.nnk.upstream.entity.self.UpstreamSession;
import com.nnk.upstream.exception.NNKSQLException;
import com.nnk.upstream.handler.MessageHandler;
import com.nnk.upstream.service.InterfaceManager;
import com.nnk.upstream.service.RedisCacheManager;
import com.nnk.upstream.util.DateUtil;
import com.nnk.upstream.util.MsgSrvResponseUtils;
import com.nnk.upstream.util.SearchUtils;
import com.nnk.upstream.vo.InterfaceConfig;
import nnk.msgsrv.server.MsgSrvLongConnector;
import nnk.msgsrv.server.MsgSrvShortConnector;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class CoreController {
    private Logger logger = Logger.getLogger(CoreController.class);

    @Autowired
    private InterfaceManager manager;

    @Autowired
    private ConfigContextManager configContextManager;

    @Autowired
    private RedisCacheManager redisCacheManager;

    @Autowired
    private MessageHandler queryHandler;

    @RequestMapping(value = "/index", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView index(HttpServletRequest request, HttpSession session) {

        return listAllpage(request,1L);
    }

    @RequestMapping(value = "/updateConfig", method = {RequestMethod.POST})
    public ModelAndView updateConfig(HttpServletRequest request, HttpServletResponse response, InterfaceConfig config) {

        ModelAndView modelAndView = new ModelAndView("index");
        try {
            manager.insertConfig(config);
            modelAndView.addObject("status", "insert success");
            modelAndView.addObject("pageType","success");
        } catch (NNKSQLException e) {
            modelAndView.addObject("status", "insert error");
            modelAndView.addObject("pageType","success");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/listAll")
    public ModelAndView listAllpage(HttpServletRequest request, @RequestParam(value = "currentPage") Long currentPage) {

        ModelAndView modelAndView = new ModelAndView("index");
        try {
            logger.info("currentPage:" + currentPage);
            PageResult pageResult = new PageResult();
            if (StringUtils.isEmpty(currentPage)) {
                currentPage = 1L;
            }
            pageResult.setCurrentPage(currentPage);
            pageResult.setSize(15L);
            List<InterfaceConfig> list = manager.findAllpage(pageResult.getCurrentPage(), pageResult.getSize());
            logger.debug("list.size" + list.size());
            pageResult.setList(list);
            long count = manager.count();
            pageResult.setCount(count);
            //如果当前页等于第一页，则上一页就是第一页，否则为当前页上一页
            pageResult.setPrePage(pageResult.getPrePage());
            //如果当前页是尾页，则下一页就是尾页，否则为当前页的下一页
            pageResult.setNextPage(pageResult.getNextPage());
            pageResult.setCount(count);
            pageResult.setTotalPage(pageResult.getTotalPage());
            modelAndView.addObject("pageResult", pageResult);
            modelAndView.addObject("pageType","list");
        } catch (NNKSQLException e) {

        }
        return modelAndView;
    }

    @RequestMapping(value = "/find")
    public ModelAndView list(HttpServletRequest request, @RequestParam(value = "interfacename") String interfacename, @RequestParam(value = "merchantno") String merchantno) {
        ModelAndView modelAndView = new ModelAndView("index");
        try {
            logger.debug("merchartno:" + merchantno);
            List<InterfaceConfig> list = manager.find(merchantno, interfacename);
            PageResult pageResult = new PageResult();
            pageResult.setList(list);
            pageResult.setSize(15L);
            pageResult.setCurrentPage(1L);
            pageResult.setCount(Long.valueOf(list.size()));
            logger.debug("list.size:" + list.size());
            //如果当前页等于第一页，则上一页就是第一页，否则为当前页上一页
            pageResult.setPrePage(pageResult.getPrePage());
            //如果当前页是尾页，则下一页就是尾页，否则为当前页的下一页
            pageResult.setNextPage(pageResult.getNextPage());
            pageResult.setCount(Long.valueOf(list.size()));
            pageResult.setTotalPage(pageResult.getTotalPage());
            modelAndView.addObject("pageResult", pageResult);
            modelAndView.addObject("pageType","list");
        } catch (NNKSQLException e) {

        }
        return modelAndView;
    }

    @RequestMapping(value = "/edit")
    public ModelAndView edit(HttpServletRequest request, @RequestParam(value = "cmd") String cmd, @RequestParam(value = "id") Integer id) {
        try {
            if ("remove".equals(cmd)) {
                boolean ret = manager.delete(id);
                ModelAndView modelAndView = new ModelAndView("index");
                modelAndView.addObject("pageType","success");
                if (ret) {
                    modelAndView.addObject("status", "delete success");
                } else {
                    modelAndView.addObject("status", "delete error");
                }
                return modelAndView;
            } else if ("edit".equals(cmd)) {
                ModelAndView modelAndView = new ModelAndView("index");
                InterfaceConfig config = manager.selectByPrimaryKey(id);
                modelAndView.addObject("show","edit");
                modelAndView.addObject("config", config);
                modelAndView.addObject("pageType","edit");
                return modelAndView;
            }else if("view".equals(cmd)){
                ModelAndView modelAndView = new ModelAndView("index");
                InterfaceConfig config = manager.selectByPrimaryKey(id);
                modelAndView.addObject("pageType","edit");
                modelAndView.addObject("show","readonly");
                modelAndView.addObject("config", config);

                return modelAndView;
            }
        } catch (NNKSQLException e) {
        }
        return null;
    }

    @RequestMapping(value = "/update")
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response, InterfaceConfig interfaceConfig) {
        ModelAndView modelAndView = new ModelAndView("index");
        try {
            boolean ret = manager.update(interfaceConfig);
            if (ret) {
                configContextManager.updateConfigContext(interfaceConfig.getMerchantno());
                Long size  = redisCacheManager.mapSize(RedisCacheManager.INSTANCEMAP);
                if(size!=1L) {
                    while (size >= 0) {
                        MsgSrvShortConnector connector = new MsgSrvShortConnector("config/msgsrv-short.xml");
                        MsgSrvLongConnector context = MsgSrvService.get("start");
                        connector.send(context.getConnector().getConf().getAppName() + " " + "UpdateContext" + " " + interfaceConfig.getMerchantno());
                        size--;
                    }
                }
                modelAndView.addObject("status", "update success");
            } else {
                modelAndView.addObject("status", "update fail");
            }

        } catch (NNKSQLException e) {
            modelAndView.addObject("status", "update error!");
        }
        modelAndView.addObject("pageType","success");
        return modelAndView;
    }

    @RequestMapping(value = "/insert")
    public ModelAndView insert(HttpServletRequest request, HttpServletResponse response, InterfaceConfig interfaceConfig) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("pageType","insert");
        return modelAndView;
    }

    @RequestMapping(value = "/findwaiting")
    public ModelAndView listwaiting(HttpServletRequest request, @RequestParam(value = "currentPage") Integer currentPage) {
        logger.error(currentPage);
        if (currentPage == null) {
            currentPage = 1;
        }
        List<Object> list = redisCacheManager.listValues(RedisCacheManager.WAITTINGMAP);
        List<Object> sublist = null;
        if (currentPage * 30 < list.size()) {
            sublist = list.subList((currentPage - 1) * 30, currentPage * 30);
        } else {
            sublist = list.subList((currentPage - 1) * 30, list.size());
        }
        long cout = redisCacheManager.mapSize(RedisCacheManager.WAITTINGMAP);
        PageResult pageResult = listPageResult(sublist, Long.valueOf(currentPage), 40L, cout);
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("pageResult", pageResult);
        modelAndView.addObject("pageType","waitlist");
        return modelAndView;
    }

    @RequestMapping(value = "/findwait")
    public ModelAndView listwait(HttpServletRequest request, String merchantno, String sendorderid, String status, String starttime, String endtime) throws ParseException {
        logger.info("status:" + status);
        List list = redisCacheManager.listValues(RedisCacheManager.WAITTINGMAP);
        ModelAndView modelAndView = new ModelAndView("index");
        List<SlowIntProctol> desList = new ArrayList<SlowIntProctol>();
        if (!StringUtils.isEmpty(sendorderid)) {
            logger.debug("sendorderid is" + sendorderid);
            SlowIntProctol slowIntProctol = (SlowIntProctol) redisCacheManager.mapGet(RedisCacheManager.WAITTINGMAP, sendorderid);
            if (null != slowIntProctol) {
                desList.add(slowIntProctol);
            }
            modelAndView.addObject("pageResult", desList);
            modelAndView.addObject("pageType","waitlistdetail");
            return modelAndView;
        }
        Date starttime1 = null;
        Date endtime1 = null;
        if (!StringUtils.isEmpty(starttime)) {
            logger.error("startime is" + starttime);
            starttime1 = DateUtil.parse(starttime, "yyyy-MM-dd HH:mm:ss");
        }
        if (!StringUtils.isEmpty(endtime)) {
            logger.warn("endtime is" + endtime);
            endtime1 = DateUtil.parse(endtime, "yyyy-MM-dd HH:mm:ss");
        }
        desList = SearchUtils.findByAll(list, merchantno, status, starttime1, endtime1);
        modelAndView.addObject("pageResult", desList);
        modelAndView.addObject("pageType","waitlistdetail");
        return modelAndView;
    }

    @RequestMapping(value = "/notifyQuery")
    @ResponseBody
    public String notifyQuery(HttpServletRequest request, String sendorderids) throws ParseException {
        logger.info("sendorderids:" + sendorderids);
        String[] sendorderidArray = sendorderids.split(",");
        List<SlowIntProctol> list = redisCacheManager.muiltGet(RedisCacheManager.WAITTINGMAP, Arrays.asList(sendorderidArray));
        for (SlowIntProctol slowIntProctol : list) {
            if (slowIntProctol.getContents() != null) {
                UpstreamSession upstreamSession = new UpstreamSession("IFTran", "SlowInt", StringUtil.transitionProtocol(slowIntProctol.getContents())
                        , System.currentTimeMillis(), ProtoclType.QUERY);
                queryHandler.handlerRequest(upstreamSession);
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 1);
        jsonObject.put("content", "success");

        return jsonObject.toString();
    }

    @RequestMapping(value = "/backtoSystem")
    @ResponseBody
    public String backtoSystem(HttpServletRequest request, String sendorderids) throws ParseException {
        logger.info("sendorderids:" + sendorderids);
        String[] sendorderidArray = sendorderids.split(",");
        List<SlowIntProctol> list = redisCacheManager.muiltGet(RedisCacheManager.WAITTINGMAP, Arrays.asList(sendorderidArray));
        for (SlowIntProctol slowIntProctol : list) {
            if (slowIntProctol.getContents() != null) {
                UpstreamSession upstreamSession = new UpstreamSession("IFTran", "SlowInt", StringUtil.transitionProtocol(slowIntProctol.getContents())
                        , System.currentTimeMillis(), ProtoclType.QUERY);
                MsgSrvResponseUtils.responseBrokeFail(upstreamSession);
            }
        }
        return removeOrderFromRedis(sendorderidArray);
    }

    private String removeOrderFromRedis(String[] sendorderidArray) {
        Long ret = redisCacheManager.removeKeys(RedisCacheManager.WAITTINGMAP, sendorderidArray);
        JSONObject jsonObject = new JSONObject();
        if(0L < ret){

            jsonObject.put("status", 1);
            jsonObject.put("content", "success");
        }else{
            jsonObject.put("status", 1);
            jsonObject.put("content", "fail");
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/setFail")
    @ResponseBody
    public String setFail(HttpServletRequest request, String sendorderids) throws ParseException {
        logger.info("sendorderids:" + sendorderids);
        String[] sendorderidArray = sendorderids.split(",");
        List<SlowIntProctol> list = redisCacheManager.muiltGet(RedisCacheManager.WAITTINGMAP, Arrays.asList(sendorderidArray));
        for (SlowIntProctol slowIntProctol : list) {
            if (slowIntProctol.getContents() != null) {
                UpstreamSession upstreamSession = new UpstreamSession("IFTran", "SlowInt", StringUtil.transitionProtocol(slowIntProctol.getContents())
                        , System.currentTimeMillis(), ProtoclType.QUERY);
                MsgSrvResponseUtils.responseCallbackFail(upstreamSession, "0", "NA", DateUtil.format(new Date()),"成功");
            }
        }
        return removeOrderFromRedis(sendorderidArray);
    }

    @RequestMapping(value = "/deleteOrders")
    @ResponseBody
    public String deleteOrders(HttpServletRequest request, String sendorderids) throws ParseException {
        logger.info("sendorderids:" + sendorderids);
        String[] sendorderidArray = sendorderids.split(",");
        JSONObject jsonObject = new JSONObject();

        return removeOrderFromRedis(sendorderidArray);
    }
    @RequestMapping(value = "/deleteOrder")
    public ModelAndView deleteOrder(HttpServletRequest request, String sendorderids) throws ParseException {
        logger.info("sendorderids:" + sendorderids);
        String[] sendorderidArray = sendorderids.split(",");
        ModelAndView modelAndView = new ModelAndView("index");
        Long ret = redisCacheManager.removeKeys(RedisCacheManager.WAITTINGMAP, sendorderidArray);
        logger.info("ret:" + ret);
        if( 0L < ret){

            modelAndView.addObject("status", "delete success");
            modelAndView.addObject("pageType", "success");
        }else{
            modelAndView.addObject("status", "delete fail");
            modelAndView.addObject("pageType", "success");
        }
        return modelAndView;
    }
    @RequestMapping(value = "/interfaceTest")
    public ModelAndView interfaceTest(HttpServletRequest request, String sendorderids) throws ParseException {

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("pageType", "test");
        return modelAndView;
    }
    private PageResult listPageResult(List list, Long currentPage, Long size, Long count) {
        PageResult pageResult = new PageResult();
        pageResult.setList(list);
        pageResult.setSize(size);
        pageResult.setCurrentPage(currentPage);

        pageResult.setCount(count);
        logger.debug("list.size:" + list.size());
        //如果当前页等于第一页，则上一页就是第一页，否则为当前页上一页
        pageResult.setPrePage(pageResult.getPrePage());
        //如果当前页是尾页，则下一页就是尾页，否则为当前页的下一页
        pageResult.setNextPage(pageResult.getNextPage());
        pageResult.setCount(count);
        pageResult.setTotalPage(pageResult.getTotalPage());
        pageResult.setFirstPage(1L);
        return pageResult;
    }

}
