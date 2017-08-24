<!DOCTYPE html>
<html>
<head>
<style type="text/css">
html, body {
	padding: 0;
	margin: 0;
	background-color: black;
}

#earth_div {
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	position: absolute !important;
}
</style>
<link rel="stylesheet" href="/resources/css/leaflet.css"
	integrity="sha512-07I2e+7D8p6he1SIM+1twR5TIrhUQn9+I6yjqD53JQjFiMf8EtC93ty0/5vJTZGF8aAocvHYNEDJajGdNx1IsQ=="
	crossorigin="" />
<script src="/resources/js/leaflet/leaflet-src.js"
	integrity="sha512-WXoSHqw/t26DszhdMhOXOkI7qCiv5QWXhH9R7CgvgZMHz1ImlkVQ3uNsiQKu5wwbbxtPzFXd1hK4tzno2VqhpA=="
	crossorigin=""></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="/resources/css/main.css" />
<link rel="stylesheet"
	href="/resources/css/markercluster/MarkerCluster.css" />
<link rel="stylesheet"
	href="/resources/css/markercluster/MarkerCluster.Default.css" />
<script src="/resources/js/earth/api.js"></script>
<script src="/resources/js/jquery/jquery-3.2.1.min.js"></script>
<script src="/resources/js/markercluster/leaflet.markercluster-src.js"></script>
<script src="/resources/js/leaflet/leaflet.spin.min.js"></script>
<script src="/resources/js/spin/spin.min.js"></script>
<script src="/resources/js/markerclusterearth.js" async defer></script>
</head>
<body>
	<div id="progress">
		<div id="progress-bar"></div>
	</div>
	<div id="earth_div"></div>
</body>
<script type="text/javascript">
	var ipData = ${ipData};
</script>
</html>