layui.use([ 'element', 'table' ], function() {
	var $ = layui.jquery, element = layui.element, table = layui.table;
	// hash get lay id
	var layId = location.hash.replace(/^#docDemoTabBrief=/, '');
	element.tabChange('docDemoTabBrief', layId);
	element.on('tab(docDemoTabBrief)', function() {
		var id = this.getAttribute('lay-id');
		if (id == 2) {
			drawPie(chartData.pieData);
		}
	});
	// table
	table.render({
		elem : '#layui-table',
		cols : [ [ {
			checkbox : false,
			fixed : true
		}, {
			field : 'country',
			title : 'country',
			width : 160,
			sort : true,
			fixed : true
		}, {
			field : 'num',
			title : 'num',
			width : 120,
			sort : true,
			fixed : true
		}, {
			fixed : 'right',
			width : 150,
			align : 'center',
			toolbar : '#barDemo'
		} ] ],
		data : chartData.tableData,
		page : true,
		loading : true,
		skin : 'line',
		even : true
	});
	table.on('tool(table-filter)', function(obj) {
		var data = obj.data;
		var layEvent = obj.event;
		var tr = obj.tr;

		if (layEvent === 'detail') {
			var oldUrlArray = window.location.href.split("?");
			var param = oldUrlArray[1]+"&cn="+data.country;
			var jumpUrl = window.location.origin+"/iplocation/linechart?"+param;
        	openIframe = layer.open({
        	      type: 2,
        	      title: 'line data',
        	      shadeClose: true,
        	      shade: false,
        	      maxmin: true, //开启最大化最小化按钮
        	      area: ['600px', '500px'],
        	      content: jumpUrl
        	});
		}
	});
	// pie chart
	var drawPie = function(config) {
		var myChart = echarts.init(document.getElementById("pieChart"));
		option = {
			title : {
				text : 'Country Data',
				subtext : 'top 10',
				x : 'center'
			},
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			legend : {
				orient : 'vertical',
				x : 'left',
				data : config.legend
			},
			toolbox : {
				show : true,
				feature : {
					mark : {
						show : true
					},
					dataView : {
						show : true,
						readOnly : false
					},
					magicType : {
						show : true,
						type : [ 'pie', 'funnel' ],
						option : {
							funnel : {
								x : '25%',
								width : '50%',
								funnelAlign : 'left',
								max : config.max,
								tooltip : {
									trigger : 'item',
									formatter : "{a} <br/>{b} : {c}"
								}
							}
						}
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : true,
			series : [ {
				name : 'Country Name',
				type : 'pie',
				radius : '55%',
				center : [ '50%', '60%' ],
				data : config.data
			} ]
		};
		myChart.setOption(option);
	};
	// active
	var active = {
		tabChange : function() {
			// change tab
			element.tabChange();
		}
	};
});