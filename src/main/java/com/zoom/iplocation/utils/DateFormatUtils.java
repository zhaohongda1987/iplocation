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

	public static String getBasicDay() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -3);
		date = calendar.getTime();
		return sdf.format(date);
	}
}
