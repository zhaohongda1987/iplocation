package com.zoom.iplocation.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatUtils {

	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public static String getYYYYmmDD(String oldDate) {
		try {
			String[] dateArray = oldDate.split("-");
			return dateArray[0] + dateArray[1] + dateArray[2];
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
