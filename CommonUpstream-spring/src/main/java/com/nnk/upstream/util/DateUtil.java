package com.nnk.upstream.util;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private static final ThreadLocal<DateFormat> DATE_FORMAT = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			return format;
		}
	};

	public static String format(Date date, String pattern) {
		DateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	public static String format(Date date) {
		return DATE_FORMAT.get().format(date);
	}

	public static Date parse(String source) throws ParseException {
		return DATE_FORMAT.get().parse(source);
	}

	public static Date parse(String source, String pattern) throws ParseException {
		DateFormat format = new SimpleDateFormat(pattern);
		return format.parse(source);
	}
	public static String transfomat(String src,String srcPattern,String desPattern)
	{
		Date date = null;
		try {
			date = parse(src, srcPattern);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String desStr = format(date, desPattern);
		return desStr;
		
	}
    public static long getsparetime(String Ordertime) throws ParseException {

        String riqi =  DateUtil.format(new Date(),"yyyyMMdd");
        String shijian = DateUtil.format(new Date(),"HHmmss");
        int i = Integer.parseInt(Ordertime)- Integer.parseInt(shijian);
        if(i>0){
            String newdate = riqi+Ordertime;
            Date nextdate= DateUtil.parse(newdate, "yyyyMMddHHmmss");
            long nexttime = nextdate.getTime();
            System.out.println(nexttime);
            long curtime= System.currentTimeMillis();
            System.out.println(curtime);
            return (nexttime-curtime)/1000;
        }else {
            int next = Integer.parseInt(riqi) + 1;
            String nextday = String.valueOf(next);
            System.out.println(nextday);
            String newdate = next + Ordertime;
            Date nextdate = DateUtil.parse(newdate, "yyyyMMddHHmmss");
            long nexttime = nextdate.getTime();
            System.out.println(nexttime);
            long curtime = System.currentTimeMillis();
            System.out.println(curtime);
            return (nexttime - curtime) / 1000;
        }
    }
    


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
			throw new RuntimeException("Date time format error");
		}
		return result;
	}

    public static boolean Isbetween(Date srctime,Date startime, Date endtime) {
        return srctime.before(endtime)&&srctime.after(startime);
    }
}
