var map = L.map('map');
var baseLayers = {
	"高德地图" : L
			.tileLayer(
					'http://webrd0{s}.is.autonavi.com/appmaptile?lang=zh_cn&size=1&scale=1&style=8&x={x}&y={y}&z={z}',
					{
						subdomains : "1234"
					}),
	'高德影像' : L
			.layerGroup([
					L
							.tileLayer(
									'http://webst0{s}.is.autonavi.com/appmaptile?style=6&x={x}&y={y}&z={z}',
									{
										subdomains : "1234"
									}),
					L
							.tileLayer(
									'http://t{s}.tianditu.cn/DataServer?T=cta_w&X={x}&Y={y}&L={z}',
									{
										subdomains : "1234"
									}) ]),
	"立体地图" : L
			.tileLayer(
					'https://a.tiles.mapbox.com/v3/examples.c7d2024a/{z}/{x}/{y}.png',
					{
						attribution : 'Map &copy; Pacific Rim Coordination Center (PRCC).  Certain data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>',
						key : 'BC9A493B41014CAABB98F0471D759707',
						styleId : 22677
					}),
	"Foursquare" : L
			.tileLayer(
					'https://a.tiles.mapbox.com/v3/foursquare.map-0y1jh28j/{z}/{x}/{y}.png',
					{
						attribution : 'Map &copy; Pacific Rim Coordination Center (PRCC).  Certain data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>',
						key : 'BC9A493B41014CAABB98F0471D759707',
						styleId : 22677
					}),
	"OSM" : L
			.tileLayer(
					'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
					{
						attribution : 'Map data © <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
					}),
	'GeoQ灰色底图' : L
			.tileLayer(
					'http://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineStreetPurplishBlue/MapServer/tile/{z}/{y}/{x}')
			.addTo(map)
};
L
		.tileLayer(
				'https://a.tiles.mapbox.com/v3/foursquare.map-0y1jh28j/{z}/{x}/{y}.png',
				{
					attribution : 'Map &copy; Pacific Rim Coordination Center (PRCC).  Certain data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>',
					key : 'BC9A493B41014CAABB98F0471D759707',
					styleId : 22677
				});
var layercontrol = L.control.layers(baseLayers, {
	position : "topleft"
}).addTo(map);
map.setView(L.latLng(37.550339, 104.114129), 3);

var overlay = new L.echartsLayer(map, echarts);
var chartsContainer = overlay.getEchartsContainer();
var myChart = overlay.initECharts(chartsContainer);
window.onresize = myChart.onresize;
var option = {
	title : {
		text : 'Echarts Map By Leaflet',
		subtext : '-- Develop By WanderGIS',
		x : 'center',
		y : 'top',
		textStyle : {
			color : '#FFC107'
		}
	},
	legend : {
		show : true,
		x : 'right',
		orient : 'vertical',
		textStyle : {
			color : 'red'
		},
		data : []
	},
	series : [ {
		name : "order process",
		type : 'map',
		mapType : 'none',
		itemStyle : {
			normal : {
				borderColor : 'rgba(100,149,237,0.2)',
				borderWidth : 0.5,
				areaStyle : {
					color : '#1b1b1b'
				}
			}
		},
		data : [ {} ],
		hoverable : false,
		clickable : false,
		roam : true,
		markLine : {
			effect : {
				color : 'rgba(204, 246, 255, 0.09)',
				show : true,
				period : 40
			},
			bundling : {
				enable : true
			},
			large : true,
			smooth : true,
			smoothness : 0.1,
			symbol : [ 'none', 'none' ],
			itemStyle : {
				normal : {
					lineStyle : {
						color : 'rgba(2, 166, 253, 0.05)',
						type : 'solid',
						width : 0.5,
						opacity : 0.2
					}
				}
			},
			data : []
		},
		markPoint : {
			symbol : 'circle',
			symbolSize : 1.5,
			itemStyle : {
				normal : {
					color : 'rgba(255, 0, 0, 0.5)'
				}
			},
			data : []
		}
	} ]
};

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
		url : 'http://7xp3u9.com1.z0.glb.clouddn.com/srcmigration.json',
		type : "post",
		date : JSON.stringify(data),
		dataType : 'json',
		success : function(data) {
			for ( var key in data) {
				data[key].forEach(function(value, index) {
					data[key][index].num = Number(value.num);
				})
			}
			option.series[0].markPoint.data = data.topCityOut.map(function(
					point) {
				return {
					geoCoord : getGeoCoord(point.name)
				}
			});
			overlay.setOption(option);
		}
	});
}