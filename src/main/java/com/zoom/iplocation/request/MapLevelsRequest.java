package com.zoom.iplocation.request;

public class MapLevelsRequest {
	private Double northwestLng;
	private Double sourtheastLat;
	private Double sourtheastLng;
	private Double northwestLat;
	private Integer zoomLevel;
	private String searchDate;
	private String ipAddr;
	private String meetingId;
	private String serverGroup;
	private String accountId;
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
	public String getSearchDate() {
		return searchDate;
	}
	public void setSearchDate(String searchDate) {
		this.searchDate = searchDate;
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
}
