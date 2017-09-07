package com.zoom.iplocation.test;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zoom.iplocation.dao.IpLocationDao;
import com.zoom.iplocation.entity.ZmIpDetail;
import com.zoom.iplocation.request.MapLevelsRequest;
import com.zoom.iplocation.test.BaseTest;

public class IpLocationDaoTest extends BaseTest {
	@Autowired
	private IpLocationDao ipLocationDao;

	private String ipPattern  = ".*\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}.*";
	@Test
	public void testQueryLal() throws Exception {
		MapLevelsRequest request = new MapLevelsRequest();
		request.setNorthwestLat(35.75097043944928);
		request.setNorthwestLng(239.47448730468753);
		request.setSourtheastLat(33.03169299978312);
		request.setSourtheastLng(243.86901855468753);
		request.setSqlDate("20170822");
		List<ZmIpDetail> zmIpDetails = ipLocationDao.queryLal(request);
		for (ZmIpDetail zmIpDetail : zmIpDetails) {
			System.out.println(zmIpDetail.getLatitude());
		}
	}
	
	@Test
	public void testQueryCity() throws Exception {
		MapLevelsRequest request = new MapLevelsRequest();
		request.setNorthwestLat(48.1367666796927);
		request.setNorthwestLng(-130.4296875);
		request.setSourtheastLat(26.941659545381512);
		request.setSourtheastLng(-46.05468749999998);
		List<ZmIpDetail> zmIpDetails = ipLocationDao.queryCity(request);
		for (ZmIpDetail zmIpDetail : zmIpDetails) {
			System.out.println(zmIpDetail.getLatitude());
		}
	}
	@Test
	public void testQueryCountry() throws Exception {
//		List<ZmIpDetail> zmIpDetails = ipLocationDao.queryCountry("2017-08-04");
//		for (ZmIpDetail zmIpDetail : zmIpDetails) {
//			System.out.println(zmIpDetail.getLatitude());
//		}
	}
	
	@Test
	public void testQuerySdkAttendee() {
		try {
			List<Map<String, Object>> tmpList = ipLocationDao.querySdkAttendee("2017-08-26");
			for(Map<String, Object> tmpMap: tmpList) {
				String address = tmpMap.get("mmr_cmd_address").toString();
				if (Pattern.matches(ipPattern, address)) {
					System.out.println(address);
				}
			}
		} catch(Exception e) {
			System.err.println(e);
		}
	}
}
