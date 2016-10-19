package com.nnk.template.util;

import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/9/20
 * Time: 17:02
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class SimpleQuarzManager {
    private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
    private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
    private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";
    /**
     * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     *
     * @param jobName
     *            任务名
     * @param jobClass
     *            任务
     * @param startime
     *            时间设置，多少秒之后执行
     * @param endtime
     *            结束时间
     * @param repeat
     *            重复次数
     * @param  repeatDelay
     *            重复间隔
     * @throws org.quartz.SchedulerException
     * @throws java.text.ParseException
     */
    public static void addJob(String jobName, String jobClass, String startime,String endtime,int repeat,long repeatDelay,JobDataMap jobDataMap) {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, Class.forName(jobClass));// 任务名，任务组，任务执行类
            jobDetail.setJobDataMap(jobDataMap);
            jobDetail.setDurability(true);
            // 触发器
            SimpleTrigger  trigger = new SimpleTrigger (jobName, TRIGGER_GROUP_NAME);// 触发器名,触发器组
            long now = System.currentTimeMillis();
            trigger.setStartTime(new Date(now + Long.parseLong(startime)));
            if(StringUtils.isNotEmpty(endtime)){
                trigger.setEndTime(new Date(now + Long.parseLong(endtime)));
            }
            if(repeat>=0){
               trigger.setRepeatCount(repeat);
            }else {
                trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
            }
            if(repeatDelay>=0){
                trigger.setRepeatInterval(repeatDelay);
            }else{
                trigger.setRepeatInterval(0);
            }
            sched.scheduleJob(jobDetail, trigger);
            // 启动
            if (!sched.isShutdown()){
                sched.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }





    /**
     * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
     *
     * @param jobName
     */
    public static void removeJob(String jobName) {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            sched.pauseTrigger(jobName, TRIGGER_GROUP_NAME);// 停止触发器
            sched.unscheduleJob(jobName, TRIGGER_GROUP_NAME);// 移除触发器
            sched.deleteJob(jobName, JOB_GROUP_NAME);// 删除任务
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 移除一个任务
     *
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     */
    public static void removeJob(String jobName, String jobGroupName,
                                 String triggerName, String triggerGroupName) {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            sched.pauseTrigger(triggerName, triggerGroupName);// 停止触发器
            sched.unscheduleJob(triggerName, triggerGroupName);// 移除触发器
            sched.deleteJob(jobName, jobGroupName);// 删除任务
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 启动所有定时任务
     */
    public static void startJobs() {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            sched.start();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭所有定时任务
     */
    public static void shutdownJobs() {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            if(!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
