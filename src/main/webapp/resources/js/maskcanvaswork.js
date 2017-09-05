'use strict';

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
	var ajaxData = getParams();
	$.ajax({
		url : "/iplocation/maskcanvasajax",
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
}