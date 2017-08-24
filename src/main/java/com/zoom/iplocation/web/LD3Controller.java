package com.zoom.iplocation.web;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zoom.iplocation.request.MapLevelsRequest;
import com.zoom.iplocation.service.LD3Service;
import com.zoom.iplocation.utils.DateFormatUtils;

@Controller
public class LD3Controller {
	@Autowired
	private LD3Service ld3Service;
	
//	@RequestMapping(value = "/ld3", method = RequestMethod.GET)
//    private ModelAndView getMaskcanvas() {
//		ModelAndView mv = new ModelAndView("ld3");
//		MapLevelsRequest request = new MapLevelsRequest();
//		request.setSearchDate(DateFormatUtils.getLast2Day());
//		try {
//			mv.addObject("ipData", ld3Service.getLD3(request).toString());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return mv;
//    }
//	
//	@RequestMapping(value = "/ld3ajax", method = RequestMethod.POST,consumes = "application/json") 
//    @ResponseBody
//    public Object getLd3Ajax(@RequestBody MapLevelsRequest request) { 
//		JSONObject response = new JSONObject();
//		try {
//			response.put("ipData", ld3Service.getLD3ByLat(request));
//			response.put("status", true);
//		} catch (Exception e) {
//			response.put("status", false);
//		}
//		return response.toString();
//	}
}
