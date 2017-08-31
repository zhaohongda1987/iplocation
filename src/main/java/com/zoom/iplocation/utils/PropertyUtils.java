package com.zoom.iplocation.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyUtils {

	private static Logger LOG = LoggerFactory.getLogger(PropertyUtils.class);
			
	public static Map<String, String> getProperty() {
		Properties prop = new Properties();
		Map<String, String> tmpMap = new HashMap<>();
		try {
			// 读取属性文件a.properties
			InputStream in = new BufferedInputStream(new FileInputStream("E:\\git_project\\iplocation\\src\\main\\resources\\jdbc.properties"));
			prop.load(in); /// 加载属性列表
			Iterator<String> it = prop.stringPropertyNames().iterator();
			while (it.hasNext()) {
				String key = it.next();
				tmpMap.put(key, prop.getProperty(key));
				System.out.println(key + ":" + prop.getProperty(key));
			}
			in.close();
		} catch (Exception e) {
			LOG.error("getProperty:", e);
		}
		return tmpMap;
	}
}
