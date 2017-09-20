package com.zoom.iplocation.service;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zoom.iplocation.request.MapLevelsRequest;

public interface IpLocationService {

	public JSONArray getMaskcanvas(MapLevelsRequest request) throws Exception;
	
	public JSONObject getMarkercluster(MapLevelsRequest request) throws Exception;
	
	public JSONObject getGeoJson(String date) throws Exception;
	
	public JSONObject getChartData(MapLevelsRequest request) throws Exception;
	
	public JSONObject getLineData(MapLevelsRequest request) throws Exception;
	
	public JSONArray getCountryPoint(MapLevelsRequest request) throws Exception;
	
	public JSONArray getCityPoint(MapLevelsRequest request) throws Exception;
	
	public JSONObject getServerGroupData() throws Exception;
}
