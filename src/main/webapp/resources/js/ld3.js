var map = L.map("map-canvas", {
	center : [ 46.81509864599243, 8.3221435546875 ],
	zoom : 3,
	maxZoom : 18,
	minZoom : 3
});
var geo = L
.tileLayer(
		'http://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineStreetPurplishBlue/MapServer/tile/{z}/{y}/{x}',
		{
			attribution : 'Map data © <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
		}).addTo(map);
var osm = L
.tileLayer(
		'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
		{
			attribution : 'Map data © <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
		});
var baseLayers= {
"geo": geo,
"osm": osm	
}
var overlayLayers = {

}

L.control.layers(baseLayers,overlayLayers).addTo(map);

var citiesOverlay;
translateData(ipData);
function translateData(dt) {
	if (citiesOverlay) {
		citiesOverlay.onRemove(map);
	}
	citiesOverlay = L.d3SvgOverlay(function(sel, proj) {

		var minLogPop = Math.log2(d3.min(dt, function(d) {
			return d.population;
		}));
		var citiesUpd = sel.selectAll('circle').data(dt);
		citiesUpd.enter().append('circle').attr('r', function(d) {
			if (d.population > 1) {
				return (Math.log2(d.population) - minLogPop) * 0.5;
			} else {
				return 2;
			}
		}).attr('cx', function(d) {
			return proj.latLngToLayerPoint(d.latlng).x;
		}).attr('cy', function(d) {
			return proj.latLngToLayerPoint(d.latlng).y;
		}).attr('fill', function(d) {
			return "rgba(255, 0, 0, 0.3)";
		});
	});
	citiesOverlay.addTo(map);
	// process loading end
	map.spin(false);
}

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
		url : "/ld3ajax",
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
}