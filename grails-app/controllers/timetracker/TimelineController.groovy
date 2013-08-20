package timetracker

import grails.converters.JSON

/**
 * TimelineController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class TimelineController {


	def timelineService
		
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index(){
		redirect action: "timeline"
	}
	def timeline(){
		//params.max = 10
		def today = new Date()
			today.clearTime()
		def l=timelineService.trackItemsInBetween(today, null)?.collect{
			[
				taskName:it.getClass().getSimpleName(),
				id:it.id,
				name:it.tag.name,
				desc:(it.hasProperty('title'))?it.title:it.desc,
				color: it.tag.color.rgb,
				beginDate:it.beginDate.getTime(),
				endDate:it.endDate.getTime()
			]
		}
		//new File("testData.json").write((l as JSON).toString())
		[appTrackItemInstanceList: l as JSON, timeBegin: l.max{it.begin}.begin, timeEnd: l.max{it.end}.end]
	}
}
