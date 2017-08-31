'use strict';
var map = L.map("map-canvas", {
	center : [ 30, 0 ],
	zoom : 3,
	maxZoom : 18,
	minZoom : 3,
	maxBounds: [[-90, -180],[90, 180]]
});
var osm = L
		.tileLayer(
				'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
				{
					attribution : 'Map data © <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
				}).addTo(map);
var geo = L
		.tileLayer(
				'http://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineStreetPurplishBlue/MapServer/tile/{z}/{y}/{x}',
				{
					attribution : 'Map data © <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
				});
var blackAndWhite = L
		.tileLayer(
				'http://{s}.www.toolserver.org/tiles/bw-mapnik/{z}/{x}/{y}.png',
				{
					attribution : '&copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>'
				});
var googleStreets = L.tileLayer(
		'http://{s}.google.com/vt/lyrs=m&x={x}&y={y}&z={z}', {
			subdomains : [ 'mt0', 'mt1', 'mt2', 'mt3' ]
		});
var googleHybrid = L.tileLayer(
		'http://{s}.google.com/vt/lyrs=s,h&x={x}&y={y}&z={z}', {
			subdomains : [ 'mt0', 'mt1', 'mt2', 'mt3' ]
		});
var googleSat = L.tileLayer(
		'http://{s}.google.com/vt/lyrs=s&x={x}&y={y}&z={z}', {
			subdomains : [ 'mt0', 'mt1', 'mt2', 'mt3' ]
		});
var googleTerrain = L.tileLayer(
		'http://{s}.google.com/vt/lyrs=p&x={x}&y={y}&z={z}', {
			subdomains : [ 'mt0', 'mt1', 'mt2', 'mt3' ]
		});

var baseLayers = {
	"osm" : osm,
	"geo" : geo,
	"blackAndWhite" : blackAndWhite,
	"googleStreets" : googleStreets,
	"googleHybrid" : googleHybrid,
	"googleSat" : googleSat,
	"googleTerrain" : googleTerrain
};
var overlayLayers = {

};
// full screen
var fsControl = new L.Control.FullScreen();
map.addControl(fsControl);
map.on('enterFullscreen', function(){
	document.getElementById("map-canvas").style.height="100%";
	document.getElementById("map-canvas").style.width="100%";
});
map.on('exitFullscreen', function(){
	document.getElementById("map-canvas").style.height="84%";
	document.getElementById("map-canvas").style.width="99%";
});

L.control.layers(baseLayers, overlayLayers).addTo(map);

//tool bar
//var LayerToolbar = new L.ToolbarAction({
//	toolbarIcon: {
//		html: '',
//		className: 'leaflet-layer-toolbar',
//		tooltip: ''
//	}
//})
//new L.Toolbar.Control({
//	position: 'topleft',
//	actions: [
//        LayerToolbar
//    ]
//}).addTo(map);
//$("#leaflet-layer-toolbar").click(function() {
//	layer.open({
//		type: 2,
//		title: false,
//		closeBtn: 0, //不显示关闭按钮
//		shade: [0],
//		area: ['340px', '215px'],
//		offset: 'rb', //右下角弹出
//		time: 2000, //2秒后自动关闭
//		anim: 2,
//		content: ['test/guodu.html', 'no'], //iframe的url，no代表不显示滚动条
//		end: function(){ //此处用于演示
//			layer.open({
//				type: 2,
//				title: '很多时候，我们想最大化看，比如像这个页面。',
//				shadeClose: true,
//				shade: false,
//				maxmin: true, //开启最大化最小化按钮
//				area: ['893px', '600px'],
//				content: '//fly.layui.com/'
//			});
//		}
//	});
//});

var coverageLayer;
function translateData(data) {
	if(coverageLayer != null) {
		coverageLayer.remove();
	}
	// maskcanvas
    var initRadius = 800;
    $('input.range').attr('value', initRadius);

    coverageLayer = new L.GridLayer.MaskCanvas({
    	opacity: 0.2,
    	radius: initRadius, 
    	useAbsoluteRadius: true, 
    	lineColor: '#A00',
    	noMask: false,
    	color: '#000'});
    coverageLayer.setData(data);
    map.addLayer(coverageLayer);
}

// submit
$(".btn-search-comment").click(function() {
	update();
});

function update() {
	// process loading start
	var index = layer.load(0, {
		shade: [0.2,'#2F4056']
	});
	var bounds = map.getBounds();
	// bounds.getWest(), bounds.getSouth(), bounds.getEast(),bounds.getNorth()
	var zoomLevels = map.getZoom();
	var selectDate = $('#startDate').val() || '';
	if (selectDate == '') {
		alert("date should not null");
		layer.close(index);
		return;
	}
	var ipAddr = $('#ipAddr').val() || '';
	var meetingId = $('#meetingId').val() || '';
	var serverGroup = $('#serverSelect').val() || '';
	var accountId = $('#accountId').val() || '';
	if(serverGroup=='all') {
		serverGroup="";
	}
	if (meetingId == '') {
		alert("meeting id should not null");
		layer.close(index);
		return;
	}
	var data = {
		northwestLng : bounds.getWest(),
		sourtheastLat : bounds.getSouth(),
		sourtheastLng : bounds.getEast(),
		northwestLat : bounds.getNorth(),
		zoomLevel : zoomLevels,
		searchDate : selectDate,
		ipAddr : ipAddr,
		meetingId : meetingId,
		serverGroup: serverGroup,
		accountId: accountId
	}
	$.ajax({
		url : "/iplocation/maskcanvasajax",
		type : "POST",
		data : JSON.stringify(data),
		dataType : 'json',
		contentType : 'application/json;charset=UTF-8',
		success : function(result) {
			if (result.status) {
				translateData(result.ipData);
			} else {
				alert("date error");
			}
			layer.close(index);
		}
	});
}