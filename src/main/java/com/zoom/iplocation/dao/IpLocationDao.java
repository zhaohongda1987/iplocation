package com.zoom.iplocation.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zoom.iplocation.entity.ZmIpDetail;
import com.zoom.iplocation.request.MapLevelsRequest;

public interface IpLocationDao {

	List<ZmIpDetail> queryLal(MapLevelsRequest request) throws Exception;
	
	List<ZmIpDetail> queryCountry(@Param("searchDate") String searchDate) throws Exception;
	
	List<ZmIpDetail> queryCity(MapLevelsRequest request) throws Exception;
	
	List<ZmIpDetail> queryLalGroup(MapLevelsRequest request) throws Exception;
	
	List<ZmIpDetail> queryLD3(MapLevelsRequest request) throws Exception;
}
