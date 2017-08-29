package com.zoom.iplocation.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtilsSelf {
	public static String regEx = "[^0-9]";

	public static Map<String, String> getDomainMap(String address) {
		Map<String, String> domainMap = new HashMap<>();
		// split address
		String[] tmpArray = null;
		if (address.contains("ZOOM")) {
			tmpArray = address.split("ZOOM");
		} else if (address.contains("zoom")) {
			tmpArray = address.split("zoom");
		}
		// get location
		String location = "";
		String num = "";
		if (tmpArray != null && tmpArray.length > 1) {
			location = tmpArray[1].substring(0, 2);
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(tmpArray[1]);
			num = m.replaceAll("");
		}
		domainMap.put("address", address);
		domainMap.put("location", location);
		domainMap.put("num", num);
		return domainMap;
	}

	public static Map<String, String> getIpMap(String address) {
		Map<String, String> ipMap = new HashMap<>();
		String ip = "";
		String ipLastNum = "";
		if (address.contains("//")) {
			String[] tmp1 = address.split("//");
			if (tmp1.length > 0) {
				String[] tmp2 = tmp1[1].split(":");
				ip = tmp2[0];
				String[] tmp3 = ip.split("\\.");
				ipLastNum = tmp3[3];
			}
		}
		ipMap.put("address", address);
		ipMap.put("ip", ip);
		ipMap.put("ipLastNum", ipLastNum);
		return ipMap;
	}
}
