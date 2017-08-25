package com.zoom.iplocation.service;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zoom.iplocation.request.MapLevelsRequest;

public interface IpLocationService {

	public JSONArray getMaskcanvas(MapLevelsRequest request) throws Exception;
	
	public JSONObject getMarkercluster(MapLevelsRequest request) throws Exception;
	
	public JSONObject getGeoJson(String date) throws Exception;
}
