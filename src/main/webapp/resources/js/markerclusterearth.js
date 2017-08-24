'use strict';

/* global L */
var map = new WE.map('earth_div');
WE.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png').addTo(map);
map.setView([51.505, 0], 3);

var progress = document.getElementById('progress');
var progressBar = document.getElementById('progress-bar');

var markers = L.markerClusterGroup({
	chunkedLoading : true,
	chunkProgress : updateProgressBar,
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
translateData(ipData.dataarray);

function translateData(data) {
	markers.clearLayers();
	for (var i = 0; i < data.length; i++) {
		var a = data[i];
		createClusterIcon(a, a.latitude, a.longitude);
	}
	map.addLayer(markers);
	// process loading end
	map.spin(false);
}
var oldZoomLevel = map.getZoom();
map.on('moveend', function changeZoomLevel() {
	var newZoomLevel = map.getZoom();
	if (newZoomLevel == oldZoomLevel || newZoomLevel == 4 || newZoomLevel == 5
			|| newZoomLevel == 10 || newZoomLevel == 11) {
		update();
	}
	oldZoomLevel = newZoomLevel;
});
function update() {
	// process loading start
	map.spin(true);
	var bounds = map.getBounds();
	// bounds.getWest(), bounds.getSouth(), bounds.getEast(),bounds.getNorth()
	var zoomLevels = map.getZoom();
	var data = {
		northwestLng : bounds.getWest(),
		sourtheastLat : bounds.getSouth(),
		sourtheastLng : bounds.getEast(),
		northwestLat : bounds.getNorth(),
		zoomLevel : zoomLevels
	}
	$.ajax({
		url : "/markerclusterajax",
		type : "POST",
		data : JSON.stringify(data),
		dataType : 'json',
		contentType : 'application/json;charset=UTF-8',
		success : function(result) {
			if (result.status) {
				translateData(result.ipData.dataarray);
			}
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

function updateProgressBar(processed, total, elapsed, layersArray) {
	var progress = document.getElementById('progress');
	var progressBar = document.getElementById('progress-bar');
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