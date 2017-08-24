package com.zoom.iplocation.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zoom.iplocation.dao.IpLocationDao;
import com.zoom.iplocation.entity.ZmIpDetail;
import com.zoom.iplocation.request.MapLevelsRequest;
import com.zoom.iplocation.service.IpLocationService;
import com.zoom.iplocation.utils.GeoJsonUtils;

@Service
public class IplocationServiceImp implements IpLocationService {
	@Autowired
	private IpLocationDao ipLocationDao;

	@Transactional
	public JSONArray getMaskcanvas(String date) throws Exception {
		JSONArray result = new JSONArray();
		List<ZmIpDetail> zmIpDetails = ipLocationDao.queryCountry(date);
		GeoJsonUtils.toGeoJson(zmIpDetails);
		for (ZmIpDetail zmIpDetail : zmIpDetails) {
			JSONArray oneLal = new JSONArray();
			oneLal.put(zmIpDetail.getLatitude());
			oneLal.put(zmIpDetail.getLongitude());

			result.put(oneLal);
		}
		return result;
	}

	public JSONObject getGeoJson(String date) throws Exception {
		List<ZmIpDetail> zmIpDetails = ipLocationDao.queryCountry(date);
		return GeoJsonUtils.toGeoJson(zmIpDetails);
	}

	@Override
	public JSONObject getMarkercluster(MapLevelsRequest request) throws Exception {
		List<ZmIpDetail> zmIpDetails = new ArrayList<ZmIpDetail>();
		if (request.getZoomLevel() < 11) {
			zmIpDetails = ipLocationDao.queryLD3(request);
		} else if (request.getZoomLevel() < 13 && request.getZoomLevel() >= 11) {
			zmIpDetails = ipLocationDao.queryLalGroup(request);
		} else {
			zmIpDetails = ipLocationDao.queryLal(request);
		}
		return GeoJsonUtils.toJsonArray(zmIpDetails);
	}
}
