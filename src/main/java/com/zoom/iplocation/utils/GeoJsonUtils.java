package com.zoom.iplocation.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zoom.iplocation.entity.ZmIpDetail;

public class GeoJsonUtils {

	public static JSONObject toGeoJson(List<ZmIpDetail> zmIpDetails) {
		JSONObject featureCollection = new JSONObject();
		try {
			featureCollection.put("type", "FeatureCollection");
			JSONArray featureList = new JSONArray();
			for (ZmIpDetail zmIpDetail : zmIpDetails) {
				// {"geometry": {"type": "Point", "coordinates": [-94.149, 36.33]}
				JSONObject point = new JSONObject();
				point.put("type", "Point");
				// construct a JSONArray from a string; can also use an array or list
				JSONArray coord = new JSONArray("[" + zmIpDetail.getLongitude() + "," + zmIpDetail.getLatitude() + "]");
				point.put("coordinates", coord);
				JSONObject feature = new JSONObject();
				// geometry
				feature.put("geometry", point);
				// type
				feature.put("type", "Feature");
				// properties
				JSONObject properties = new JSONObject();
				if (zmIpDetail.getIpCount() != null) {
					properties.put("point_count", zmIpDetail.getIpCount());
				}
				feature.put("properties", properties);

				featureList.put(feature);
				featureCollection.put("features", featureList);
			}
		} catch (JSONException e) {
			System.err.println("can't save json object: " + e.toString());
		}
		return featureCollection;
	}

	public static JSONObject toJsonArray(List<ZmIpDetail> zmIpDetails) {
		JSONObject result = new JSONObject();
		JSONArray dataArray = new JSONArray();
		try {
			for (ZmIpDetail zmIpDetail : zmIpDetails) {
				JSONObject oneData = new JSONObject();
				oneData.put("latitude", zmIpDetail.getLatitude());
				oneData.put("longitude", zmIpDetail.getLongitude());
				if (zmIpDetail.getIpCount() != null) {
					oneData.put("ipcount", zmIpDetail.getIpCount());
				}

				dataArray.put(oneData);
			}
		} catch (JSONException e) {
			System.err.println("can't save json object: " + e.toString());
		}
		result.put("dataarray", dataArray);
		return result;
	}

	public static void main(String args[]) {
		List<ZmIpDetail> zmIpDetails = new ArrayList<>();
		ZmIpDetail zmIpDetail = new ZmIpDetail();
		zmIpDetail.setLatitude(43.087653);
		zmIpDetail.setLongitude(-79.044073);
		zmIpDetails.add(zmIpDetail);
		System.out.println(GeoJsonUtils.toGeoJson(zmIpDetails));
	}
}
