package com.nnk.template.util;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

	// ʱ�䶨�Ƹ�ʽ"yyyy-MM-dd HH:mm:ss"
	private static SimpleDateFormat bartDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private static Logger log = Logger.getLogger(DateUtils.class);
	/**
	 * ��DateStringת����ָ����ʽ��DataString
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
     * ���ݸ���ʱ�䣬�����ʱʱ��
     *
     * @param time
     *            ��ʽ������HH:mm:ss
     * @return ���صĵ�λ�Ƿ�
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
	 * ��DateStringת��������
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
	 * �����ض���ʽ�ĵ�ǰʱ��
	 * @return
	 */
	public static String getNowTime() {
		Date date = new Date();
		String nowTime = bartDateFormat.format(date);
		return nowTime;
	}
	
	/**
	 * ��ʽ��ʱ��
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
	 * ���������ʽ����ʱ��
	 * �������룺yyyy-MM-dd
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
	 * ��ȡ��ǰʱ��֮���ʱ��<br>
	 * typeΪ֮���ʱ��ĵ�λ����ѡֵΪCalendar.MINUTE��<br>
	 * afteTimeΪ֮���ʱ�����
	 * @param type
	 * @param afteTime
	 * @return
	 */
	public static Date getAfterDate(int type, int afteTime) {
		Calendar nowTime = Calendar.getInstance();//��ȡ��ǰʱ��
		nowTime.add(type, afteTime);//�������ʱ����
		return nowTime.getTime();
	}
	
	/**
	 * �ж�һ�������ַ����Ƿ�Ϊ����ʱ��
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
     * ���ַ���ת������Ӧ��ʽ��date
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
            log.info("�ַ���ʱ��  " + date_str + " ת����  " + dateformat + " ��ʽ�쳣�� " + e.getMessage());
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
	 * �ж��Ƿ�ʱ
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
            log.warn("���յ��ķ���ʱ��[" + sendTime + "]��ʽ����");
        }
        return result;
    }
}
