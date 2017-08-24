package com.zoom.iplocation.service.imp;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zoom.iplocation.dao.IpLocationDao;
import com.zoom.iplocation.entity.ZmIpDetail;
import com.zoom.iplocation.request.MapLevelsRequest;
import com.zoom.iplocation.service.LD3Service;

@Service
public class LD3ServiceImp implements LD3Service {
	@Autowired
	private IpLocationDao ipLocationDao;

	@Override
	public JSONArray getLD3(MapLevelsRequest request) throws Exception{
		JSONArray result = new JSONArray();
		List<ZmIpDetail> zmIpDetails = ipLocationDao.queryLD3(request);
		for (ZmIpDetail zmIpDetail : zmIpDetails) {
			JSONObject tmp = new JSONObject();
			if (zmIpDetail.getIpCount() != null) {
				tmp.put("population", zmIpDetail.getIpCount());
			} else {
				tmp.put("population", 1);
			}
			JSONArray latlng = new JSONArray();
			latlng.put(zmIpDetail.getLatitude());
			latlng.put(zmIpDetail.getLongitude());
			tmp.put("latlng", latlng);

			result.put(tmp);
		}
		return result;
	}

	@Override
	public JSONArray getLD3ByLat(MapLevelsRequest request) throws Exception{
		Integer zoomLevel = 3;
		if (request.getZoomLevel() != null) {
			zoomLevel = request.getZoomLevel();
		}
		JSONArray result = new JSONArray();
		if (zoomLevel <= 6) {
			List<ZmIpDetail> zmIpDetails = ipLocationDao.queryLD3(request);
			for (ZmIpDetail zmIpDetail : zmIpDetails) {
				JSONObject tmp = new JSONObject();
				if (zmIpDetail.getIpCount() != null) {
					tmp.put("population", zmIpDetail.getIpCount());
				} else {
					tmp.put("population", 1);
				}
				JSONArray latlng = new JSONArray();
				latlng.put(zmIpDetail.getLatitude());
				latlng.put(zmIpDetail.getLongitude());
				tmp.put("latlng", latlng);

				result.put(tmp);
			}
		} else {
			List<ZmIpDetail> zmIpDetails = ipLocationDao.queryLal(request);
			for (ZmIpDetail zmIpDetail : zmIpDetails) {
				JSONObject tmp = new JSONObject();
				tmp.put("population", 1);
				JSONArray latlng = new JSONArray();
				latlng.put(zmIpDetail.getLatitude());
				latlng.put(zmIpDetail.getLongitude());
				tmp.put("latlng", latlng);

				result.put(tmp);
			}
		}
		return result;
	}
}
