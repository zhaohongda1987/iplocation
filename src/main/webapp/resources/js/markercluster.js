// ============
// Base Layers
var osmUrl = 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
var osmAttrib = 'Map data © <a href="http://osm.org/copyright">OpenStreetMap</a> contributors';
var osm = new L.TileLayer(osmUrl, {
	maxZoom : 18,
	attribution : osmAttrib,
	minZoom : 3
});

map = new L.Map('map', {
	center : new L.LatLng(52.51538, 13.40997),
	zoom : 3,
	layers : [ osm ]
});

var progress = document.getElementById('progress');
var progressBar = document.getElementById('progress-bar');

function updateProgressBar(processed, total, elapsed, layersArray) {
	if (elapsed > 1000) {
		// if it takes more than a second to load, display the progress bar:
		progress.style.display = 'block';
		progressBar.style.width = Math.round(processed / total * 100) + '%';
	}

	if (processed === total) {
		// all markers processed - hide the progress bar:
		progress.style.display = 'none';
	}
}

var markers = L.markerClusterGroup({
	chunkedLoading : true,
	chunkProgress : updateProgressBar
});

var markerList = [];

// console.log('start creating markers: ' + window.performance.now());

for (var i = 0; i < ipData.length; i++) {
	var a = ipData[i];
	var title = "test";
	var marker = L.marker(L.latLng(a[0], a[1]), {
		title : title
	});
	marker.bindPopup(title);
	markerList.push(marker);
}
//console.log(markerList);
// console.log('start clustering: ' + window.performance.now());

markers.addLayers(markerList);
map.addLayer(markers);

// console.log('end clustering: ' + window.performance.now());