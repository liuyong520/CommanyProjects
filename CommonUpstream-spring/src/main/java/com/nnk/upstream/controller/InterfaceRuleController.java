package com.nnk.upstream.controller;

import com.alibaba.fastjson.JSONObject;
import com.nnk.interfacetemplate.common.XmlUtil;
import com.nnk.upstream.core.ConfigContextManager;
import com.nnk.upstream.core.MsgSrvService;
import com.nnk.upstream.entity.mapping.Mapdata;
import com.nnk.upstream.entity.mapping.XmlModel;
import com.nnk.upstream.entity.self.PageResult;
import com.nnk.upstream.exception.NNKSQLException;
import com.nnk.upstream.service.InterfaceManager;
import com.nnk.upstream.service.RedisCacheManager;
import com.nnk.upstream.vo.InterfaceRule;
import nnk.msgsrv.server.MsgSrvLongConnector;
import nnk.msgsrv.server.MsgSrvShortConnector;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/5/23
 * Time: 14:56
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class InterfaceRuleController {
    private Logger logger = Logger.getLogger(InterfaceRuleController.class);
    @Autowired
    private InterfaceManager interfaceManager;

    @Autowired
    private ConfigContextManager configContextManager;

    @Autowired
    private RedisCacheManager redisCacheManager;

    @RequestMapping(value = "/insertInterfaceRule")
    public ModelAndView insertInterfaceRule(HttpServletRequest request){
        System.out.println("insertInterfaceRule");
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("pageType","insertInterfaceRule");
        return modelAndView;
    }

    @RequestMapping(value = "/xmlUtils")
    public ModelAndView xmlUtils(HttpServletRequest request){
        System.out.println("insertInterfaceRule");
        ModelAndView modelAndView = new ModelAndView("/interfaceRule/xmlCreate");
//        modelAndView.addObject("pageType","insertInterfaceRule");
        return modelAndView;
    }

    @RequestMapping(value = "/xmlCreate")
    public ModelAndView xmlCreate(XmlModel xmlModel) throws JAXBException, IOException {
        System.out.println("xmlCreate: " + xmlModel);
        ModelAndView modelAndView = new ModelAndView("/interfaceRule/xmlCreate");
        String siqnsquence = xmlModel.getSignSquence();
        String[] array = siqnsquence.split(",");
        StringBuilder stringBuilder = new StringBuilder();
        for(String str:array){
            stringBuilder.append(str).append("=").append("$"+str).append("@");
        }
        String str = stringBuilder.toString();
        String signFormart = str.substring(0,str.length()-1);
        xmlModel.setSignFormart(signFormart);
        List<Mapdata> mapdataList = xmlModel.getMapdata();
        StringBuilder stringBuilder1 = new StringBuilder();
        if(xmlModel.getProtoclType().equals("JSON")){
            JSONObject jsonObject = new JSONObject();
            if(mapdataList.size()!=0) {
                for (Mapdata mapdata : mapdataList) {
                    if(mapdata.getType().equals("Password")){
                        continue;
                    }
                    jsonObject.put(mapdata.getName(), "$" + mapdata.getName());
                }
                xmlModel.setProtocl(jsonObject.toJSONString());
            }

        }else if(xmlModel.getProtoclType().equals("XML")){

            for(Mapdata mapdata:mapdataList){
                if(mapdata.getType().equals("Password")){
                    continue;
                }
                stringBuilder1.append("<" + mapdata.getName()+">").append("$" + mapdata.getName()).append("</" + mapdata.getName()+">");
            }
            xmlModel.setProtocl(stringBuilder1.toString());
        }else if(xmlModel.getProtoclType().equals("STR")){
            for(Mapdata mapdata:mapdataList){
                if(mapdata.getType().equals("Password")){
                    continue;
                }
                stringBuilder1.append(mapdata.getName()).append("=").append("$" + mapdata.getName()).append("@");
            }
            xmlModel.setProtocl(stringBuilder1.toString().substring(0,stringBuilder1.length()-1));
        }
        modelAndView.addObject("result", XmlUtil.toXml(xmlModel));
        return modelAndView;
    }
    @RequestMapping(value = "/insertProtcol")
    public ModelAndView insertProtcol(HttpServletRequest request,HttpServletResponse response,InterfaceRule rule){

        ModelAndView modelAndView = new ModelAndView("index");

        try {

            interfaceManager.insertInterfaceRule(rule);
            modelAndView.addObject("status", "insert success");
            modelAndView.addObject("pageType","success");
        } catch (Exception e) {
            modelAndView.addObject("status", "insert error");
            modelAndView.addObject("pageType", "success");
            e.printStackTrace();
        }


        return modelAndView;
    }
    @RequestMapping(value = "/listInterfaceRule")
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
            List<InterfaceRule> list = interfaceManager.findAllRulepage(pageResult.getCurrentPage(), pageResult.getSize());
            logger.debug("list.size" + list.size());
            pageResult.setList(list);
            long count = interfaceManager.countRule();
            pageResult.setCount(count);
            //如果当前页等于第一页，则上一页就是第一页，否则为当前页上一页
            pageResult.setPrePage(pageResult.getPrePage());
            //如果当前页是尾页，则下一页就是尾页，否则为当前页的下一页
            pageResult.setNextPage(pageResult.getNextPage());
            pageResult.setCount(count);
            pageResult.setTotalPage(pageResult.getTotalPage());
            modelAndView.addObject("pageResult", pageResult);
            modelAndView.addObject("pageType","listAllRules");
        } catch (NNKSQLException e) {

        }
        return modelAndView;
    }


    @RequestMapping(value = "/editrule")
    public ModelAndView edit(HttpServletRequest request, @RequestParam(value = "cmd") String cmd, @RequestParam(value = "id") Integer id) {
        try {
            if ("remove".equals(cmd)) {
                boolean ret = interfaceManager.deleteRule(id);
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
                InterfaceRule rule = interfaceManager.selectRuleByPrimaryKey(id);
                System.out.println(rule);
                modelAndView.addObject("show","edit");
                modelAndView.addObject("rule", rule);
                modelAndView.addObject("pageType","editrule");
                return modelAndView;
            }else if("view".equals(cmd)){
                ModelAndView modelAndView = new ModelAndView("index");
                InterfaceRule rule = interfaceManager.selectRuleByPrimaryKey(id);
                logger.info(rule);
                modelAndView.addObject("pageType","editrule");
                modelAndView.addObject("show","readonly");
                modelAndView.addObject("rule", rule);
                return modelAndView;
            }
        } catch (NNKSQLException e) {
        }
        return null;
    }

    @RequestMapping(value = "/updaterule")
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response, InterfaceRule rule) {
        ModelAndView modelAndView = new ModelAndView("index");
        try {
            boolean ret = interfaceManager.updateRule(rule);
            if (ret) {
                configContextManager.updateRuleContext(rule.getMerchantno());
                Long size  = redisCacheManager.mapSize(RedisCacheManager.INSTANCEMAP);
                if(size!=1L) {
                    while (size >= 0) {
                        MsgSrvShortConnector connector = new MsgSrvShortConnector("config/msgsrv-short.xml");
                        MsgSrvLongConnector context = MsgSrvService.get("start");
                        connector.send(context.getConnector().getConf().getAppName() + " " + "UpdateContext" + " " + rule.getMerchantno());
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
    @RequestMapping(value = "/findRule")
    public ModelAndView list(HttpServletRequest request, @RequestParam(value = "merchantno") String merchantno) {
        ModelAndView modelAndView = new ModelAndView("index");
        try {
            logger.debug("merchartno:" + merchantno);
            List<InterfaceRule> list = interfaceManager.findRule(merchantno);
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
            modelAndView.addObject("pageType","listAllRules");
        } catch (NNKSQLException e) {

        }
        return modelAndView;
    }
}
