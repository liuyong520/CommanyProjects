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
     * ���һ����ʱ����ʹ��Ĭ�ϵ�������������������������������
     *
     * @param jobName
     *            ������
     * @param jobClass
     *            ����
     * @param startime
     *            ʱ�����ã�������֮��ִ��
     * @param endtime
     *            ����ʱ��
     * @param repeat
     *            �ظ�����
     * @param  repeatDelay
     *            �ظ����
     * @throws org.quartz.SchedulerException
     * @throws java.text.ParseException
     */
    public static void addJob(String jobName, String jobClass, String startime,String endtime,int repeat,long repeatDelay,JobDataMap jobDataMap) {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, Class.forName(jobClass));// �������������飬����ִ����
            jobDetail.setJobDataMap(jobDataMap);
            jobDetail.setDurability(true);
            // ������
            SimpleTrigger  trigger = new SimpleTrigger (jobName, TRIGGER_GROUP_NAME);// ��������,��������
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
            // ����
            if (!sched.isShutdown()){
                sched.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }





    /**
     * �Ƴ�һ������(ʹ��Ĭ�ϵ�������������������������������)
     *
     * @param jobName
     */
    public static void removeJob(String jobName) {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            sched.pauseTrigger(jobName, TRIGGER_GROUP_NAME);// ֹͣ������
            sched.unscheduleJob(jobName, TRIGGER_GROUP_NAME);// �Ƴ�������
            sched.deleteJob(jobName, JOB_GROUP_NAME);// ɾ������
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * �Ƴ�һ������
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
            sched.pauseTrigger(triggerName, triggerGroupName);// ֹͣ������
            sched.unscheduleJob(triggerName, triggerGroupName);// �Ƴ�������
            sched.deleteJob(jobName, jobGroupName);// ɾ������
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * �������ж�ʱ����
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
     * �ر����ж�ʱ����
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
