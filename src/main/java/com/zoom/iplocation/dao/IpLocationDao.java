package com.zoom.iplocation.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zoom.iplocation.entity.ZmIpDetail;
import com.zoom.iplocation.entity.ZmZcServerGroup;
import com.zoom.iplocation.request.MapLevelsRequest;

public interface IpLocationDao {

	List<ZmIpDetail> queryLal(MapLevelsRequest request) throws Exception;
	
	List<ZmIpDetail> queryCountry(MapLevelsRequest request) throws Exception;
	
	List<ZmIpDetail> queryCity(MapLevelsRequest request) throws Exception;
	
	List<ZmIpDetail> queryLalGroup(MapLevelsRequest request) throws Exception;
	
	List<ZmIpDetail> queryLD3(MapLevelsRequest request) throws Exception;
	
	List<Map<String, Object>> querySdkAttendee(String date) throws Exception;
	
	void insertServerGroupTmp(List<ZmZcServerGroup> zmZcServerGroupList) throws Exception;
	
	Map<String,Set<String>> querySeverGroupIp() throws Exception;
	
	List<ZmIpDetail> queryLineData(MapLevelsRequest request) throws Exception;
}
