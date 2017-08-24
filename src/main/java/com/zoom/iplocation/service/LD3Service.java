package com.zoom.iplocation.service;

import org.json.JSONArray;

import com.zoom.iplocation.request.MapLevelsRequest;


public interface LD3Service {
	public JSONArray getLD3(MapLevelsRequest request) throws Exception;
	
	public JSONArray getLD3ByLat(MapLevelsRequest request) throws Exception;
}