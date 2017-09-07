package com.zoom.iplocation.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateFormatUtils {

	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private static Logger LOG = LoggerFactory.getLogger(DateFormatUtils.class);

	public static String getYYYYmmDD(String oldDate) {
		try {
			String[] dateArray = oldDate.split("-");
			return dateArray[0] + dateArray[1] + dateArray[2];
		} catch (Exception e) {
			LOG.error("getYYYYmmDD:", e);
			return "20170804";
		}
	}
	
	public static String getYYYYmm(String oldDate) {
		try {
			String[] dateArray = oldDate.split("-");
			return dateArray[0] + dateArray[1];
		} catch (Exception e) {
			LOG.error("getYYYYmm:", e);
			return "201708";
		}
	}

	public static String getThreeDayAgo() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -3);
		date = calendar.getTime();
		return sdf.format(date);
	}
	
	public static String getFourDayAgo() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -4);
		date = calendar.getTime();
		return sdf.format(date);
	}
}
