        

var  bg_pink = "#C476B2";
var  bg_green = "#76c8bd";
var  bg_info = "#1797be";
var  bg_yellow = "#D1CB26";
var  bg_red = "#D9001D";
var  bg_gray= "#D1D1D1";
var bg_orange = "#FFAE00";


gaugesPalette = ['#76c8bd', '#f7c676', '#FF0000', '#c5819a', '#96a3d4'];
piePalette = ['#76c8bd','#C5819A','#96A3D4'];
chartsPalette = ['#76c8bd', '#75c0e0', '#c5cce7'];
colors = {fontColor:'#a7acbc', gridColor:'#515873', bkgColor: '#363E5B', needle: '#ffffff', shutter: { color: '#363e5b', opacity: 0.65}};
fonts = {sliderMarker: {color: '#43474b',size: 11,weight: 400}}; 

palette = 'Dark Palette';
fontColor = '#a7acbc';
gridColor = '#515873';
bkgColor = '#363E5B';
labelTextColor = '#363e5b';
labelColor = 'white';

var darkPalette = ['#46508c', '#556fa6', '#5d8dbc', '#62b7db', '#70cdd6', '#8ccebb'], lightPalette = ['#737db5', '#7e93bf', '#8bafd2', '#90cce6', '#70cdd6', '#bae3d7'];
DevExpress.viz.registerPalette('Dark Palette', darkPalette);
DevExpress.viz.registerPalette('Light Palette', lightPalette);

/*

var barChartOptions = {
    equalBarWidth: false,
    dataSource: dataSource,
    commonSeriesSettings: {
        argumentField: "column1",
        type: "bar"
    },
    series: [
        { valueField: "columnvalue", name: "Month" }
    ],
    legend: {
        verticalAlignment: "bottom",
        horizontalAlignment: "center"
    },
    title: "Percent of Total Energy Production"
};
*/


/*
function multiMonthOfDayLineChartOptions(arrayForSplineChart,chartTitle) {
	
	var chartOptions = {
		    dataSource: dataSource,
		    commonSeriesSettings: {
		        type: 'line',
		        argumentField: "day"
		    },
		    commonAxisSettings: {
		        grid: {
		            visible: true
		        }
		    },
		    margin: {
		        bottom: 20
		    },
		    series: [
		        { valueField: "Jan", name: "JANUARY" },
		        { valueField: "Dec", name: "DECEMBER" },
		        { valueField: "Nov", name: "NOVEMBER" },
		        { valueField: "Oct", name: "OCTOBER" }
		    ],
		    tooltip:{
		        enabled: true
		    },
		    legend: {
		        verticalAlignment: "top",
		        horizontalAlignment: "right"
		    },
		    title: {
	            text:  chartTitle,
	            font: { color: 'white',size : 24 }
	        }
		};

	
};*/






function multiLineChartOptions(arrayForLineChart,chartTitle,seriesNamesArray,maxy) {
    var lineChartOptions = {
        commonAxisSettings: {
            grid: {
                color: gridColor,
                opacity:1
            },
            label: {
                font: {
                    color: fontColor
                }
            }
        },
        animation: {
            enabled: true
        },
        margin: {
            right: 0
        },
        valueAxis: {
            min: 0,
            max: maxy
        },
        
        legend: { 
        	verticalAlignment: "bottom",
            horizontalAlignment: "center",
        	visible: true
        },
		tooltip: {
        enabled: true,
        customizeTooltip: function (arg) {
            return {
                text: arg.valueText
            };
        }
		},      
        dataSource: arrayForLineChart,
        commonSeriesSettings: {
            type:'spline',
            argumentField: "xcolumn"
        },
        series: [
                 { valueField: "value1", name: seriesNamesArray[0]},
                 { valueField: "value2", name:  seriesNamesArray[1]}
             ],
        title: {
            text:  chartTitle,
            font: { color: 'white',size : 24 }
        }
        
    };
    return lineChartOptions;
};



function lineChartOptions(arrayForLineChart,chartTitle,seriesName,maxy) {
    var lineChartOptions = {
        commonAxisSettings: {
            grid: {
                color: gridColor,
                opacity:1
            },
            label: {
                font: {
                    color: fontColor
                }
            }
        },
        animation: {
            enabled: true
        },
        margin: {
            right: 0
        },
        commonSeriesSettings: {
        	 type: 'spline'
        },
        valueAxis: {
            min: 0,
            max: maxy
        },
        
        legend: { 
        	verticalAlignment: "bottom",
            horizontalAlignment: "center",
        	visible: true
        },
        tooltip: {
            enabled: true
        },        
        dataSource: arrayForLineChart,
        series: [{
            label: {
                visible: false,
                backgroundColor: labelColor,
                font: {
                    color: labelTextColor,
                    size:11
                }
            },
            name: seriesName,
            argumentField: 'column1',
            valueField: 'columnvalue',
            color: "#7cd2c7" //3999CC
        }],
        title: {
            text:  chartTitle,
            font: { color: 'white',size : 24 }
        }
        
    };
    return lineChartOptions;
}




