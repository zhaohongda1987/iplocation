'use strict';

var mapLayer;
// check basic data
if(ipData.dataarray != null) {
	translateMarkerData(ipData.dataarray);
}

//submit
$(".btn-search-comment").click(function() {
	//get map type
	var mapType = $('#mapType').val();
	if(mapType=='marker') {
		updateMarker();
	} else if(mapType=='heat') {
		updateHeat();
	} else if(mapType=='point') {
		updatePointMap();
	}
	// close iframe
	if(openIframe!=null) {
		layer.close(openIframe);
	}
});

//on change
function updateMap(date) {
	//get map type
	var mapType = $('#mapType').val();
	if(mapType=='marker') {
		updateMarker(date);
	} else if(mapType=='heat') {
		updateHeat(date);
	} else if(mapType=='point') {
		updatePointMap(date);
	}
	// close iframe
	if(openIframe!=null) {
		layer.close(openIframe);
	}
};

//move change
var oldZoomLevel = map.getZoom();
map.on('moveend', function changeZoomLevel() {
	//get map type
	var mapType = $('#mapType').val();
	var newZoomLevel = map.getZoom();
	if(mapType=='marker') {
		if (newZoomLevel < 11) {
			if (oldZoomLevel >= 11) {
				updateMarker();
			}
		} 
		if(newZoomLevel >= 11 && newZoomLevel < 14) {
			if (oldZoomLevel < 11) {
				updateMarker();
			}
			if (newZoomLevel == oldZoomLevel) {
				updateMarker();
			}
		}
	} else if(mapType=='heat') {
		if (newZoomLevel < 11) {
			if (oldZoomLevel >= 11) {
				updateHeat();
			}
		} else {
			if (oldZoomLevel < 11) {
				updateHeat();
			}
			if (newZoomLevel == oldZoomLevel) {
				updateHeat();
			}
		}
	} else if(mapType=='point') {
		if (newZoomLevel < 9) {
			if (oldZoomLevel >= 9) {
				updatePointMap();
			}
		} 
		if(newZoomLevel >= 9) {
			if (oldZoomLevel < 9) {
				updatePointMap();
			}
			if (newZoomLevel == oldZoomLevel) {
				updatePointMap();
			}
		}
	}
	oldZoomLevel = newZoomLevel;
});
// updateMarker
function updateMarker(date) {
	// process loading start
	var index = layer.load(0, {
		shade: [0.2,'#2F4056']
	});
	var ajaxData = getParams(index,date);
	$.ajax({
		url : "/iplocation/markerclusterajax",
		type : "POST",
		data : JSON.stringify(ajaxData),
		dataType : 'json',
		contentType : 'application/json;charset=UTF-8',
		success : function(result) {
			if (result.status) {
				translateMarkerData(result.ipData.dataarray);
			} else {
				alert("date error");
			}
			layer.close(index);
		}
	});
};
function translateMarkerData(data) {
	if (mapLayer!=null) {
		map.removeLayer(mapLayer);
	}
	mapLayer = L.markerClusterGroup({
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
	var markerList = [];
	for (var i = 0; i < data.length; i++) {
		var a = data[i];
		var title = a.describe;
		var marker = createClusterIcon(a, a.latitude, a.longitude);
		marker.bindPopup(title);
		markerList.push(marker);
	}
	mapLayer.addLayers(markerList);
	map.addLayer(mapLayer);
}
//create cluster
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
	return marker;
}
//process loading
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

// update heat
function updateHeat(date) {
	// process loading start
	var index = layer.load(0, {
		shade : [ 0.2, '#2F4056' ]
	});
	var ajaxData = getParams(index,date);
	$.ajax({
		url : "/iplocation/heatmapworkajax",
		type : "POST",
		data : JSON.stringify(ajaxData),
		dataType : 'json',
		contentType : 'application/json;charset=UTF-8',
		success : function(result) {
			if (result.status) {
				translateHeatData(result.ipData);
			} else {
				alert("date error");
			}
			layer.close(index);
		}
	});
};
function translateHeatData(dt) {
	if (mapLayer!=null) {
		map.removeLayer(mapLayer);
	}
	mapLayer = L.heatLayer(dt);
	map.addLayer(mapLayer);
};

// update point map
function updatePointMap(date) {
	// process loading start
	var index = layer.load(0, {
		shade : [ 0.2, '#2F4056' ]
	});
	var ajaxData = getParams(index,date);
	$.ajax({
		url : "/iplocation/pointMapWorkajax",
		type : "POST",
		data : JSON.stringify(ajaxData),
		dataType : 'json',
		contentType : 'application/json;charset=UTF-8',
		success : function(result) {
			if (result.status) {
				translatePointMap(result.ipData);
			} else {
				alert("date error");
			}
			layer.close(index);
		}
	});
};
function translatePointMap(dt) {
	if (mapLayer!=null) {
		map.removeLayer(mapLayer);
	}
	var pointMarkerList = [];
	for (var i = 0; i < dt.length; i++) {
		var a = dt[i];
		var pulsingIcon = L.icon.pulse({iconSize:[a.size,a.size],color:a.color});
		var pointMarker = L.marker([a.latitude,a.longitude],{icon: pulsingIcon,title:a.title});
		pointMarkerList.push(pointMarker);
	}
	mapLayer = L.layerGroup(pointMarkerList); 
	map.addLayer(mapLayer);
};