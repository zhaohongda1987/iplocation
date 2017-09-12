package com.zoom.iplocation.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zoom.iplocation.dao.IpLocationDao;
import com.zoom.iplocation.dao.imp.JDBCDao;
import com.zoom.iplocation.entity.ZmZcServerGroup;
import com.zoom.iplocation.service.ServerGroupService;
import com.zoom.iplocation.utils.StringUtilsSelf;

@Service
public class ServerGroupServiceImp implements ServerGroupService {
	@Autowired
	private IpLocationDao ipLocationDao;
	private String ipPattern = ".*\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}.*";

	public void getData(String date) {
		List<Map<String,String>> ipList= new ArrayList<>();
		List<Map<String,String>> domainList= new ArrayList<>();
		List<ZmZcServerGroup> zmZcServerGroupList = new ArrayList<>();
		try {
			List<Map<String, Object>> tmpList = ipLocationDao.querySdkAttendee(date);
			for (Map<String, Object> tmpMap : tmpList) {
				String address = tmpMap.get("mmr_cmd_address").toString();
				if (Pattern.matches(ipPattern, address)) {
					ipList.add(StringUtilsSelf.getIpMap(address));
				} else {
					domainList.add(StringUtilsSelf.getDomainMap(address));
				}
			}
//			System.out.println(domainList.toString());
//			System.out.println(ipList.toString());
			for(Map<String, String> ipMap : ipList) {
				ZmZcServerGroup zmZcServerGroup = new ZmZcServerGroup();
				String ipLastNum = ipMap.get("ipLastNum");
				for(Map<String, String> domainMap : domainList) {
					String num = domainMap.get("num");
					if (num.equals(ipLastNum)) {
						zmZcServerGroup.setLocation(domainMap.get("location").toLowerCase());
					}
				}
				zmZcServerGroup.setAddress(ipMap.get("address"));
				zmZcServerGroup.setIpAddr(ipMap.get("ip"));
				
				zmZcServerGroupList.add(zmZcServerGroup);
			}
			for(Map<String, String> domainMap : domainList) {
				ZmZcServerGroup zmZcServerGroup = new ZmZcServerGroup();
				zmZcServerGroup.setAddress(domainMap.get("address"));
				zmZcServerGroup.setLocation(domainMap.get("location").toLowerCase());
				String num = domainMap.get("num");
				for(Map<String, String> ipMap : ipList) {
					String ipLastNum = ipMap.get("ipLastNum");
					if(ipLastNum.equals(num)) {
						zmZcServerGroup.setIpAddr(ipMap.get("ip"));
					}
				}
				
				zmZcServerGroupList.add(zmZcServerGroup);
			}
			// insert data
//			ipLocationDao.insertServerGroupTmp(zmZcServerGroupList);
			JDBCDao.batchInsertTable(zmZcServerGroupList);
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
