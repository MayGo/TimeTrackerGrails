package timetracker

import grails.converters.JSON

/**
 * TimelineController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class TimelineController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index(){
		redirect action: "timeline"
	}
	def timeline(){
		params.max = 1000
		def l=AppTrackItem.list(params).collect{
			[
				taskName:'Application',
				id:it.tag.name,
				name:it.tag.name,
				desc:it.title,
				color: it.tag.color.rgb,
				startDate:it.beginDate.getTime(),
				endDate:it.endDate.getTime()
			]
		}
		//new File("testData.json").write((l as JSON).toString())
		[appTrackItemInstanceList: l as JSON, timeBegin: l.max{it.begin}.begin, timeEnd: l.max{it.end}.end]
	}
}
