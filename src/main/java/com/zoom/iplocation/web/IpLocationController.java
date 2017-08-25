package com.zoom.iplocation.web;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zoom.iplocation.request.MapLevelsRequest;
import com.zoom.iplocation.service.IpLocationService;
import com.zoom.iplocation.utils.ControllerUtils;

@Controller
public class IpLocationController {
	@Autowired
	private IpLocationService ipLocationService;

	@RequestMapping(value = "/maskcanvas", method = RequestMethod.GET)
	private ModelAndView getMaskcanvas() {
		ModelAndView mv = new ModelAndView("maskcanvas");
		return mv;
	}

	@RequestMapping(value = "/maskcanvasajax", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Object getMaskCanvasAjax(@RequestBody MapLevelsRequest request) {
		JSONObject response = new JSONObject();
		try {
			request = ControllerUtils.getBasicMapLevelsRequest(request);
			response.put("ipData", ipLocationService.getMaskcanvas(request));
			response.put("status", true);
		} catch (Exception e) {
			response.put("status", false);
		}
		return response.toString();
	}
	//
	// @RequestMapping(value = "/getgeojson", method = RequestMethod.GET)
	// private ModelAndView getGeoJson() {
	// ModelAndView mv = new ModelAndView("maskcanvas");
	// try {
	// mv.addObject("ipData", ipLocationService.getMaskcanvas("2017-08-04"));
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return mv;
	// }

	@RequestMapping(value = "/markercluster", method = RequestMethod.GET)
	private ModelAndView getMarkercluster() {
		ModelAndView mv = new ModelAndView("markercluster");
		try {
			MapLevelsRequest request = new MapLevelsRequest();
			request = ControllerUtils.getBasicMapLevelsRequest(request);
			mv.addObject("ipData", ipLocationService.getMarkercluster(request));
		} catch (Exception e) {
			mv.addObject("ipData", new JSONObject());
			System.out.println(e.getMessage());
		}
		return mv;
	}

	@RequestMapping(value = "/markerclusterajax", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Object getMarkerclusterAjax(@RequestBody MapLevelsRequest request) {
		JSONObject response = new JSONObject();
		try {
			request = ControllerUtils.getBasicMapLevelsRequest(request);
			response.put("ipData", ipLocationService.getMarkercluster(request));
			response.put("status", true);
		} catch (Exception e) {
			response.put("status", false);
		}
		return response.toString();
	}

	@RequestMapping(value = "/markerclusterearth", method = RequestMethod.GET)
	private ModelAndView getMarkerclusterearth() {
		ModelAndView mv = new ModelAndView("markerclusterearth");
		try {
			MapLevelsRequest request = new MapLevelsRequest();
			request = ControllerUtils.getBasicMapLevelsRequest(request);
			mv.addObject("ipData", ipLocationService.getMarkercluster(request));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
}
