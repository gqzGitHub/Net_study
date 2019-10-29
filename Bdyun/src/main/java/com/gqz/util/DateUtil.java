package com.gqz.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ���ڹ�����
 */
public class DateUtil {

	/**
	 * ��ָ�����ڶ���ת��ָ����ʽ�������ַ���
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}

	/**
	 * ��ָ�������ַ���ת��ָ����ʽ�����ڶ���
	 * @param str
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static Date formatString(String str,String format) throws Exception{
		if(StringUtil.isEmpty(str)){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.parse(str);
	}

	/**
	 * ��ȡ��ǰ���ڵ��ַ���
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentDateStr()throws Exception{
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
}
