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

var progress = document.getElementById('progress');
var progressBar = document.getElementById('progress-bar');

var markers = L.markerClusterGroup({
	chunkedLoading : true,
	chunkProgress : updateProgressBar,
	maxClusterRadius : 40,
	iconCreateFunction : function(cluster) {
		var markers = cluster.getAllChildMarkers();
		var n = 0;
		for (var i = 0; i < markers.length; i++) {
			n += markers[i].number;
		}
		var size = n < 100 ? 'small' : n < 1000 ? 'medium' : 'large';
		return L.divIcon({
			html : '<div><span>' + n + '</span></div>',
			className : 'marker-cluster marker-cluster-' + size,
			iconSize : L.point(40, 40)
		});
	}
});
if(ipData.dataarray != null) {
	translateData(ipData.dataarray);	
}

function translateData(data) {
	markers.clearLayers();
	for (var i = 0; i < data.length; i++) {
		var a = data[i];
		createClusterIcon(a, a.latitude, a.longitude);
	}
	map.addLayer(markers);
}
// move change
var oldZoomLevel = map.getZoom();
map.on('moveend', function changeZoomLevel() {
	var newZoomLevel = map.getZoom();
	if (newZoomLevel < 11) {
		if (oldZoomLevel >= 11) {
			update();
		}
	} 
	if(newZoomLevel >= 11 && newZoomLevel < 14) {
		if (oldZoomLevel < 11) {
			update();
		}
		if (oldZoomLevel >= 14) {
			update();
		}
		if (newZoomLevel == oldZoomLevel) {
			update();
		}
	}
	if(newZoomLevel >= 14) {
		if (oldZoomLevel < 14) {
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
		url : "/iplocation/markerclusterajax",
		type : "POST",
		data : JSON.stringify(data),
		dataType : 'json',
		contentType : 'application/json;charset=UTF-8',
		success : function(result) {
			if (result.status) {
				translateData(result.ipData.dataarray);
			} else {
				alert("date error");
			}
			layer.close(index);
		}
	});
}

function createClusterIcon(data, latitude, longitude) {
	var marker;
	if (!data.ipcount) {
		marker = L.marker(L.latLng(latitude, longitude));
		marker.number = 1;
	} else {
		var count = data.ipcount;
		var size = count < 100 ? 'small' : count < 1000 ? 'medium' : 'large';
		var icon = L.divIcon({
			html : '<div><span>' + count + '</span></div>',
			className : 'marker-cluster marker-cluster-' + size,
			iconSize : L.point(40, 40)
		});
		marker = L.marker(L.latLng(latitude, longitude), {
			icon : icon
		});
		marker.number = count;
	}
	markers.addLayer(marker);
}
// process loading
function updateProgressBar(processed, total, elapsed, layersArray) {
	if (elapsed > 10) {
		// if it takes more than a second to load, display the progress bar:
		progress.style.display = 'block';
		progressBar.style.width = Math.round(processed / total * 100) + '%';
	}

	if (processed === total) {
		// all markers processed - hide the progress bar:
		progress.style.display = 'none';
	}
}