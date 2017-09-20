package com.zoom.iplocation.dao.imp;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.util.CollectionUtils;

import com.zoom.iplocation.dao.IpLocationDao;
import com.zoom.iplocation.entity.ZmIpDetail;
import com.zoom.iplocation.entity.ZmZcServerGroup;
import com.zoom.iplocation.request.MapLevelsRequest;
import com.zoom.iplocation.utils.StringUtilsSelf;

public class IpLocationDaoImp extends JdbcDaoSupport implements IpLocationDao {

	@Override
	public List<ZmIpDetail> queryLal(MapLevelsRequest request) throws Exception {
		String sql = "select meeting_id,account_id,latitude,longitude from zmlog.zm_cache_ip_detail_"
				+ request.getSqlDate() + " where city!='' and (date between '" + request.getStartDate() + "' and '"
				+ request.getEndDate() + "') and (latitude between " + request.getSourtheastLat() + " and "
				+ request.getNorthwestLat() + ")" + " and (longitude between " + request.getNorthwestLng() + " and "
				+ request.getSourtheastLng() + ")";
		if (StringUtils.isNotBlank(request.getIpAddr())) {
			sql = sql + " and server_ip_addr='" + request.getIpAddr() + "'";
		}
		if (StringUtils.isNotBlank(request.getMeetingId())) {
			sql = sql + " and meeting_id='" + request.getMeetingId() + "'";
		}
		if (StringUtils.isNotBlank(request.getServerGroup())) {
			sql = sql + " and server_group='" + request.getServerGroup() + "'";
		}
		if (StringUtils.isNotBlank(request.getAccountId())) {
			if (StringUtilsSelf.numFormat(request.getAccountId())) {
				sql = sql + " and account_id_num='" + request.getAccountId() + "'";
			} else {
				sql = sql + " and account_id='" + request.getAccountId() + "'";
			}
		}
		if (request.getAccountType() != null) {
			sql = sql + " and account_type in (";
			for (int i = 0; i < request.getAccountType().size(); i++) {
				if (i == request.getAccountType().size() - 1) {
					sql = sql + request.getAccountType().get(i) + ")";
				} else {
					sql = sql + request.getAccountType().get(i) + ",";
				}
			}
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
		String sql = "select avg(b.latitude) as latitude,avg(b.longitude) as longitude,count(*) as ip_count, a.cn from zmlog.zm_cache_ip_detail_"
				+ request.getSqlDate()
				+ " as a LEFT JOIN zmlog.zm_cn_location as b on b.cn=a.cn where a.latitude is not null and (a.date between '"
				+ request.getStartDate() + "' and '" + request.getEndDate() + "')";
		if (StringUtils.isNotBlank(request.getIpAddr())) {
			sql = sql + " and a.server_ip_addr='" + request.getIpAddr() + "'";
		}
		if (StringUtils.isNotBlank(request.getMeetingId())) {
			sql = sql + " and a.meeting_id='" + request.getMeetingId() + "'";
		}
		if (StringUtils.isNotBlank(request.getServerGroup())) {
			sql = sql + " and a.server_group='" + request.getServerGroup() + "'";
		}
		if (StringUtils.isNotBlank(request.getAccountId())) {
			if (StringUtilsSelf.numFormat(request.getAccountId())) {
				sql = sql + " and a.account_id_num='" + request.getAccountId() + "'";
			} else {
				sql = sql + " and a.account_id='" + request.getAccountId() + "'";
			}
		}
		if (request.getAccountType() != null && !CollectionUtils.isEmpty(request.getAccountType())) {
			sql = sql + " and a.account_type in (";
			for (int i = 0; i < request.getAccountType().size(); i++) {
				if (i == request.getAccountType().size() - 1) {
					sql = sql + request.getAccountType().get(i) + ")";
				} else {
					sql = sql + request.getAccountType().get(i) + ",";
				}
			}
		}
		sql = sql + " group by a.cn order by ip_count desc";
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql);
		List<ZmIpDetail> zmImDetails = new ArrayList<ZmIpDetail>();
		for (Map<String, Object> row : list) {
			ZmIpDetail zmIpDetail = new ZmIpDetail();
			zmIpDetail.setCn(row.get("cn").toString());
			Long ipCount = (Long) row.get("ip_count");
			zmIpDetail.setIpCount(ipCount.intValue());
			BigDecimal latitude = (BigDecimal) row.get("latitude");
			zmIpDetail.setLatitude(latitude.doubleValue());
			BigDecimal longitude = (BigDecimal) row.get("longitude");
			zmIpDetail.setLongitude(longitude.doubleValue());

			zmImDetails.add(zmIpDetail);
		}
		return zmImDetails;
	}
	
