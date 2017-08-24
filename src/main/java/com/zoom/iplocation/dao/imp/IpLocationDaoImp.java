package com.zoom.iplocation.dao.imp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.zoom.iplocation.dao.IpLocationDao;
import com.zoom.iplocation.entity.ZmIpDetail;
import com.zoom.iplocation.request.MapLevelsRequest;

public class IpLocationDaoImp extends JdbcDaoSupport implements IpLocationDao {

	@Override
	public List<ZmIpDetail> queryLal(MapLevelsRequest request) throws Exception {
		String sql = "select latitude,longitude from zmlog.zm_tm_ip_detail_" + request.getSearchDate()
				+ " where city!='' \r\n" + "        and (latitude between " + request.getSourtheastLat() + " and "
				+ request.getNorthwestLat() + ") \r\n" + "        and (longitude between " + request.getNorthwestLng()
				+ " and " + request.getSourtheastLng() + ")";
		if (StringUtils.isNotBlank(request.getIpAddr())) {
			sql = sql + " and ip_addr='" + request.getIpAddr() + "'";
		}
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql);
		List<ZmIpDetail> zmImDetails = new ArrayList<ZmIpDetail>();
		for (Map<String, Object> row : list) {
			ZmIpDetail zmIpDetail = new ZmIpDetail();
			BigDecimal latitude = (BigDecimal) row.get("latitude");
			zmIpDetail.setLatitude(latitude.doubleValue());
			BigDecimal longitude = (BigDecimal) row.get("longitude");
			zmIpDetail.setLongitude(longitude.doubleValue());

			zmImDetails.add(zmIpDetail);
		}
		return zmImDetails;
	}

	@Override
	public List<ZmIpDetail> queryCountry(String searchDate) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ZmIpDetail> queryCity(MapLevelsRequest request) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ZmIpDetail> queryLalGroup(MapLevelsRequest request) throws Exception {
		String sql = "select latitude,longitude,count(*) as ip_count from zmlog.zm_tm_ip_detail_"
				+ request.getSearchDate() + " where city!='' \r\n" + "		and (latitude between "
				+ request.getSourtheastLat() + " and " + request.getNorthwestLat() + ") \r\n"
				+ "		and (longitude between " + request.getNorthwestLng() + " and " + request.getSourtheastLng()
				+ ")";
		if (StringUtils.isNotBlank(request.getIpAddr())) {
			sql = sql + " and ip_addr='" + request.getIpAddr() + "'";
		}
		sql = sql + "		group by latitude,longitude";
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql);
		List<ZmIpDetail> zmImDetails = new ArrayList<ZmIpDetail>();
		for (Map<String, Object> row : list) {
			ZmIpDetail zmIpDetail = new ZmIpDetail();
			BigDecimal latitude = (BigDecimal) row.get("latitude");
			zmIpDetail.setLatitude(latitude.doubleValue());
			BigDecimal longitude = (BigDecimal) row.get("longitude");
			zmIpDetail.setLongitude(longitude.doubleValue());
			Long ipCount = (Long) row.get("ip_count");
			zmIpDetail.setIpCount(ipCount.intValue());

			zmImDetails.add(zmIpDetail);
		}
		return zmImDetails;
	}

	@Override
	public List<ZmIpDetail> queryLD3(MapLevelsRequest request) throws Exception{
		String sql = "select MIN(b.latitude) as latitude,MIN(b.longitude) as longitude,a.city,count(a.ip_addr) as ip_count \r\n"
				+ "        from zmlog.zm_tm_ip_detail_" + request.getSearchDate() + " as a \r\n"
				+ "        LEFT JOIN zmlog.zm_city_location as b on a.city=b.city \r\n"
				+ "        where a.city!=''";
		if (StringUtils.isNotBlank(request.getIpAddr())) {
			sql = sql + " and a.ip_addr='" + request.getIpAddr() + "'";
		}
		sql = sql + " group by a.city";
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql);
		List<ZmIpDetail> zmImDetails = new ArrayList<ZmIpDetail>();
		for (Map<String, Object> row : list) {
			ZmIpDetail zmIpDetail = new ZmIpDetail();
			BigDecimal latitude = (BigDecimal) row.get("latitude");
			zmIpDetail.setLatitude(latitude.doubleValue());
			BigDecimal longitude = (BigDecimal) row.get("longitude");
			zmIpDetail.setLongitude(longitude.doubleValue());
			zmIpDetail.setCity(row.get("city").toString());
			Long ipCount = (Long) row.get("ip_count");
			zmIpDetail.setIpCount(ipCount.intValue());

			zmImDetails.add(zmIpDetail);
		}
		return zmImDetails;
	}

}
