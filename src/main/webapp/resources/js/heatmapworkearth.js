var map = new WE.map('earth_div');
WE.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png').addTo(map);
map.setView([51.505, 0], 3);

var heat;
translateData(ipData);
function translateData(dt) {
	heat = L.heatLayer(dt).addTo(map);
	// process loading end
	map.spin(false);
};

// ajax
var oldZoomLevel = map.getZoom();
map.on('moveend', function changeZoomLevel() {
	var newZoomLevel = map.getZoom();
	if (newZoomLevel <= 6) {
		if (newZoomLevel != oldZoomLevel) {
			update();
		}
	} else {
		if (newZoomLevel == oldZoomLevel) {
			update();
		}
		if (oldZoomLevel <= 6) {
			update();
		}
	}
	oldZoomLevel = newZoomLevel;
});
function update() {
	// process loading start
	map.spin(true, {
		color : 'red'
	});
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
		url : "/heatmapworkearthajax",
		type : "POST",
		data : JSON.stringify(data),
		dataType : 'json',
		contentType : 'application/json;charset=UTF-8',
		success : function(result) {
			if (result.status) {
				translateData(result.ipData);
			}
		}
	});
};