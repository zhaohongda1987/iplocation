$(function() {
    //============
    // Base Layers
    var osmUrl='http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
    var osmAttrib='Map data © <a href="http://osm.org/copyright">OpenStreetMap</a> contributors';
    var osm = new L.TileLayer(osmUrl, {
            attribution: osmAttrib
    });

    map = new L.Map('map', {
    	center: new L.LatLng(52.51538, 13.40997),
        zoom: 3,
        layers: [osm]
    });

    L.control.scale().addTo(map);

    //================
    // Set up overlays

    var initRadius = 800;
    $('input.range').attr('value', initRadius);

    var coverageLayer = new L.TileLayer.MaskCanvas(
    		{'opacity': 0.2,
    			radius: initRadius, 
    			useAbsoluteRadius: true, 
    			lineColor: 'rgba(2, 166, 253, 0.05)',
    			noMask: false,
    			color: 'rgba(255, 0, 0, 0.5)'});
    coverageLayer.setData(ipData);
    map.addLayer(coverageLayer);
});