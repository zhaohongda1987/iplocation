'use strict';

/*global L */
var map = L.map('map').setView([52.51538, 13.40997], 3);

L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
}).addTo(map);

var markers = L.geoJson(null, {
    pointToLayer: createClusterIcon
}).addTo(map);
markers.clearLayers();
markers.addData(ipData);

var index = supercluster({
	log : true,
	radius : 60,
	maxZoom : 18,
	minZoom : 3
}).load(ipData.features);

function update() {
	var bounds = map.getBounds();

	index.getClusters([ bounds.getWest(), bounds.getSouth(), bounds.getEast(),
			bounds.getNorth() ], map.getZoom());
}

map.on('moveend', update);

function createClusterIcon(feature, latlng) {
    if (!feature.properties.cluster) return L.marker(latlng);

    var count = feature.properties.point_count;
    var size =
        count < 100 ? 'small' :
        count < 1000 ? 'medium' : 'large';
    var icon = L.divIcon({
        html: '<div><span>' + feature.properties.point_count_abbreviated + '</span></div>',
        className: 'marker-cluster marker-cluster-' + size,
        iconSize: L.point(40, 40)
    });
    return L.marker(latlng, {icon: icon});
}