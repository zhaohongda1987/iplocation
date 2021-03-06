package com.zoom.iplocation.web;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zoom.iplocation.request.MapLevelsRequest;
import com.zoom.iplocation.service.HeatMapService;
import com.zoom.iplocation.utils.ControllerUtils;
@Controller
public class HeatMapController {
	@Autowired
	private HeatMapService heatMapService;
	
	private static Logger LOG = LoggerFactory.getLogger(HeatMapController.class);
	
	@RequestMapping(value = "/heatmap", method = RequestMethod.GET)
    private ModelAndView getHeatmapwork() {
		ModelAndView mv = new ModelAndView("heatmap");
		try {
			MapLevelsRequest request = new MapLevelsRequest();
			request = ControllerUtils.getBasicMapLevelsRequest(request);
			mv.addObject("ipData", heatMapService.getHeatMap(request).toString());
		} catch (Exception e) {
			mv.addObject("ipData",  new JSONObject());
			LOG.error("heatmap:", e);
		}
		return mv;
    }
	
	@RequestMapping(value = "/heatmapworkajax", method = RequestMethod.POST,consumes = "application/json") 
    @ResponseBody
    public Object getHeatmapworkajax(@RequestBody MapLevelsRequest request) { 
		JSONObject response = new JSONObject();
		try {
			request = ControllerUtils.getBasicMapLevelsRequest(request);
			response.put("ipData", heatMapService.getHeatMapAjax(request));
			response.put("status", true);
		} catch (Exception e) {
			response.put("status", false);
			LOG.error("heatmapworkajax:", e);
		}
		return response.toString();
	}
	
	@RequestMapping(value = "/heatmapworkearth", method = RequestMethod.GET)
    private ModelAndView getHeatmapworkearth() {
		ModelAndView mv = new ModelAndView("heatmapworkearth");
		try {
			MapLevelsRequest request = new MapLevelsRequest();
			request = ControllerUtils.getBasicMapLevelsRequest(request);
			mv.addObject("ipData", heatMapService.getHeatMap(request).toString());
		} catch (Exception e) {
			LOG.error("heatmapworkearth:", e);
		}
		return mv;
    }
	
	@RequestMapping(value = "/heatmapworkearthajax", method = RequestMethod.POST,consumes = "application/json") 
    @ResponseBody
    public Object getHeatmapworkearthAjax(@RequestBody MapLevelsRequest request) { 
		JSONObject response = new JSONObject();
		try {
			request = ControllerUtils.getBasicMapLevelsRequest(request);
			response.put("ipData", heatMapService.getHeatMapAjax(request));
			response.put("status", true);
		} catch (Exception e) {
			response.put("status", false);
		}
		return response.toString();
	}
}