function barChartOptions(arrayForBarChart,chartTitle,seriesName,maxy) {
    var barChartOptions = {
        equalBarWidth: false,	
        commonAxisSettings: {
            grid: {
                color: gridColor,
                opacity:1
            },
            label: {
                font: {
                    color: fontColor
                }
            }
        },
        animation: {
            enabled: true
        },
        margin: {
            right: 0
        },
        commonSeriesSettings: {
            type: 'bar'
        },
        valueAxis: {
            min: 0,
            max: maxy
        },
        
        legend: { 
        	verticalAlignment: "bottom",
            horizontalAlignment: "center",
        	visible: true
        },
        dataSource: arrayForBarChart,
        series: [{
            label: {
                visible: true,
                backgroundColor: labelColor,
                font: {
                    color: labelTextColor,
                    size:11
                }
            },
            name: seriesName,
            argumentField: 'column1',
            valueField: 'columnvalue',
            color: "#7cd2c7" //3999CC
        }],
        title: {
            text:  chartTitle,
            font: { color: 'white',size : 24 }
        }
        
    };
    return barChartOptions;
}




function donutChartOptions(arrayForChart,chartTitle) {
	 var donutChartOptions = {
	                    palette: piePalette,
	                    legend: {
	                        visible: true
	                    },
	                    tooltip: {
	                        enabled: true,
	                        percentPrecision: 2,
	                        customizeTooltip: function (arg) {
	                            return {
	                                text: arg.valueText + " - " + arg.percentText
	                            };
	                        }
	                    },
	                    animation: {
	                        enabled: true
	                    },
	                    dataSource: arrayForChart,
	                    series: {
	                    	type: "donut",
	                        border: {
	                            color: bkgColor,
	                            width: 2,
	                            visible: true
	                        },
	                        hoverStyle: {
	                            border: {
	                                color: bkgColor,
	                                width: 2,
	                                visible: true
	                            }
	                        },
	                        label:{
	                            visible: true,
	                            connector:{
	                                visible:true,           
	                                width: 1
	                            }
	                        },
	                        argumentField: 'column1',
	                        valueField: 'columnvalue'
	                    },
	                    title: {
	                        text:  chartTitle,
	                        font: { color: 'white',size : 24 }
	                    }
	                };
	          return donutChartOptions;
	}


function pieChartOptions(arrayForPieChart,chartTitle) {
	 var pieChartOptions = {
	                    palette: piePalette,
	                    legend: {
	                        visible: true
	                    },
	                    tooltip: {
	                        enabled: true,
	                        customizeText: function () {
	                            return this.argumentText + '<br/>' + this.percentText;
	                        }
	                    },
	                    animation: {
	                        enabled: true
	                    },
	                    dataSource: arrayForPieChart,
	                    series: {
	                        border: {
	                            color: bkgColor,
	                            width: 2,
	                            visible: true
	                        },
	                        hoverStyle: {
	                            border: {
	                                color: bkgColor,
	                                width: 2,
	                                visible: true
	                            }
	                        },
	                        label:{
	                            visible: true,
	                            connector:{
	                                visible:true,           
	                                width: 1
	                            }
	                        },
	                        argumentField: 'column1',
	                        valueField: 'columnvalue'
	                    },
	                    title: {
	                        text:  chartTitle,
	                        font: { color: 'white',size : 24 }
	                    }
	                };
	          return pieChartOptions;
	}




function percentageGaugeOptions(gaugeValue, tickInterval, chartTitle,endValue) {
	
    var gaugeRanges = [{
        startValue: 0*tickInterval,
        endValue: 1*tickInterval,
        color: bg_gray
    }, {
        startValue: 1*tickInterval+1,
        endValue: 2*tickInterval-1,
        color: bg_green
    }, {
        startValue: 2*tickInterval+1,
        endValue: 3*tickInterval-1,
    	color: bg_green
    }, {
        startValue: 3*tickInterval+1,
        endValue: 4*tickInterval-1,
    	color: bg_info
    }, {
        startValue: 4*tickInterval+1,
        endValue: 6*tickInterval,
    	color: bg_pink
    }
    ];
	
    
    
    
	
    var gaugeOptions = {
        containerBackgroundColor: colors.bkgColor,
        scale: {
            startValue: gaugeRanges[0].startValue,
            endValue:   endValue,
            majorTick: {
                color: 'none',
                tickInterval: tickInterval
            },
            label: {
                font: {
                    color: colors.fontColor
                },
                indentFromTick: 8
            }
        },
        rangeContainer: {
            width: 6,
            ranges: gaugeRanges,
            backgroundColor: 'none'
        },
        value: gaugeValue,
        valueIndicator: {
            offset: 5,
            indentFromCenter: 7,
            color: colors.needle
        },
        title: {
            text:  chartTitle,
            font: { color: 'white',size : 24 }
        }
        
        
    };
    return gaugeOptions;
}

