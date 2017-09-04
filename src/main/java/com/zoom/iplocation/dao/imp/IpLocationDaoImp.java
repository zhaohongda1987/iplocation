package com.zoom.iplocation.dao.imp;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.zoom.iplocation.dao.IpLocationDao;
import com.zoom.iplocation.entity.ZmIpDetail;
import com.zoom.iplocation.entity.ZmZcServerGroup;
import com.zoom.iplocation.request.MapLevelsRequest;

public class IpLocationDaoImp extends JdbcDaoSupport implements IpLocationDao {

	@Override
	public List<ZmIpDetail> queryLal(MapLevelsRequest request) throws Exception {
		String sql = "select meeting_id,account_id,latitude,longitude from zmlog.zm_tm_ip_detail_"
				+ request.getSearchDate() + " where city!='' \r\n" + "        and (latitude between "
				+ request.getSourtheastLat() + " and " + request.getNorthwestLat() + ") \r\n"
				+ "        and (longitude between " + request.getNorthwestLng() + " and " + request.getSourtheastLng()
				+ ")";
		if (StringUtils.isNotBlank(request.getIpAddr())) {
			sql = sql + " and ip_addr='" + request.getIpAddr() + "'";
		}
		if (StringUtils.isNotBlank(request.getMeetingId())) {
			sql = sql + " and meeting_id='" + request.getMeetingId() + "'";
		}
		if (StringUtils.isNotBlank(request.getServerGroup())) {
			sql = sql + " and server_group='" + request.getServerGroup() + "'";
		}
		if (StringUtils.isNotBlank(request.getAccountId())) {
			sql = sql + " and account_id like'" + request.getAccountId() + "%'";
		}
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql);
		List<ZmIpDetail> zmImDetails = new ArrayList<ZmIpDetail>();
		for (Map<String, Object> row : list) {
			ZmIpDetail zmIpDetail = new ZmIpDetail();
			BigDecimal latitude = (BigDecimal) row.get("latitude");
			zmIpDetail.setLatitude(latitude.doubleValue());
			BigDecimal longitude = (BigDecimal) row.get("longitude");
			zmIpDetail.setLongitude(longitude.doubleValue());

			zmIpDetail.setDescribe("meeting_id:" + row.get("meeting_id").toString() + ";account_id:"
					+ row.get("account_id").toString());
			zmImDetails.add(zmIpDetail);
		}
		return zmImDetails;
	}

	@Override
	public List<ZmIpDetail> queryCountry(MapLevelsRequest request) throws Exception {
		String sql = "select count(*) as ip_count, cn from zmlog.zm_tm_ip_detail_" + request.getSearchDate()
				+ " where latitude is not null";
		if (StringUtils.isNotBlank(request.getIpAddr())) {
			sql = sql + " and ip_addr='" + request.getIpAddr() + "'";
		}
		if (StringUtils.isNotBlank(request.getMeetingId())) {
			sql = sql + " and meeting_id='" + request.getMeetingId() + "'";
		}
		if (StringUtils.isNotBlank(request.getServerGroup())) {
			sql = sql + " and server_group='" + request.getServerGroup() + "'";
		}
		if (StringUtils.isNotBlank(request.getAccountId())) {
			sql = sql + " and account_id like'" + request.getAccountId() + "%'";
		}
		sql = sql + " group by cn order by ip_count desc";
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql);
		List<ZmIpDetail> zmImDetails = new ArrayList<ZmIpDetail>();
		for (Map<String, Object> row : list) {
			ZmIpDetail zmIpDetail = new ZmIpDetail();
			zmIpDetail.setCn(row.get("cn").toString());
			Long ipCount = (Long) row.get("ip_count");
			zmIpDetail.setIpCount(ipCount.intValue());

			zmImDetails.add(zmIpDetail);
		}
		return zmImDetails;
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
		if (StringUtils.isNotBlank(request.getMeetingId())) {
			sql = sql + " and meeting_id='" + request.getMeetingId() + "'";
		}
		if (StringUtils.isNotBlank(request.getServerGroup())) {
			sql = sql + " and server_group='" + request.getServerGroup() + "'";
		}
		if (StringUtils.isNotBlank(request.getAccountId())) {
			sql = sql + " and account_id like'" + request.getAccountId() + "%'";
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

			zmIpDetail.setDescribe("region cluster");

			zmImDetails.add(zmIpDetail);
		}
		return zmImDetails;
	}

	@Override
	public List<ZmIpDetail> queryLD3(MapLevelsRequest request) throws Exception {
		// String sql = "select min(b.latitude) as latitude,min(b.longitude) as
		// longitude,a.city,count(a.ip_addr) as ip_count \r\n"
		// + " from zmlog.zm_tm_ip_detail_" + request.getSearchDate() + " as a \r\n"
		// + " LEFT JOIN zmlog.zm_city_location as b on a.city=b.city \r\n"
		// + " where a.city!='' and a.cn!='' and b.country=a.cn";
		String sql = "select avg(latitude) as latitude,avg(longitude) as longitude,city,count(ip_addr) as ip_count \r\n"
				+ "from zmlog.zm_tm_ip_detail_" + request.getSearchDate() + " where city!='' and cn!='' \r\n";
		if (StringUtils.isNotBlank(request.getIpAddr())) {
			sql = sql + " and ip_addr='" + request.getIpAddr() + "'";
		}
		if (StringUtils.isNotBlank(request.getMeetingId())) {
			sql = sql + " and meeting_id='" + request.getMeetingId() + "'";
		}
		if (StringUtils.isNotBlank(request.getServerGroup())) {
			sql = sql + " and server_group='" + request.getServerGroup() + "'";
		}
		if (StringUtils.isNotBlank(request.getAccountId())) {
			sql = sql + " and account_id like'" + request.getAccountId() + "%'";
		}
		sql = sql + " group by city,cn";
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

			zmIpDetail.setDescribe("city:" + zmIpDetail.getCity() + ";cn:" + zmIpDetail.getCn());

			zmImDetails.add(zmIpDetail);
		}
		return zmImDetails;
	}

	@Override
	public List<Map<String, Object>> querySdkAttendee(String date) throws Exception {
		String sql = "select mmr_cmd_address, count(*) as total from zmlog.mmrlog_sdk_attendee_join a where a.date='"
				+ date + "' group by  mmr_cmd_address ";
		return this.getJdbcTemplate().queryForList(sql);
	}

	public void insertServerGroupTmp(List<ZmZcServerGroup> zmZcServerGroupList) throws Exception {
		String sql = "insert into zmlog.server_group (address,location) values(?,?)";
		getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ZmZcServerGroup zmZcServerGroup = zmZcServerGroupList.get(i);
				ps.setString(1, zmZcServerGroup.getAddress());
				ps.setString(2, zmZcServerGroup.getLocation());
			}

			@Override
			public int getBatchSize() {
				return zmZcServerGroupList.size();
			}
		});
	}
}
