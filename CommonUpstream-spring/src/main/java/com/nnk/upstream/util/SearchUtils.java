package com.nnk.upstream.util;

import com.nnk.upstream.entity.self.SlowIntProctol;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/20
 * Time: 16:43
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class SearchUtils {

    //根据代理商编号查询
    public static List<SlowIntProctol> findByMerid(List<SlowIntProctol> list,String merid){
        List<SlowIntProctol> meridlist = new ArrayList<SlowIntProctol>();

        for (SlowIntProctol slowIntProctol : list){
            if(slowIntProctol.getMerid().equals(merid)){
                meridlist.add(slowIntProctol);
            }
        }
        return  meridlist;
    }

    /**
     * search list fiter
     * @param list
     * @param merid
     * @param status
     * @param starttime
     * @param endtime
     * @return
     * @throws ParseException
     */
    public static List<SlowIntProctol> findByAll(List<SlowIntProctol> list,String merid,String status,Date starttime,Date endtime) throws ParseException {
        if(StringUtils.isEmpty(merid)){
            merid = "";
        }if(StringUtils.isEmpty(status)){
            status = "";
        }if(starttime == null){
            starttime =  DateUtil.parse(DateUtil.format(new Date(),"yyyyMMdd"),"yyyyMMdd");
        }if(endtime == null){
            endtime = new Date();
        }

        List<SlowIntProctol> allList = new ArrayList<SlowIntProctol>();
        for (SlowIntProctol slowIntProctol : list){
            if("".equals(merid)){
                if("".equals(status)) {
                    if (DateUtil.Isbetween(slowIntProctol.getOrdertime(), starttime, endtime)) {
                        allList.add(slowIntProctol);
                    }
                }else{
                    boolean ret = DateUtil.Isbetween(slowIntProctol.getOrdertime(), starttime, endtime);
                    if (DateUtil.Isbetween(slowIntProctol.getOrdertime(), starttime, endtime) &&
                            slowIntProctol.getSendStatus()!=null
                            &&status.equals(slowIntProctol.getSendStatus().getIndex())) {
                        allList.add(slowIntProctol);
                    }
                }
            }else{
                if("".equals(status)) {
                    if (DateUtil.Isbetween(slowIntProctol.getOrdertime(), starttime, endtime)&& slowIntProctol.getMerid().equals(merid)) {
                        allList.add(slowIntProctol);
                    }
                }else {
                    if (DateUtil.Isbetween(slowIntProctol.getOrdertime(), starttime, endtime)
                            &&slowIntProctol.getSendStatus()!=null
                            &&status.equals(slowIntProctol.getSendStatus().getIndex())
                            && slowIntProctol.getMerid().equals(merid)) {
                        allList.add(slowIntProctol);
                    }
                }
            }
        }
        return  allList;
    }
    //根据状态查询
    public static List<SlowIntProctol> findByStatus(List<SlowIntProctol>list,String stauts){
        List<SlowIntProctol> statusList = new ArrayList<SlowIntProctol>();
        for (SlowIntProctol slowIntProctol : list){
            if(slowIntProctol.getMerid().equals(stauts)){
                statusList.add(slowIntProctol);
            }
        }
        return statusList;
    }
    //根据时间查询
    public static List<SlowIntProctol> findByTime(List<SlowIntProctol>list,Date startime,Date endtime){
        List<SlowIntProctol> timeList = new ArrayList<SlowIntProctol>();
        for (SlowIntProctol slowIntProctol : list){
           if(DateUtil.Isbetween(slowIntProctol.getOrdertime(),startime,endtime)){
               timeList.add(slowIntProctol);
           }
        }
        return timeList;
    }

}
