var lineChart = echarts.init(document.getElementById("line-chart"));
option = {
	title : {
		text : 'data trend',
		subtext : 'country'
	},
	tooltip : {
		trigger : 'axis'
	},
	legend : {
		data : lineData.cn
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
				type : [ 'line', 'bar' ]
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
	xAxis : [ {
		type : 'category',
		boundaryGap : false,
		data : lineData.dateArray
	} ],
	yAxis : [ {
		type : 'value',
		axisLabel : {
			formatter : '{value}'
		}
	} ],
	series : [ {
		name : 'user num',
		type : 'line',
		data : lineData.countArray
	} ]
};

lineChart.setOption(option);
