package com.zoom.iplocation.request;

import java.util.List;

public class MapLevelsRequest {
	private Double northwestLng;
	private Double sourtheastLat;
	private Double sourtheastLng;
	private Double northwestLat;
	private Integer zoomLevel;
	private String startDate;
	private String endDate;
	private String ipAddr;
	private String meetingId;
	private String serverGroup;
	private String accountId;
	private String sqlDate;
	private List<String> accountType;
	private String cn;
	public Double getNorthwestLng() {
		return northwestLng;
	}
	public void setNorthwestLng(Double northwestLng) {
		this.northwestLng = northwestLng;
	}
	public Double getSourtheastLat() {
		return sourtheastLat;
	}
	public void setSourtheastLat(Double sourtheastLat) {
		this.sourtheastLat = sourtheastLat;
	}
	public Double getSourtheastLng() {
		return sourtheastLng;
	}
	public void setSourtheastLng(Double sourtheastLng) {
		this.sourtheastLng = sourtheastLng;
	}
	public Double getNorthwestLat() {
		return northwestLat;
	}
	public void setNorthwestLat(Double northwestLat) {
		this.northwestLat = northwestLat;
	}
	public Integer getZoomLevel() {
		return zoomLevel;
	}
	public void setZoomLevel(Integer zoomLevel) {
		this.zoomLevel = zoomLevel;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}
	public String getServerGroup() {
		return serverGroup;
	}
	public void setServerGroup(String serverGroup) {
		this.serverGroup = serverGroup;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getSqlDate() {
		return sqlDate;
	}
	public void setSqlDate(String sqlDate) {
		this.sqlDate = sqlDate;
	}
	public List<String> getAccountType() {
		return accountType;
	}
	public void setAccountType(List<String> accountType) {
		this.accountType = accountType;
	}
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
}
