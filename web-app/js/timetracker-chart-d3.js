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
			top : 20,
			right : 40,
			bottom : 20,
			left : 70
		},
		defaultTimeDomainString : "1hr"
	};
	var self = this;

	// The actual plugin constructor
	function Plugin(element, options) {
		this.element = element;

		this.options = $.extend({}, defaults, options);

		this._defaults = defaults;
		this._name = pluginName;

		self.trackItems = options.trackItems;
		self.trackNames = options.trackNames;

		self.height = $(this.element).height() - this.options.margin.top - this.options.margin.bottom - 5;
		self.width = $(this.element).width() - this.options.margin.right - this.options.margin.left - 5;

		this.init();
	}

	Plugin.prototype = {

		init : function() {
			self.plugin = this;
			this.setTimeDomain();
			this.initAxis();
			this.initSvg();
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

			var svg = d3.select(self.plugin.element).append("svg").attr("width", width + margin.left + margin.right).attr("height", height + margin.top + margin.bottom).append("g").attr("class", "chart").attr("transform", "translate(" + margin.left + ", " + margin.top + ")");
			svg.call(d3.behavior.zoom().x(self.xScale).on("zoom", this.redraw));

			svg.selectAll(".chart").data(self.trackItems, self.keyFunction).enter().append("rect").attr("rx", 5).attr("ry", 5).style("fill", function(d) {
				return d.color;
			}).attr("y", 0).attr("x", function(d) {
				return self.xScale(d.startDate);
			}).attr("transform", self.rectTransform).attr("height", function(d) {
				return self.yScale.rangeBand();
			}).attr("width", function(d) {
				return (self.xScale(d.endDate) - self.xScale(d.startDate));
			}).on("click", function(d, i) {
				// alert(i);
				alert(d.name + ": "+d.desc);
			});

			svg.append("g").attr("class", "x axis").attr("transform", "translate(0, " + (height - margin.top - margin.bottom) + ")").transition().call(self.xAxis);
			svg.append("g").attr("class", "y axis").transition().call(self.yAxis);
		},
		initAxis : function() {
			console.log("Init axis.");
			var margin = this.options.margin;
			var height = self.height;
			var width = self.width;
			self.xScale = d3.time.scale().domain([ self.timeDomainStart, self.timeDomainEnd ]).range([ 0, width ]).clamp(true);
			self.yScale = d3.scale.ordinal().domain(self.trackNames).rangeRoundBands([ 0, height - margin.top - margin.bottom ], .1);

			self.xAxis = d3.svg.axis().scale(self.xScale).orient("bottom").tickFormat(d3.time.format(self.tickFormat)).tickSubdivide(true).tickSize(8).tickPadding(8);
			self.yAxis = d3.svg.axis().scale(self.yScale).orient("left").tickSize(0);
		},

		redraw : function() {
			console.log("redrawing...")
			var tasks = self.trackItems;
			// self.plugin.initAxis();

			var svg = d3.select("svg");

			var rect = svg.select(".chart").selectAll("rect").data(tasks, self.keyFunction);

			// rect.enter().insert("rect", ":first-child").attr("rx",
			// 5).attr("ry", 5).transition().attr("y", 0).attr("transform",
			// self.rectTransform).attr("height", function(d) {
			// return self.yScale.rangeBand();
			// }).attr("width", function(d) {
			// return (self.xScale(d.endDate) - self.xScale(d.startDate));
			// });

			rect.transition().attr("transform", self.rectTransform).attr("height", function(d) {
				return self.yScale.rangeBand();
			}).attr("width", function(d) {
				return (self.xScale(d.endDate) - self.xScale(d.startDate));
			}).attr("x", function(d) {
				return self.xScale(d.startDate);
			});

			// rect.exit().remove();

			svg.select(".x").transition().call(self.xAxis);
			svg.select(".y").transition().call(self.yAxis);

		},

		keyFunction : function(d) {
			return d.id;
		},

		rectTransform : function(d) {
			return "translate(" + self.xScale(d.startDate) + "," + self.yScale(d.taskName) + ")";
		},
		getMaxDate : function() {
			var tasks = self.trackItems;
			tasks.sort(function(a, b) {
				return a.endDate - b.endDate;
			});
			var maxDate = tasks[tasks.length - 1].endDate;
			console.log("maxDate: " + new Date(maxDate));
			return maxDate;
		},
		getMinDate : function() {
			var tasks = self.trackItems;
			tasks.sort(function(a, b) {
				return a.startDate - b.startDate;
			});
			var minDate = tasks[0].startDate;
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