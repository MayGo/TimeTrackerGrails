// the semi-colon before function invocation is a safety net against concatenated
// scripts and/or other plugins which may not be closed properly.
;
(function($, window, document, undefined) {

	// Create the defaults once
	var pluginName = "timetrackerD3", defaults = {
		propertyName : "value",
		trackItems : [],
		trackName : [],
		margin : {
			top : 10,
			right : 0,
			bottom : 10,
			left : 70
		},
		defaultTimeDomainString : "1hr",
		onBrushSelection : function(d) {
		},
		onTrackItemChange : function(d) {
		}
	};
	var self = this;
	

	function msToTime(ms) {
		var secs = Math.floor(ms / 1000);
		var msleft = ms % 1000;
		var hours = Math.floor(secs / (60 * 60));
		var divisor_for_minutes = secs % (60 * 60);
		var minutes = Math.floor(divisor_for_minutes / 60);
		var divisor_for_seconds = divisor_for_minutes % 60;
		var seconds = Math.ceil(divisor_for_seconds);
		var formattedTime = "";
		if (hours > 0)
			formattedTime += hours + " h ";

		if (minutes > 0)
			formattedTime += minutes + " m ";
		else if (hours > 0 && minutes == 0)
			formattedTime += "0 m ";

		if (seconds > 0)
			formattedTime += seconds + " s ";
		else if (str != "" && seconds == 0)
			formattedTime += "0 s ";

		if (formattedTime == "")
			formattedTime = msleft + " ms";
		return formattedTime;
	}

	// The actual plugin constructor
	function Plugin(element, options) {
		this.element = element;

		this.options = $.extend({}, defaults, options);

		this._defaults = defaults;
		this._name = pluginName;

		self.trackItems = options.trackItems;
		self.trackNames = options.trackNames;

		self.miniHeight = 30;
		self.axisHeight = 30;
		self.height = $(this.element).height();
		self.width = $(this.element).width();

		self.mainHeight = self.height-self.miniHeight-this.options.margin.top-this.options.margin.bottom;
		self.mainWidth = self.width-self.miniHeight-this.options.margin.left-this.options.margin.right;
		
		
		this.init();
	}

	Plugin.prototype = {

		init : function() {
			self.plugin = this;
			this.setTimeDomain();
			this.initAxis();
			this.initSvg();
			this.initZoom();
			this.initBrush();
			this.initSelectionTool();

		},

		setTimeDomain : function(timeDomainString) {
			console.log("Setting timeDomain:" + timeDomainString);

			switch (timeDomainString) {
			case "1min":
				self.tickFormat = "%H:%M:%S";
				self.timeDomainStart = d3.time.minute.offset(this.getEndDate(), -1)
				self.timeDomainEnd = this.getEndDate();
				break;
			case "1hr":
				self.tickFormat = "%H:%M:%S";
				self.timeDomainStart = d3.time.hour.offset(this.getEndDate(), -1)
				self.timeDomainEnd = this.getEndDate();
				break;
			case "3hr":
				self.tickFormat = "%H:%M";
				self.timeDomainStart = d3.time.hour.offset(this.getEndDate(), -3)
				self.timeDomainEnd = this.getEndDate();
				break;

			case "6hr":
				self.tickFormat = "%H:%M";
				self.timeDomainStart = d3.time.hour.offset(this.getEndDate(), -6)
				self.timeDomainEnd = this.getEndDate();
				break;

			case "1day":
				self.tickFormat = "%H:%M";
				self.timeDomainStart = d3.time.day.offset(this.getEndDate(), -1)
				self.timeDomainEnd = this.getEndDate();
				break;

			case "1week":
				self.tickFormat = "%a %H:%M";
				self.timeDomainStart = d3.time.day.offset(this.getEndDate(), -7)
				self.timeDomainEnd = this.getEndDate();
				break;
			default:
				self.tickFormat = "%H:%M:%S"
				self.timeDomainStart = this.getMinDate();
				self.timeDomainEnd = this.getMaxDate();
			}
		},
		changeTimeDomain : function(timeDomainString) {
			this.setTimeDomain(timeDomainString);
			self.plugin.initAxis();
			this.redraw();
		},
		
		initSvg : function() {
			var margin = this.options.margin;
			var height = self.height;
			var width = self.width;
			var miniHeight = self.miniHeight

			var chart = d3.select(self.plugin.element).append("svg").attr("class", "chart").
							attr("width", width).
							attr("height", height);
			
			var mainChart = chart.append("g").attr("class", "mainChart").
			attr("transform", "translate(" + margin.left + ", 0)")
			.attr("width", self.mainWidth)
			.attr("height", self.mainHeight)
			
			
			var miniChart = chart.append("g")
			.attr("transform", "translate(" + margin.left + ", " +  (self.mainHeight + self.axisHeight) + ")")
			.attr("class", "miniChart");
			
		
			//miniChart.append("g").attr("class", "x axis").attr("transform", "translate(0, " + (height - margin.top - margin.bottom) + ")").transition().call(self.xAxis);
		//	miniChart.append("g").attr("class", "y axis").transition().call(self.yAxis);
			
			

			var rect = miniChart.selectAll("rect").data(self.trackItems, self.plugin.keyFunction);

			rect.enter().insert("rect", ":first-child").attr('class', 'miniItems').
				attr("rx", 5).
				attr("ry", 5).
				style("fill", function(d) {
					return d.color;
				}).attr("y", 0)

			rect.transition().attr("transform", self.plugin.rectTransform).attr("height", function(d) {
				return self.yScaleMini.rangeBand();
			}).attr("width", function(d) {
				return (self.xScale(d.endDate) - self.xScale(d.beginDate));
			}).attr("x", function(d) {
				return self.xScale(d.beginDate);
			});
			var miniChartBrush = d3.svg.brush().
						x(self.xScale).
						on("brush", this.redraw);
			//.on("brushstart", self.plugin.brushstart).on("brush", self.plugin.brushmove).on("brushend", self.plugin.brushend);
			
			var miniChartBrushSvg = miniChart.append('g').
						attr('class', 'miniBrush').
						call(miniChartBrush).
						selectAll("rect").attr('height', miniHeight)//.
						//select(".background").attr('height', margin.top)
			self.miniChartBrush=miniChartBrush
			//svg.select(".x").transition().call(self.xAxis);
			//svg.select(".y").transition().call(self.yAxis);
			
			
			
			
			
			
			/*
			 * d3.select("svg").on("click", function(d, i) { // set var to
			 * enable brush selecting //alert(2); self.plugin.toggleBrush(true);
			 * });
			 */
			mainChart.selectAll(".trackItems").data(self.trackItems, self.plugin.keyFunction).enter().append("rect").attr('class', 'trackItem').attr("rx", 5).attr("ry", 5).style("fill", function(d) {
				return d.color;
			}).attr("y", 0).attr("x", function(d) {
				return self.xScale(d.beginDate);
			}).attr("transform", self.plugin.rectTransform).attr("height", function(d) {
				return self.yScale.rangeBand();
			}).attr("width", function(d) {
				return (self.xScale(d.endDate) - self.xScale(d.beginDate));
			}).on("click", self.plugin.onClickTrackItem).call(self.plugin.onCreateTrackItem)

			mainChart.append("g")
				.attr("class", "x axis")
				.attr("transform", "translate(0, " + self.mainHeight + ")")
				.transition()
				.call(self.xAxis);
			mainChart.append("g")
				.attr("class", "y axis")
				.transition()
				.call(self.yAxis);

		},
		initAxis : function() {
			console.log("Init axis.");
			var margin = this.options.margin;
			//var height = self.height;
			//var width = self.width;
			self.xScale = d3.time.scale().domain([ self.timeDomainStart, self.timeDomainEnd ]).range([ 0, self.mainWidth ]).clamp(true);
			self.yScale = d3.scale.ordinal().domain(self.trackNames).rangeRoundBands([ 0, self.mainHeight], .1);
			self.yScaleMini = d3.scale.ordinal().domain(self.trackNames).rangeRoundBands([ 0, self.miniHeight ], .1);
			
			self.xAxis = d3.svg.axis().scale(self.xScale).orient("bottom").tickFormat(d3.time.format(self.tickFormat)).tickSubdivide(true).tickSize(8).tickPadding(8);
			self.yAxis = d3.svg.axis().scale(self.yScale).orient("left").tickSize(0);
		},
		initZoom : function() {
			console.log("init zoom");
			var mainChart = d3.select(".mainChart");
			mainChart.call(d3.behavior.zoom().x(self.xScale).on("zoom", this.redraw));
		},
		initBrush : function() {

			var margin = this.options.margin;
			var height = self.height;
			var width = self.width;

			var brush = d3.svg.brush().x(self.xScale).on("brushstart", self.plugin.brushstart).on("brush", self.plugin.brushmove).on("brushend", self.plugin.brushend);
			// make brush available globally
			var svg = d3.select("svg");

			var brushSvg = svg.append('g').attr('class', 'brush').attr("transform", "translate(" + margin.left + ", " + 0 + ")").call(brush)
			brushSvg.selectAll("rect").attr('height', height)
			brushSvg.select(".background").attr('height', margin.top)
			// make it available in other functions
			self.brush = brush;
			$('html').click(function() {
				// Hide selection brushes
				self.brush.clear()
				d3.select("svg").select('.brush').call(brush)

			});

			$('.brush').click(function(event) {
				event.stopPropagation();
			});
		},

		brushstart : function(p) {
			// var p = d3.event.target;
		},

		brushmove : function(p) {
			// var e = self.brush.extent();
		},

		brushend : function() {
			var minExtent = self.brush.extent()[0];
			var maxExtent = self.brush.extent()[1];
			// Add vars to be added to new item start/enddate
			var d = {
				beginDate : minExtent,
				endDate : maxExtent
			};
			self.plugin.options.onBrushSelection(d);

		},
		/*
		 * Selection tool to select an item on timeline and to edit its bounds
		 */
		initSelectionTool : function() {

			var margin = this.options.margin;

			var selectionTool = d3.svg.brush().x(self.xScale).on("brushstart", self.plugin.selectionToolBrushStart).on("brush", self.plugin.selectionToolBrushMove).on("brushend", self.plugin.selectionToolBrushEnd);

			var selectionToolSvg = d3.select("svg").append('g').attr('class', 'selectionTool').attr("transform", "translate(" + margin.left + ", " + margin.top + ")").call(selectionTool)
			// make it available in other functions
			self.selectionTool = selectionTool;
			$('html').click(function() {
				// Hide selection brushes
				self.selectionTool.x(d3.time.scale().domain([ 0, 0 ]).range([ 0, 0 ])).extent([ 0, 0 ])
				// self.selectionTool.clear()//.clear() not working as expected
				d3.select("svg").select('.selectionTool').call(self.selectionTool)

			});

		},
		selectionToolBrushStart : function(p) {
			// var p = d3.event.target;
		},

		selectionToolBrushMove : function(p) {
		},

		selectionToolBrushEnd : function() {
			// change data based on selection brush
			self.selectedTrackItemData.beginDate = self.selectionTool.extent()[0].getTime();
			self.selectedTrackItemData.endDate = self.selectionTool.extent()[1].getTime();
			console.log("selectionToolBrushEnd")
			console.log(self.selectedTrackItemData)
			self.plugin.changeTrackItem(self.selectedTrackItemData)
			self.plugin.options.onTrackItemChange(self.selectedTrackItemData);

		},
		/*
		 * Function do transition one item based on changed data, basically
		 * beginDate/endDate
		 */
		changeTrackItem : function(data) {
			var rect = d3.select("svg").select(".mainChart").selectAll("rect").data([ data ], self.plugin.keyFunction);
			rect.transition().attr("transform", self.plugin.rectTransform).attr("width", function(d) {
				return (self.xScale(d.endDate) - self.xScale(d.beginDate));
			}).attr("x", function(d) {
				return self.xScale(d.beginDate);
			});
		},
		onClickTrackItem : function(d, i) {
			console.log("onClickTrackItem")
			var p = d3.select(this);
			self.selectedTrackItem = p;
			var data = p.data()[0];
			self.selectedTrackItemData = data;
			var selectionToolSvg = d3.select(".selectionTool");

			var traslate = p.attr('transform');
			var x = new Number(p.attr('x'));
			var y = new Number(p.attr('y'));

			// position brush same as trackitem
			selectionToolSvg.selectAll("rect").attr('height', p.attr('height')).attr("transform", traslate);

			// to make unselecting work correctly
			self.selectionTool.x(self.xScale);

			// Make brush same size as trackitem
			self.selectionTool.extent([ data.beginDate, data.endDate ])
			selectionToolSvg.call(self.selectionTool)

			// remove crosshair outside of item
			selectionToolSvg.select(".background").attr('width', p.attr('width'));

			if (d.taskName === "LogTrackItem") {

			}
			// prevent event bubbling up, to unselect when clicking outside
			event.stopPropagation();

		},

		addItem : function(item) {
			self.trackItems.push(item);
			self.plugin.redraw();

		},
		redraw : function() {
			console.log("redrawing...")
			var tasks = self.trackItems;
			
			
			var minExtent = miniChartBrush.extent()[0],
			maxExtent = miniChartBrush.extent()[1];
			
			if(minExtent && maxExtent){
				self.timeDomainStart = minExtent;
				self.timeDomainEnd = maxExtent;
				console.log(minExtent)
				console.log(maxExtent)
				self.plugin.initAxis();
			}
			// self.plugin.initAxis();

			var svg = d3.select("svg");

			var rect = svg.select(".mainChart").selectAll("rect").data(tasks, self.plugin.keyFunction);

			rect.enter().insert("rect", ":first-child").attr('class', 'trackItem').attr("rx", 5).attr("ry", 5).style("fill", function(d) {
				return d.color;
			}).attr("y", 0).attr("x", function(d) {
				return self.xScale(d.beginDate);
			}).attr("transform", self.plugin.rectTransform).attr("height", function(d) {
				return self.yScale.rangeBand();
			}).attr("width", function(d) {
				return (self.xScale(d.endDate) - self.xScale(d.beginDate));
			}).on("click", self.plugin.onClickTrackItem).call(self.plugin.onCreateTrackItem)

			rect.transition().attr("transform", self.plugin.rectTransform).attr("height", function(d) {
				return self.yScale.rangeBand();
			}).attr("width", function(d) {
				return (self.xScale(d.endDate) - self.xScale(d.beginDate));
			}).attr("x", function(d) {
				return self.xScale(d.beginDate);
			});

			svg.select(".x").transition().call(self.xAxis);
			svg.select(".y").transition().call(self.yAxis);

		},
		keyFunction : function(d) {
			return d.id;
		},
		/*
		 * Function to add qTip to every item
		 */
		onCreateTrackItem : function(selection) {

			selection.each(function(d, i) {
				$(this).qtip({
					content : {
						text : function(event, api) {
							var data = d;
							var duration=d.endDate-d.beginDate;
							var durationFormatted=msToTime(duration); 
							return data.desc+"<br/>"+durationFormatted;
						},
						title : function(event, api) {
							var data = d;
							return data.name
						}
					},
					style : {
						classes : 'qtip-light qtip-shadow qtip-rounded'
					},

					position : {
						my : 'bottom center', // Position my top left...
						at : 'top center', // at the bottom right of...
						target : 'event'
					}
				});
			});
		},

		rectTransform : function(d) {
			return "translate(" + 0 + "," + self.yScale(d.taskName) + ")";
		},

		getMaxDate : function() {
			var tasks = self.trackItems;
			if(tasks.length==0)return self.plugin.options.defaultBeginDate + 86400000;
			
			tasks.sort(function(a, b) {
				return a.endDate - b.endDate;
			});
			var maxDate = tasks[tasks.length - 1].endDate;
			console.log("maxDate: " + new Date(maxDate));
			return maxDate;
		},
		getMinDate : function() {
			var tasks = self.trackItems;
			if(tasks.length==0)return self.plugin.options.defaultBeginDate;
			
			tasks.sort(function(a, b) {
				return a.beginDate - b.beginDate;
			});
			
			var minDate = tasks[0].beginDate;
			console.log("minDate: " + new Date(minDate));
			return minDate;
		},
		getEndDate : function() {
			var lastEndDate = Date.now();
			var tasks = self.trackItems;
			if (tasks.length > 0) {
				lastEndDate = tasks[tasks.length - 1].endDate;
			}

			return lastEndDate;
		}
	};

	// A really lightweight plugin wrapper around the constructor,
	// preventing against multiple instantiations
	$.fn[pluginName] = function(options) {
		return this.each(function() {
			if (!$.data(this, "plugin_" + pluginName)) {
				$.data(this, "plugin_" + pluginName, new Plugin(this, options));
			}
		});
	};

})(jQuery, window, document);