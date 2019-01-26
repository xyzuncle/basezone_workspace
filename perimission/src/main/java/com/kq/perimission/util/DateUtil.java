package com.kq.perimission.util;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/** 
 * 说明：日期处理
 * @author
 * 修改时间：2015年11月24日
 * @version
 */
public class DateUtil {
	
	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
	private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");
	private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat sdfTimes = new SimpleDateFormat("yyyyMMddHHmmss");


	/**
	 * 获取YYYY格式
	 * @return
	 */
	public static String getSdfTimes() {
		return sdfTimes.format(new Date());
	}
	
	/**
	 * 获取YYYY格式
	 * @return
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 * @return
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}
	
	/**
	 * 获取YYYYMMDD格式
	 * @return
	 */
	public static String getDays(){
		return sdfDays.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}

	/**
	* @Title: compareDate
	* @Description:
	* @param s
	* @param e
	* @return boolean  
	* @throws
	* @author
	 */
	public static boolean compareDate(String s, String e) {
		if(fomatDate(s)==null||fomatDate(e)==null){
			return false;
		}
		return fomatDate(s).getTime() >=fomatDate(e).getTime();
	}


    /**
     * 判断时间是否在时间段内
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean isinDate(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        }else if(date.equals(begin) && date.before(end)){
			return true;
		}
        else if(nowTime.compareTo(beginTime)==0 || nowTime.compareTo(endTime) == 0 ){
            return true;
        }else {
            return false;
        }
    }

	/**
	 * 格式化数据
	 * 利用jodatime来判断是否在时间之间,
	 * 返回 ture 包含 false 不包含
	 */
	public static boolean isinDateByJODA(Date nowTime, Date beginTime, Date endTime,String formate) {

		String begin = format(beginTime, formate);
		String end = format(endTime, formate);
		String now = format(nowTime, formate);
		begin = begin + "T00:00:00";
		end = end + "T23:59:59";
		DateTime dtBegin = new DateTime(begin);
		DateTime dtEnd = new DateTime(end);
		DateTime dtnow = new DateTime(now);

		Interval interval = new Interval(dtBegin,dtEnd);

		boolean contained = interval.contains(dtnow);

		return contained;
	}




	/**
	 * 格式化日期
	 * @return
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date fomateDateGMT8(String date){
		DateTime dt = new DateTime(date);
		dt = dt.plusHours(8);
		return dt.toDate();
	}

	/**
	 * 格式化日期
	 * @return
	 */
	public static Date fomatDateTime(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 校验日期是否合法
	 * @return
	 */
	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}
	
	/**
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int getDiffYear(String startTime,String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			//long aa=0;
			int years=(int) (((fmt.parse(endTime).getTime()-fmt.parse(startTime).getTime())/ (1000 * 60 * 60 * 24))/365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}
	 
	/**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long 
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr,String endDateStr){
        long day=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;
        
            try {
				beginDate = format.parse(beginDateStr);
				endDate= format.parse(endDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
            //System.out.println("相隔的天数="+day);
      
        return day;
    }
    
    /**
     * 得到n天之后的日期
     * @param days
     * @return
     */
    public static String getAfterDayDate(Date targetDate,String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.setTime(targetDate);
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);
        
        return dateStr;
    }

    /**
     * 得到n小时候之后的日期
     * @param hours
     * @return
     */
    public static String getAfterHoursDate(Date targetDate,String hours) {
        int daysInt = Integer.parseInt(hours);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.setTime(targetDate);
        canlendar.add(Calendar.HOUR_OF_DAY, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);

        return dateStr;
    }



	/**
	 * 得到30秒后的时间
	 * @param second
	 * @return
	 */
	public static Date getAfter30SecondDate(Date targetDate,String second) {
		int daysInt = Integer.parseInt(second);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.setTime(targetDate);
		canlendar.add(Calendar.SECOND, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		/*SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);*/

		return date;
	}
    
    /**
     * 得到n天之后是周几
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
    	int daysInt = Integer.parseInt(days);
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    /**
     * 拿当前时间和需要的时间进行比较，判断是否是未来时间
     * @param now 当前时间
     * @param dt  需要被比较的时间
     * @return 如果是false 表示 不是未来时间
     *         true 表示是未来时间
     */
    public static boolean getFutureTimeFlag(String now,Date dt){
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");

        DateTime nowTime = DateTime.parse(now,format);
        DateTime endTime = new DateTime(dt);

        Duration duration = new Duration(nowTime, endTime);
        long  time = duration.getStandardDays();
        if (time>0){
            return true;
        } else {
            return false;
        }
    }


    public static String format(Date date , String regex){
        String formate = "";
        switch (regex){
            case "yyyy-MM-dd":
                formate = sdfDay.format(date);
                break;
            case "yyyy-MM-dd HH:mm:ss":
                formate = sdfTime.format(date);
                break;
            case "yyyyMMdd":
                formate = sdfDays.format(date);
                break;
            case "yyyyMMddHHmmss":
                formate = sdfTimes.format(date);
                break;
            case "yyyy":
                formate = sdfYear.format(date);
                break;
        }

        return formate;
    }





    
    public static void main(String[] args) throws ParseException {

        DateTime dt1 = new DateTime(new Date());
		dt1 = dt1.plusHours(5);
		DateTime dt2 = dt1.plusYears(1);

		DateTime now = new DateTime(new Date());


        boolean result = DateUtil.isinDateByJODA(now.toDate(),dt1.toDate(),dt2.toDate(),"yyyy-MM-dd");
		System.out.println(result);


    }

}
