package com.zoom.iplocation.service.imp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zoom.iplocation.dao.IpLocationDao;
import com.zoom.iplocation.entity.ZmIpDetail;
import com.zoom.iplocation.request.MapLevelsRequest;
import com.zoom.iplocation.service.IpLocationService;
import com.zoom.iplocation.utils.GeoJsonUtils;

@Service
public class IplocationServiceImp implements IpLocationService {
	@Autowired
	private IpLocationDao ipLocationDao;

	public JSONArray getMaskcanvas(MapLevelsRequest request) throws Exception {
		JSONArray result = new JSONArray();
		List<ZmIpDetail> zmIpDetails = ipLocationDao.queryLal(request);
		for (ZmIpDetail zmIpDetail : zmIpDetails) {
			JSONArray oneLal = new JSONArray();
			oneLal.put(zmIpDetail.getLatitude());
			oneLal.put(zmIpDetail.getLongitude());

			result.put(oneLal);
		}
		return result;
	}

	public JSONObject getGeoJson(String date) throws Exception {
		// List<ZmIpDetail> zmIpDetails = ipLocationDao.queryCountry(date);
		return null;
	}

	@Override
	public JSONObject getMarkercluster(MapLevelsRequest request) throws Exception {
		List<ZmIpDetail> zmIpDetails = new ArrayList<ZmIpDetail>();
		if (request.getZoomLevel() < 11) {
			zmIpDetails = ipLocationDao.queryLD3(request);
		} else {
			zmIpDetails = ipLocationDao.queryLalGroup(request);
		}
		return GeoJsonUtils.toJsonArray(zmIpDetails);
	}

	@Override
	public JSONObject getChartData(MapLevelsRequest request) throws Exception {
		JSONObject result = new JSONObject();
		List<ZmIpDetail> zmIpDetails = ipLocationDao.queryCountry(request);
		// table
		JSONArray tableArray = new JSONArray();
		// total num
		Integer totalNum = 0;
		for (ZmIpDetail zmIpDetail : zmIpDetails) {
			totalNum += zmIpDetail.getIpCount();
		}
		JSONObject allDataCol = new JSONObject();
		allDataCol.put("country", "ALL");
		allDataCol.put("num", totalNum);
		tableArray.put(allDataCol);
		// pie chart
		List<String> legend = new ArrayList<>();
		List<JSONObject> data = new ArrayList<>();
		JSONObject pieChart = new JSONObject();
		for (ZmIpDetail zmIpDetail : zmIpDetails) {
			// table
			JSONObject col = new JSONObject();
			col.put("country", zmIpDetail.getCn());
			col.put("num", zmIpDetail.getIpCount());

			tableArray.put(col);
			// pie chart
			if (data.size() < 10) {
				legend.add(zmIpDetail.getCn());
				JSONObject simpleData = new JSONObject();
				simpleData.put("name", zmIpDetail.getCn());
				simpleData.put("value", zmIpDetail.getIpCount());
				data.add(simpleData);
			}
			// funnel max
			if (data.size() == 1) {
				pieChart.put("max", zmIpDetail.getIpCount());
			}
		}
		pieChart.put("legend", legend);
		pieChart.put("data", data);
		result.put("tableData", tableArray);
		result.put("pieData", pieChart);
		return result;
	}

	@Override
	public JSONArray getCountryPoint(MapLevelsRequest request) throws Exception {
		JSONArray result = new JSONArray();
		List<ZmIpDetail> zmIpDetails = ipLocationDao.queryCountry(request);
		for (ZmIpDetail zmIpDetail : zmIpDetails) {
			JSONObject tmp = new JSONObject();
			tmp.put("latitude", zmIpDetail.getLatitude());
			tmp.put("longitude", zmIpDetail.getLongitude());
			Integer num = zmIpDetail.getIpCount();
			tmp.put("title", "Country=" + zmIpDetail.getCn() + ";Num=" + num);
			// calculate icon size
			if (num <= 100) {
				tmp.put("size", 6);
				tmp.put("color", "#33ffff");
			} else if (num > 100 && num <= 500) {
				tmp.put("size", 8);
				tmp.put("color", "#3399ff");
			} else if (num > 500 && num <= 1000) {
				tmp.put("size", 10);
				tmp.put("color", "#3333ff");
			} else if (num > 1000 && num <= 3000) {
				tmp.put("size", 12);
				tmp.put("color", "#ffff99");
			} else if (num > 3000 && num <= 5000) {
				tmp.put("size", 14);
				tmp.put("color", "#ffff00");
			} else if (num > 5000 && num <= 10000) {
				tmp.put("size", 16);
				tmp.put("color", "#ff9933");
			} else if (num > 10000 && num <= 50000) {
				tmp.put("size", 18);
				tmp.put("color", "#ff6600");
			} else {
				tmp.put("size", 20);
				tmp.put("color", "#ff3300");
			}
			result.put(tmp);
		}
		return result;
	}

	@Override
	public JSONArray getCityPoint(MapLevelsRequest request) throws Exception {
		JSONArray result = new JSONArray();
		List<ZmIpDetail> zmIpDetails = ipLocationDao.queryCity(request);
		for (ZmIpDetail zmIpDetail : zmIpDetails) {
			JSONObject tmp = new JSONObject();
			tmp.put("latitude", zmIpDetail.getLatitude());
			tmp.put("longitude", zmIpDetail.getLongitude());
			Integer num = zmIpDetail.getIpCount();
			tmp.put("title", "City=" + zmIpDetail.getCity() + ";Num=" + num);
			// calculate icon size
			if (num <= 10) {
				tmp.put("size", 6);
				tmp.put("color", "#33ffff");
			} else if (num > 10 && num <= 50) {
				tmp.put("size", 8);
				tmp.put("color", "#3399ff");
			} else if (num > 50 && num <= 100) {
				tmp.put("size", 10);
				tmp.put("color", "#3333ff");
			} else if (num > 100 && num <= 300) {
				tmp.put("size", 12);
				tmp.put("color", "#ffff99");
			} else if (num > 300 && num <= 500) {
				tmp.put("size", 14);
				tmp.put("color", "#ffff00");
			} else if (num > 500 && num <= 1000) {
				tmp.put("size", 16);
				tmp.put("color", "#ff9933");
			} else if (num > 1000 && num <= 5000) {
				tmp.put("size", 18);
				tmp.put("color", "#ff6600");
			} else {
				tmp.put("size", 20);
				tmp.put("color", "#ff3300");
			}
			result.put(tmp);
		}
		return result;
	}

	@Override
	public JSONObject getServerGroupData() throws Exception {
		Map<String, Set<String>> tmpMap = ipLocationDao.querySeverGroupIp();
		JSONObject result = new JSONObject();
		Set<String> allSet = new HashSet<>();
		for (Map.Entry<String, Set<String>> entry : tmpMap.entrySet()) {
			result.put(entry.getKey(), entry.getValue());
			allSet.addAll(entry.getValue());
		}
		result.put("all", allSet);
		return result;
	}
}
