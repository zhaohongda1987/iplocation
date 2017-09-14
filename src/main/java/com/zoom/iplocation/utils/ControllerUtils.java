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
		if (StringUtils.isNotBlank(request.getStartDate())) {
			String[] dateArray = request.getStartDate().split("~");
			if (dateArray.length > 1) {
				request.setStartDate(dateArray[0].trim());
				request.setEndDate(dateArray[1].trim());
				request.setSqlDate(DateFormatUtils.getYYYYmm(dateArray[1].trim()));
			}
		} else {
			String basicDate = DateFormatUtils.getThreeDayAgo();
			request.setStartDate(basicDate);
			request.setEndDate(basicDate);
			request.setSqlDate(DateFormatUtils.getYYYYmm(basicDate));
		}
		// check server group
		if (StringUtils.isNotBlank(request.getServerGroup())) {
			if (request.getServerGroup().equals("all")) {
				request.setServerGroup("");
			}
		}

		// check ip
		if (StringUtils.isNotBlank(request.getIpAddr())) {
			if (request.getIpAddr().equals("all")) {
				request.setIpAddr("");
			}
		}
		// check accountType
		if (request.getAccountType() != null && request.getAccountType().size() > 0) {
			for (String accountTypeStr : request.getAccountType()) {
				if (accountTypeStr.equals("null")) {
					request.setAccountType(null);
				}
			}
		}

		return request;
	}
}