/*

function createSimpleBarChartOptions(arrayForBarChart, chartName) {
    var barChartOptions = {
            dataSource:arrayForBarChart,
        series: {
            argumentField: 'column1',
            valueField: 'columnvalue',
            name: chartName,
            type: "bar",
            color: "#7cd2c7"
        }
    };
    
    return barChartOptions;
}
function createPieChartOptions(arrayForPieChart) {
 var pieChartOptions = {
                    palette: piePalette,
                    size: {
                        height: 320
                    },
                    margin: {
                        top: 5
                    },
                    legend: {
                        visible: true
                    },
                    tooltip: {
                        enabled: true,
                        customizeText: function () {
                            return this.argumentText + '<br/>' + this.percentText;
                        }
                    },
                    animation: {
                        enabled: true
                    },
                    dataSource: arrayForPieChart,
                    series: {
                        border: {
                            color: bkgColor,
                            width: 2,
                            visible: true
                        },
                        hoverStyle: {
                            border: {
                                color: bkgColor,
                                width: 2,
                                visible: true
                            }
                        },
                        label:{
                            visible: true,
                            connector:{
                                visible:true,           
                                width: 1
                            }
                        },
                        argumentField: 'column1',
                        valueField: 'columnvalue'
                    }
                };
          return pieChartOptions;
}







function createBarChartOptions(arrayForBarChart,chartName) {
                var barChartOptions = {
                    commonAxisSettings: {
                        grid: {
                            color: gridColor,
                            opacity:1
                        },
                        label: {
                            font: {
                                color: fontColor
                            }
                        }
                    },
                    animation: {
                        enabled: true
                    },
                    margin: {
                        right: 0
                    },
                    commonSeriesSettings: {
                        type: 'bar'
                    },
                    valueAxis: {
                        min: 0,
                        max: 10
                    },
                    legend: { visible: true},
                    dataSource: arrayForBarChart,
                    series: [{
                        label: {
                            visible: true,
                            backgroundColor: labelColor,
                            font: {
                                color: labelTextColor,
                                size:11
                            }
                        },
                        name: chartName,
                        argumentField: 'column1',
                        valueField: 'columnvalue',
                        color: "#7cd2c7"
                    }]
                };
                return barChartOptions;
 }
 
 function createStackedBarChartOptions(arrayForStackedBar,seriesAxis) {
                var barChartOptions = {
                    palette: palette,
                    commonAxisSettings: {
                        grid: {
                            color: gridColor,
                            opacity: 1
                        },
                        label: {
                            font: {
                                color: fontColor
                            }
                        },
                        opacity: 1
                    },
                    animation: animation,
                    commonSeriesSettings: {
                        argumentField: 'day',
                        type: 'stackedBar'
                    },
                    valueAxis: {
                        inverted: true,
                        label: {
                            customizeText: function () {
                                return 100 - this.value;
                            }
                        }
                    },
                    argumentAxis: {
                        min: 0,
                        max: 100,
                        label: {
                            indentFromAxis: -16
                        }
                    },
                    legend: {
                        margin: 30,
                        rowItemSpacing: 10,
                        markerSize: 20,
                        font: {
                            color:fontColor
                        }
                    },
                    margin: {
                        top: 16,
                        right: 41
                    },
                    dataSource: arrayForStackedBar,
                    series: seriesAxis
                };
                return barChartOptions;
 }
 
 
 function createLineChartOptions(arrayForLineChart,seriesAxis) {
                var lineChartOptions = {
                    palette: piePalette,
                    commonAxisSettings: {
                        valueMarginsEnabled: false,
                        grid: {
                            color: gridColor,
                            visible: true,
                            opacity: 1
                        },
                        label: {
                            font: {
                                color: fontColor
                            }
                        },
                        opacity: 1
                    },
                    size: {
                        width: 400
                    },
                    margin: {
                        top: 16
                    },
                    commonSeriesSettings: {
                        argumentField: 'hour',
                        type: 'spline',
                        point: { visible: false }
                    },
                    commonPaneSettings: {
                        border: {
                            visible: true,
                            color: gridColor,
                            opacity:1
                        }
                    },
                    argumentAxis: {
                        label: {
                            format: 'hour'
                        },
                        tickInterval: {
                            hours: 4
                        }
                    },
                    valueAxis: {
                        min: 0,
                        max: 500
                    },
                    legend: { visible: false },
                    dataSource: arrayForLineChart,
                    series: [{
                        valueField: 'y1'
                    }, {
                        valueField: 'y2'
                    }, {
                        valueField: 'y3'
                    }, {
                        valueField: 'y4'
                    }, {
                        valueField: 'y5'
                    }, {
                        valueField: 'y6'
                    }]
                };
                
                
return lineChartOptions;
 }            
    
*/