'use strict';
var map = L.map("map-canvas", {
	center : [ 30, 0 ],
	zoom : 3,
	maxZoom : 18,
	minZoom : 3,
	maxBounds: [[-90, -180],[90, 180]]
});
var geo = L
		.tileLayer(
				'http://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineStreetPurplishBlue/MapServer/tile/{z}/{y}/{x}',
				{
					attribution : 'Map data © <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
				});
var osm = L
		.tileLayer(
				'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
				{
					attribution : 'Map data © <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
				}).addTo(map);
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
//layui
var layer;
layui.use(['layer'], function(){
	layer = layui.layer
});
// full screen
var fsControl = new L.Control.FullScreen();
map.addControl(fsControl);
map.on('enterFullscreen', function() {
	document.getElementById("map-canvas").style.height = "100%";
	document.getElementById("map-canvas").style.width = "100%";
});
map.on('exitFullscreen', function() {
	document.getElementById("map-canvas").style.height = "84%";
	document.getElementById("map-canvas").style.width = "99%";
});

L.control.layers(baseLayers, overlayLayers).addTo(map);
//param
function getParams() {
	var bounds = map.getBounds();
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
	return data;
}
//easy button
var stateChangingButton = L.easyButton({
    states: [{
            stateName: 'show-data',
            icon:      'fa-table',
            title:     'show data',
            onClick: function(btn, map) {
            	// jump url
            	var params = getParams();
            	var jumpUrl = window.location.origin+'/iplocation/chartdata?'+encodeSearchParams(params);
            	layer.open({
            	      type: 2,
            	      title: 'country data',
            	      shadeClose: true,
            	      shade: false,
            	      maxmin: true, //开启最大化最小化按钮
            	      area: ['893px', '600px'],
            	      content: jumpUrl
            	});
            }
        }]
});
stateChangingButton.addTo(map);

var heat;
if (ipData != null) {
	translateData(ipData);
}
function translateData(dt) {
	if (heat == null) {
		heat = L.heatLayer(dt).addTo(map);
	} else {
		heat.setLatLngs(dt);
		heat.redraw();
	}
};

// move change
var oldZoomLevel = map.getZoom();
map.on('moveend', function changeZoomLevel() {
	var newZoomLevel = map.getZoom();
	if (newZoomLevel < 11) {
		if (oldZoomLevel >= 11) {
			update();
		}
	} else {
		if (oldZoomLevel < 11) {
			update();
		}
		if (newZoomLevel == oldZoomLevel) {
			update();
		}
	}
	oldZoomLevel = newZoomLevel;
});
// submit
$(".btn-search-comment").click(function() {
	update();
});

function update() {
	// process loading start
	var index = layer.load(0, {
		shade : [ 0.2, '#2F4056' ]
	});
	var ajaxData = getParams();
	$.ajax({
		url : "/iplocation/heatmapworkajax",
		type : "POST",
		data : JSON.stringify(ajaxData),
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
};
//get url join
function encodeSearchParams(obj) {
	const params = [];
	Object.keys(obj).forEach((key) => {
		let value = obj[key]
	    // 如果值为undefined我们将其置空
	    if (typeof value === 'undefined') {
	    	value = ''
	    }
	    // 对于需要编码的文本（比如说中文）我们要进行编码
	    params.push([key, encodeURIComponent(value)].join('='))
	 })
	 return params.join('&')
}