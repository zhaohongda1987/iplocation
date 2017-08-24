<html>
<head>
    <title>heatmap example</title>
    <style type="text/css">
        html { height: 100% }
        body { height: 100%; margin: 0; padding: 0 }
        #map-canvas { height: 100% }
    </style>
    <link href='/resources/css/leaflet.css'
    	rel='stylesheet' type='text/css'/>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<script src="/resources/js/leaflet/leaflet-src.js"></script>
	<script src="/resources/js/jquery/jquery-3.2.1.min.js"></script>
	<script src="/resources/js/leaflet/leaflet.spin.min.js"></script>
		<script src="/resources/js/spin/spin.min.js"></script>
	<script src="/resources/js/heatmap/leaflet-heat.js"></script>
	<script src="/resources/js/heatmapwork.js" defer></script>
</head>
<body>
  <div id="map-canvas"></div>
</body>
<script type="text/javascript">
	var ipData = ${ipData};
</script>
</html>