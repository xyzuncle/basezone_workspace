package com.kq.perimission.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeScopeUtile {

	private final static Date date = new Date();
	//A
	private final static String A_BEGIN = "11:00:00";
	private final static String A_END = "13:00:00";
	//B
	private final static String B_BEGIN = "09:00:00";
	private final static String B_END = "11:00:00";
	private final static String B_BEGIN_TWO = "13:00:00";
	private final static String B_END_TWO = "15:00:00";
	//C
	private final static String C_BEGIN = "08:00:00";
	private final static String C_END = "09:00:00";
	private final static String C_BEGIN_TWO = "15:00:00";
	private final static String C_END_TWO = "16:00:00";
	//D
	private final static String D_BEGIN = "06:00:00";
	private final static String D_END = "08:00:00";
	private final static String D_BEGIN_TWO = "16:00:00";
	private final static String D_END_TWO = "18:00:00";	
	//E
	private final static String E_BEGIN = "04:00:00";
	private final static String E_END = "06:00:00";
	private final static String E_BEGIN_TWO = "18:00:00";
	private final static String E_END_TWO = "20:00:00";	
	//F
	private final static String F_BEGIN = "20:00:00";
	private final static String F_END = "00:00:00";
	private final static String F_BEGIN_TWO = "00:00:00";
	private final static String F_END_TWO = "04:00:00";	
	
	
	public static void main(String[] args) {
		String strDateBegin="2018-07-01";
		String strDateEnd="2018-07-30";
		Date datse = new Date();
		boolean inDate = isInDate(datse,strDateBegin,strDateEnd);
		System.out.println(inDate);
		/*String timeType = timeType();*//*
		System.out.println(timeType);*/
	}
	
	/** 
	* 判断时间是否在时间段内 
	*  
	* @param date 
	*            当前时间 yyyy-MM-dd HH:mm:ss 
	* @param strDateBegin 
	*            开始时间 00:00:00 
	* @param strDateEnd 
	*            结束时间 00:05:00 
	* @return 
	*/  
	public static boolean isInDate(Date date, String strDateBegin,String strDateEnd) {  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);  
		String strDate = sdf.format(date);   //2016-12-16 11:53:54




		// 截取当前时间时分秒 转成整型
		int  tempDate=Integer.parseInt(strDate.substring(11, 13)+strDate.substring(14, 16)+strDate.substring(17, 19));  
		
		// 截取开始时间时分秒  转成整型
		int  tempDateBegin=Integer.parseInt(strDateBegin.substring(0, 2)+strDateBegin.substring(3, 5)+strDateBegin.substring(6, 8));  
		// 截取结束时间时分秒  转成整型
		int  tempDateEnd=Integer.parseInt(strDateEnd.substring(0, 2)+strDateEnd.substring(3, 5)+strDateEnd.substring(6, 8));
	
		if ((tempDate >= tempDateBegin && tempDate <= tempDateEnd)) {  
		return true;  
		} else {  
		return false;  
		}  
	}
	
	/**
	 * 
	 * @return
	 */
	public static String timeType(){
		String type="";
		int[] zhuanhuanInt_A = zhuanhuanInt(date,A_BEGIN,A_END);
		int[] zhuanhuanInt_B = zhuanhuanInt(date,B_BEGIN,B_END);
		int[] zhuanhuanInt_B_two = zhuanhuanInt(date,B_BEGIN_TWO,B_END_TWO);
		int[] zhuanhuanInt_C = zhuanhuanInt(date,C_BEGIN,C_END);
		int[] zhuanhuanInt_C_two = zhuanhuanInt(date,C_BEGIN_TWO,C_END_TWO);
		int[] zhuanhuanInt_D = zhuanhuanInt(date,D_BEGIN,D_END);
		int[] zhuanhuanInt_D_two = zhuanhuanInt(date,D_BEGIN_TWO,D_END_TWO);
		int[] zhuanhuanInt_E = zhuanhuanInt(date,E_BEGIN,E_END);
		int[] zhuanhuanInt_E_two = zhuanhuanInt(date,E_BEGIN_TWO,E_END_TWO);
		int[] zhuanhuanInt_F = zhuanhuanInt(date,F_BEGIN,F_END);
		int[] zhuanhuanInt_F_two = zhuanhuanInt(date,F_BEGIN_TWO,F_END_TWO);
		if(zhuanhuanInt_A[2]>zhuanhuanInt_A[0]&&zhuanhuanInt_A[2]<zhuanhuanInt_A[1]){
			type="A";
		}else if((zhuanhuanInt_B[2]>zhuanhuanInt_B[0]&&zhuanhuanInt_B[2]<zhuanhuanInt_B[1])||(zhuanhuanInt_B_two[2]>zhuanhuanInt_B_two[0]&&zhuanhuanInt_B_two[2]<zhuanhuanInt_B_two[1])){
			type="B";
		}else if((zhuanhuanInt_C[2]>zhuanhuanInt_C[0]&&zhuanhuanInt_C[2]<zhuanhuanInt_C[1])||(zhuanhuanInt_C_two[2]>zhuanhuanInt_C_two[0]&&zhuanhuanInt_C_two[2]<zhuanhuanInt_C_two[1])){
			type="C";
		}else if((zhuanhuanInt_D[2]>zhuanhuanInt_D[0]&&zhuanhuanInt_D[2]<zhuanhuanInt_D[1])||(zhuanhuanInt_D_two[2]>zhuanhuanInt_D_two[0]&&zhuanhuanInt_D_two[2]<zhuanhuanInt_D_two[1])){
			type="D";
		}else if((zhuanhuanInt_E[2]>zhuanhuanInt_E[0]&&zhuanhuanInt_E[2]<zhuanhuanInt_E[1])||(zhuanhuanInt_E_two[2]>zhuanhuanInt_E_two[0]&&zhuanhuanInt_E_two[2]<zhuanhuanInt_E_two[1])){
			type="E";
		}else if((zhuanhuanInt_F[2]>zhuanhuanInt_F[0]&&zhuanhuanInt_F[2]<zhuanhuanInt_F[1])||(zhuanhuanInt_F_two[2]>zhuanhuanInt_F_two[0]&&zhuanhuanInt_F_two[2]<zhuanhuanInt_F_two[1])){
			type="F";
		}
		return type;
	} 
	
	public static int[] zhuanhuanInt(Date date, String strDateBegin,String strDateEnd) {  
		int[] str = new int[3];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);  
		String strDate = sdf.format(date);   //2016-12-16 11:53:54
		// 截取当前时间时分秒 转成整型
		int  tempDate=Integer.parseInt(strDate.substring(11, 13)+strDate.substring(14, 16)+strDate.substring(17, 19));  
		
		// 截取开始时间时分秒  转成整型
		int  tempDateBegin=Integer.parseInt(strDateBegin.substring(0, 2)+strDateBegin.substring(3, 5)+strDateBegin.substring(6, 8));  
		// 截取结束时间时分秒  转成整型
		int  tempDateEnd=Integer.parseInt(strDateEnd.substring(0, 2)+strDateEnd.substring(3, 5)+strDateEnd.substring(6, 8));
		str[0]=tempDateBegin;
		str[1]=tempDateEnd;
		str[2]=tempDate;
		return str; 
	}
}
