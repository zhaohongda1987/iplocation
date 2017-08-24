'use strict';
importScripts('/resources/js/supercluster/supercluster.js');
var index;
self.onmessage = function(e) {
	index = supercluster({
		log : true,
		radius : 60,
		maxZoom : 18,
		minZoom : 3
	});
	if (e.data.ipdata) {
		index.load(e.data.ipdata.features);
		postMessage({
			ready : true
		});
	}
	if (e.data.bbox) {
		postMessage(index.getClusters(e.data.bbox, e.data.zoom));
	}
};