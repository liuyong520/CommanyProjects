package com.nnk.template.util;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

	// 时间定制格式"yyyy-MM-dd HH:mm:ss"
	private static SimpleDateFormat bartDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private static Logger log = Logger.getLogger(DateUtils.class);
	/**
	 * 将DateString转换成指定格式的DataString
	 * @param srcDateFormatString
	 * @param tarDateFormatString
	 * @param dataString
	 * @return
	 */
	public static String tranferDateFormat(String srcDateFormatString, String tarDateFormatString, String dataString) {
		SimpleDateFormat srcDateFormat = new SimpleDateFormat(srcDateFormatString, Locale.CHINA);
		Date date = null;
		try {
			date = srcDateFormat.parse(dataString);
		} catch (ParseException e) {
		}
		return DateUtils.parseDate(tarDateFormatString, date);
	}
    /**
     * 根据给定时间，求出定时时间
     *
     * @param time
     *            格式必须是HH:mm:ss
     * @return 返回的单位是分
     */
    public static long getAutoTime(String time) {
        String nowdatetime = DateUtils.getNowTime("yyyy-MM-dd HH:mm:ss");
        String nowtime = "";
        if (nowdatetime != null && nowdatetime.split(" ").length >= 2) {
            nowtime = nowdatetime.split(" ")[1].trim();
        } else {
            nowtime = DateUtils.getNowTime("HH:mm:ss");
        }
        long diff = 0;
        Date targetDate = DateUtils.dateStringToDate(time, "HH:mm:ss");
        Date nowDate = DateUtils.dateStringToDate(nowtime, "HH:mm:ss");
        if (targetDate.before(nowDate)) {
            Date nextdate = DateUtils.getAfterDate(Calendar.DATE, 1);
            String target = DateUtils.parseDate("yyyy-MM-dd", nextdate) + " "
                    + time;
            targetDate = DateUtils.dateStringToDate(target,
                    "yyyy-MM-dd HH:mm:ss");
            nowDate = DateUtils.dateStringToDate(nowdatetime,
                    "yyyy-MM-dd HH:mm:ss");
            diff = (targetDate.getTime() - nowDate.getTime()) / 60000;
            System.out.println(target + " - " + nowdatetime + " = " + diff);
        } else {
            diff = (targetDate.getTime() - nowDate.getTime()) / 60000;
            System.out.println(time + " - " + nowtime + " = " + diff);
        }
        return diff;
    }
	/**
	 * 将DateString转换成日期
	 * @param dateString
	 * @param dataFormatString
	 * @return
	 */
	public static Date dateStringToDate(String dateString, String dataFormatString) {
		
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(dataFormatString, Locale.CHINA);
			return dateFormat.parse(dateString);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 返回特定格式的当前时间
	 * @return
	 */
	public static String getNowTime() {
		Date date = new Date();
		String nowTime = bartDateFormat.format(date);
		return nowTime;
	}
	
	/**
	 * 格式化时间
	 * @param dateFormatString
	 * @param date
	 * @return
	 */
	public static String parseDate(String dateFormatString, Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				dateFormatString, Locale.CHINA);
		String nowTime = dateFormat.format(date);
		return nowTime;
	}
	
	/**
	 * 根据输入格式返回时间
	 * 例如输入：yyyy-MM-dd
	 * @param dateFormatString
	 * @return
	 */
	public static String getNowTime(String dateFormatString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				dateFormatString, Locale.CHINA);
		Date date = new Date();
		String nowTime = dateFormat.format(date);
		return nowTime;
	}
	
	/**
	 * 获取当前时间之后的时间<br>
	 * type为之后的时间的单位：可选值为Calendar.MINUTE等<br>
	 * afteTime为之后的时间度数
	 * @param type
	 * @param afteTime
	 * @return
	 */
	public static Date getAfterDate(int type, int afteTime) {
		Calendar nowTime = Calendar.getInstance();//获取当前时间
		nowTime.add(type, afteTime);//添加心跳时间间隔
		return nowTime.getTime();
	}
	
	/**
	 * 判断一个日期字符串是否为当天时间
	 * @param dateString
	 * @return
	 */
	public static boolean isToday(String dateString) {
		
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhMMss", Locale.CHINA);
			Date date = dateFormat.parse(dateString);
			Date nowTime = new Date();
			nowTime = dateFormat.parse(dateFormat.format(nowTime));
			if (date.compareTo(nowTime) == 0) {
				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
				Date date = dateFormat.parse(dateString);
				Date nowTime = new Date();
				nowTime = dateFormat.parse(dateFormat.format(nowTime));
				if (date.compareTo(nowTime) == 0) {
					return true;
				} else {
					return false;
				}
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			
			return false;
		}
		
	}
    /**
     * 将字符串转换成相应格式的date
     * @param date_str
     * @param dateformat
     * @return
     */
    public static Date strToDate(String date_str,String dateformat){
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat, Locale.CHINA);
        Date date = null;
        try {
            date = dateFormat.parse(date_str);
        } catch (ParseException e) {
            log.info("字符串时间  " + date_str + " 转换成  " + dateformat + " 格式异常： " + e.getMessage());
        }
        return date;
    }
    public static String getBeforeDay(int berforeDay){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -berforeDay);
        Date date = calendar.getTime();
        return  DateUtils.parseDate("yyyy-MM-dd", date);
    }
    public static String getBeforeDay(int berforeDay,String pattern){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -berforeDay);
        Date date = calendar.getTime();
        return  DateUtils.parseDate(pattern, date);
    }


     /*
	 * 判断是否超时
	 */

    public static boolean isTimeout(Date sendTime, long timeout) {
        boolean result = true;
        try {
            long _sendTimeTimeMillis = sendTime.getTime();
            long _currentTimeMillis = System.currentTimeMillis();
            long _timeout = timeout * 1000;
            if (_currentTimeMillis - _sendTimeTimeMillis < _timeout) {
                result = false;
            }
        } catch (Exception e) {
            log.warn("接收到的发送时间[" + sendTime + "]格式有误");
        }
        return result;
    }
}
