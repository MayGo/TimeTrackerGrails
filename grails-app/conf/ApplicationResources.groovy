modules = {
	application { resource url:'js/application.js' }
	timelineD3 {
		dependsOn 'jquery'
		//resource url:'js/timelineD3.js'
		//resource url:'js/d3-timeline.js'
		//resource url:'js/timelineD3.css'
		resource url:'js/gantt-chart-d3.js'
		resource  url:'http://cdnjs.cloudflare.com/ajax/libs/qtip2/2.0.1/jquery.qtip.min.css'
		resource  url:'http://cdnjs.cloudflare.com/ajax/libs/qtip2/2.0.1/jquery.qtip.min.js'
	}
	timetracker {
		dependsOn 'jquery'
		resource url:'js/timetracker-chart-d3.css'
		resource url:'js/timetracker-chart-d3.js'
		resource  url:'http://cdnjs.cloudflare.com/ajax/libs/qtip2/2.0.1/jquery.qtip.min.css'
		resource  url:'http://cdnjs.cloudflare.com/ajax/libs/qtip2/2.0.1/jquery.qtip.min.js'
		resource url:'js/bootstrap-notify.js'
		resource url:'js/bootstrap-notify.css'
		resource url:'js/jquery.tipsy.js'
		resource url:'js/tipsy.css'
		//resource url:'js/jquery.jgrowl.min.css'
		//resource url:'js/jquery.jgrowl.map'
	}
}