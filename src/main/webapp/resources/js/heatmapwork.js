'use strict';

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