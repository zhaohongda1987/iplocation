package com.zoom.iplocation.utils;

import org.apache.commons.lang3.StringUtils;

import com.zoom.iplocation.request.MapLevelsRequest;

public class ControllerUtils {

	public static MapLevelsRequest getBasicMapLevelsRequest(MapLevelsRequest request) {
		// check zoom level
		Integer zoomLevel = 3;
		if (request.getZoomLevel() == null) {
			request.setZoomLevel(zoomLevel);
		}
		// check date
		if (StringUtils.isNotBlank(request.getEndDate())) {
			request.setSqlDate(DateFormatUtils.getYYYYmm(request.getEndDate()));
		} else {
			String endDate = DateFormatUtils.getThreeDayAgo();
			request.setSqlDate(DateFormatUtils.getYYYYmm(endDate));
			request.setEndDate(endDate);
		}
		
		if (StringUtils.isBlank(request.getStartDate())) {
			String startDate = DateFormatUtils.getThreeDayAgo();
			request.setStartDate(startDate);
		}
		
		return request;
	}
}
