package com.zoom.iplocation.service;

import org.json.JSONArray;

import com.zoom.iplocation.request.MapLevelsRequest;

public interface HeatMapService {
	public JSONArray getHeatMap(MapLevelsRequest request) throws Exception;

	public JSONArray getHeatMapAjax(MapLevelsRequest request) throws Exception;
}
