package com.zoom.iplocation.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zoom.iplocation.dao.IpLocationDao;
import com.zoom.iplocation.entity.ZmIpDetail;
import com.zoom.iplocation.request.MapLevelsRequest;
import com.zoom.iplocation.service.HeatMapService;
import com.zoom.iplocation.utils.DateFormatUtils;

@Service
public class HeatMapServiceImp implements HeatMapService {

	@Autowired
	private IpLocationDao ipLocationDao;

	@Override
	public JSONArray getHeatMap(MapLevelsRequest request) throws Exception {
		List<ZmIpDetail> zmIpDetails = ipLocationDao.queryLD3(request);
		return getHeatMapResult(zmIpDetails);
	}

	private JSONArray getHeatMapResult(List<ZmIpDetail> zmIpDetails) {
		JSONArray result = new JSONArray();
		for (ZmIpDetail zmIpDetail : zmIpDetails) {
			JSONArray tmp = new JSONArray();
			tmp.put(zmIpDetail.getLatitude());
			tmp.put(zmIpDetail.getLongitude());
			if (zmIpDetail.getIpCount() != null) {
				tmp.put(String.valueOf(zmIpDetail.getIpCount()));
			} else {
				tmp.put("1");
			}

			result.put(tmp);
		}
		
		return result;
	}

	@Override
	public JSONArray getHeatMapAjax(MapLevelsRequest request) throws Exception {
		List<ZmIpDetail> zmIpDetails = new ArrayList<ZmIpDetail>();
		if (request.getZoomLevel() < 11) {
			zmIpDetails = ipLocationDao.queryLD3(request);
		} else {
			zmIpDetails = ipLocationDao.queryLalGroup(request);
		}
		return getHeatMapResult(zmIpDetails);
	}

}
