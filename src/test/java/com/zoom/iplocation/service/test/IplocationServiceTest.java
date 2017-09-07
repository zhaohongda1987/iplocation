package com.zoom.iplocation.service.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zoom.iplocation.request.MapLevelsRequest;
import com.zoom.iplocation.service.IpLocationService;
import com.zoom.iplocation.service.LD3Service;
import com.zoom.iplocation.service.ServerGroupService;
import com.zoom.iplocation.test.BaseTest;
import com.zoom.iplocation.utils.DateFormatUtils;
import com.zoom.iplocation.utils.PropertyUtils;

public class IplocationServiceTest extends BaseTest {

	@Autowired
	private IpLocationService ipLocationService;
	@Autowired
	private LD3Service ld3Service;
	@Autowired
	private ServerGroupService serverGroupService;
	
	@Test
	public void getLalByDateTest() {
		MapLevelsRequest request = new MapLevelsRequest();
		request.setSqlDate(DateFormatUtils.getThreeDayAgo());
		try {
			System.out.println(ipLocationService.getMaskcanvas(request));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getLD3() {
		MapLevelsRequest request = new MapLevelsRequest();
		request.setSqlDate(DateFormatUtils.getThreeDayAgo());
		try {
			System.out.println(ld3Service.getLD3(request));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getData() {
		serverGroupService.getData("2017-08-22");
	}
}
