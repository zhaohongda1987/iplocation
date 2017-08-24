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
// full screen
var fsControl = new L.Control.FullScreen();
map.addControl(fsControl);
map.on('enterFullscreen', function() {
	document.getElementById("map-canvas").style.height = "100%";
	document.getElementById("map-canvas").style.width = "100%";
});
map.on('exitFullscreen', function() {
	document.getElementById("map-canvas").style.height = "78%";
	document.getElementById("map-canvas").style.width = "99%";
});

L.control.layers(baseLayers, overlayLayers).addTo(map);
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
	// if (newZoomLevel <= 6) {
	// if (newZoomLevel != oldZoomLevel) {
	// update();
	// }
	// } else {
	// if (newZoomLevel == oldZoomLevel) {
	// update();
	// }
	// if (oldZoomLevel <= 6) {
	// update();
	// }
	// }
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
	var bounds = map.getBounds();
	// bounds.getWest(), bounds.getSouth(), bounds.getEast(),bounds.getNorth()
	var zoomLevels = map.getZoom();
	var selectDate = $('#startDate').val() || '';
	var ipAddr = $('#ipAddr').val() || '';
	var data = {
		northwestLng : bounds.getWest(),
		sourtheastLat : bounds.getSouth(),
		sourtheastLng : bounds.getEast(),
		northwestLat : bounds.getNorth(),
		zoomLevel : zoomLevels,
		searchDate : selectDate,
		ipAddr : ipAddr
	}
	$.ajax({
		url : "/heatmapworkajax",
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
};