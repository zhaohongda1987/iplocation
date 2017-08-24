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
		if (StringUtils.isNotBlank(request.getSearchDate())) {
			request.setSearchDate(DateFormatUtils.getYYYYmmDD(request.getSearchDate()));
		} else {
			String basicDate = DateFormatUtils.getLast2Day();
			request.setSearchDate(DateFormatUtils.getYYYYmmDD(basicDate));
		}
		
		return request;
	}
}