	@Override
	public List<ZmIpDetail> queryLineData(MapLevelsRequest request) throws Exception {
		String sql = "select avg(b.latitude) as latitude,avg(b.longitude) as longitude,count(*) as ip_count, a.date from zmlog.zm_cache_ip_detail_"
				+ request.getSqlDate()
				+ " as a LEFT JOIN zmlog.zm_cn_location as b on b.cn=a.cn where a.latitude is not null and (a.date between '"
				+ request.getStartDate() + "' and '" + request.getEndDate() + "')";
		if (StringUtils.isNotBlank(request.getIpAddr())) {
			sql = sql + " and a.server_ip_addr='" + request.getIpAddr() + "'";
		}
		if (StringUtils.isNotBlank(request.getMeetingId())) {
			sql = sql + " and a.meeting_id='" + request.getMeetingId() + "'";
		}
		if (StringUtils.isNotBlank(request.getServerGroup())) {
			sql = sql + " and a.server_group='" + request.getServerGroup() + "'";
		}
		if (StringUtils.isNotBlank(request.getAccountId())) {
			if (StringUtilsSelf.numFormat(request.getAccountId())) {
				sql = sql + " and a.account_id_num='" + request.getAccountId() + "'";
			} else {
				sql = sql + " and a.account_id='" + request.getAccountId() + "'";
			}
		}
		if (request.getAccountType() != null && !CollectionUtils.isEmpty(request.getAccountType())) {
			sql = sql + " and a.account_type in (";
			for (int i = 0; i < request.getAccountType().size(); i++) {
				if (i == request.getAccountType().size() - 1) {
					sql = sql + request.getAccountType().get(i) + ")";
				} else {
					sql = sql + request.getAccountType().get(i) + ",";
				}
			}
		}
		if(StringUtils.isNotBlank(request.getCn())) {
			sql = sql + " and a.cn='" + request.getCn() + "'";
		}
		sql = sql + " group by a.date order by a.date";
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql);
		List<ZmIpDetail> zmImDetails = new ArrayList<ZmIpDetail>();
		for (Map<String, Object> row : list) {
			ZmIpDetail zmIpDetail = new ZmIpDetail();
			zmIpDetail.setDate(row.get("date").toString());
			Long ipCount = (Long) row.get("ip_count");
			zmIpDetail.setIpCount(ipCount.intValue());
			BigDecimal latitude = (BigDecimal) row.get("latitude");
			zmIpDetail.setLatitude(latitude.doubleValue());
			BigDecimal longitude = (BigDecimal) row.get("longitude");
			zmIpDetail.setLongitude(longitude.doubleValue());

			zmImDetails.add(zmIpDetail);
		}
		return zmImDetails;
	}

	@Override
	public List<ZmIpDetail> queryCity(MapLevelsRequest request) throws Exception {
		String sql = "select avg(latitude) as latitude,avg(longitude) as longitude,city,count(ip_addr) as ip_count "
				+ "from zmlog.zm_cache_ip_detail_" + request.getSqlDate()
				+ " where city!='' and cn!='' and (latitude between " + request.getSourtheastLat() + " and "
				+ request.getNorthwestLat() + ") " + " and (longitude between " + request.getNorthwestLng() + " and "
				+ request.getSourtheastLng() + ") and (date between '" + request.getStartDate() + "' and '"
				+ request.getEndDate() + "')";
		if (StringUtils.isNotBlank(request.getIpAddr())) {
			sql = sql + " and server_ip_addr='" + request.getIpAddr() + "'";
		}
		if (StringUtils.isNotBlank(request.getMeetingId())) {
			sql = sql + " and meeting_id='" + request.getMeetingId() + "'";
		}
		if (StringUtils.isNotBlank(request.getServerGroup())) {
			sql = sql + " and server_group='" + request.getServerGroup() + "'";
		}
		if (StringUtils.isNotBlank(request.getAccountId())) {
			if (StringUtilsSelf.numFormat(request.getAccountId())) {
				sql = sql + " and account_id_num='" + request.getAccountId() + "'";
			} else {
				sql = sql + " and account_id='" + request.getAccountId() + "'";
			}
		}
		if (request.getAccountType() != null) {
			sql = sql + " and account_type in (";
			for (int i = 0; i < request.getAccountType().size(); i++) {
				if (i == request.getAccountType().size() - 1) {
					sql = sql + request.getAccountType().get(i) + ")";
				} else {
					sql = sql + request.getAccountType().get(i) + ",";
				}
			}
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
	public List<ZmIpDetail> queryLalGroup(MapLevelsRequest request) throws Exception {
		String sql = "select latitude,longitude,count(*) as ip_count from zmlog.zm_cache_ip_detail_"
				+ request.getSqlDate() + " where city!='' " + " and (latitude between " + request.getSourtheastLat()
				+ " and " + request.getNorthwestLat() + ") " + "		and (longitude between "
				+ request.getNorthwestLng() + " and " + request.getSourtheastLng() + ") and (date between '"
				+ request.getStartDate() + "' and '" + request.getEndDate() + "')";
		if (StringUtils.isNotBlank(request.getIpAddr())) {
			sql = sql + " and server_ip_addr='" + request.getIpAddr() + "'";
		}
		if (StringUtils.isNotBlank(request.getMeetingId())) {
			sql = sql + " and meeting_id='" + request.getMeetingId() + "'";
		}
		if (StringUtils.isNotBlank(request.getServerGroup())) {
			sql = sql + " and server_group='" + request.getServerGroup() + "'";
		}
		if (StringUtils.isNotBlank(request.getAccountId())) {
			if (StringUtilsSelf.numFormat(request.getAccountId())) {
				sql = sql + " and account_id_num='" + request.getAccountId() + "'";
			} else {
				sql = sql + " and account_id='" + request.getAccountId() + "'";
			}
		}
		if (request.getAccountType() != null) {
			sql = sql + " and account_type in (";
			for (int i = 0; i < request.getAccountType().size(); i++) {
				if (i == request.getAccountType().size() - 1) {
					sql = sql + request.getAccountType().get(i) + ")";
				} else {
					sql = sql + request.getAccountType().get(i) + ",";
				}
			}
		}
		sql = sql + " group by latitude,longitude";
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
		String sql = "select avg(latitude) as latitude,avg(longitude) as longitude,city,count(ip_addr) as ip_count \r\n"
				+ "from zmlog.zm_cache_ip_detail_" + request.getSqlDate()
				+ " where city!='' and cn!='' and (date between '" + request.getStartDate() + "' and '"
				+ request.getEndDate() + "')";
		if (StringUtils.isNotBlank(request.getIpAddr())) {
			sql = sql + " and server_ip_addr='" + request.getIpAddr() + "'";
		}
		if (StringUtils.isNotBlank(request.getMeetingId())) {
			sql = sql + " and meeting_id='" + request.getMeetingId() + "'";
		}
		if (StringUtils.isNotBlank(request.getServerGroup())) {
			sql = sql + " and server_group='" + request.getServerGroup() + "'";
		}
		if (StringUtils.isNotBlank(request.getAccountId())) {
			if (StringUtilsSelf.numFormat(request.getAccountId())) {
				sql = sql + " and account_id_num='" + request.getAccountId() + "'";
			} else {
				sql = sql + " and account_id='" + request.getAccountId() + "'";
			}
		}
		if (request.getAccountType() != null) {
			sql = sql + " and account_type in (";
			for (int i = 0; i < request.getAccountType().size(); i++) {
				if (i == request.getAccountType().size() - 1) {
					sql = sql + request.getAccountType().get(i) + ")";
				} else {
					sql = sql + request.getAccountType().get(i) + ",";
				}
			}
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
		String sql = "insert into zmlog.server_group (address,location,ip_addr) values(?,?,?)";
		getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ZmZcServerGroup zmZcServerGroup = zmZcServerGroupList.get(i);
				ps.setString(1, zmZcServerGroup.getAddress());
				ps.setString(2, zmZcServerGroup.getLocation());
				ps.setString(3, zmZcServerGroup.getIpAddr());
			}

			@Override
			public int getBatchSize() {
				return zmZcServerGroupList.size();
			}
		});
	}

	@Override
	public Map<String, Set<String>> querySeverGroupIp() throws Exception {
		String sql = "select ip_addr,location,count(*) as num from zmlog.server_group group by ip_addr,location";
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql);

		Map<String, Set<String>> result = new HashMap<>();
		for (Map<String, Object> row : list) {
			if (row.get("location") != null && row.get("ip_addr") != null) {
				String key = row.get("location").toString();
				Set<String> setList;
				if (result.containsKey(key)) {
					setList = result.get(key);
					setList.add(row.get("ip_addr").toString());
				} else {
					setList = new HashSet<>();
					setList.add(row.get("ip_addr").toString());
				}
				result.put(key, setList);
			}
		}
		return result;
	}
}
