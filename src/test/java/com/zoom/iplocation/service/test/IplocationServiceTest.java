package com.zoom.iplocation.service.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zoom.iplocation.request.MapLevelsRequest;
import com.zoom.iplocation.service.IpLocationService;
import com.zoom.iplocation.service.LD3Service;
import com.zoom.iplocation.test.BaseTest;
import com.zoom.iplocation.utils.DateFormatUtils;

public class IplocationServiceTest extends BaseTest {

	@Autowired
	private IpLocationService ipLocationService;
	@Autowired
	private LD3Service ld3Service;
	
	@Test
	public void getLalByDateTest() {
		try {
			System.out.println(ipLocationService.getMaskcanvas("2017-08-04"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getLD3() {
		MapLevelsRequest request = new MapLevelsRequest();
		request.setSearchDate(DateFormatUtils.getLast2Day());
		try {
			System.out.println(ld3Service.getLD3(request));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
