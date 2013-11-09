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
	
		def day = (params.day)?new Date(params.day as long):today
		
		today.clearTime()
		day.clearTime()
		List<TrackItem> trackItems=timelineService.trackItemsInBetween(day, day+1)
		def items=trackItems?.collect{
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
		List stateItems=[]
		trackItems.each{
			if(it.getClass()==AppTrackItem.class){
				def currentItem=[taskName:"StateTrackItem",
				id:it.id,
				name: "Active",
				desc:"",
				color: "#19E512",
				beginDate:it.beginDate.getTime(),
				endDate:it.endDate.getTime()]
				if(AppTrackItem.notActiveNameAndTitle.containsKey(it.tag.name) && AppTrackItem.notActiveNameAndTitle[it.tag.name].find{s->s==it.title}){
					//println "inactive item"
					currentItem.name="Inactive"
					currentItem.color = "#FF2424"
				}
				def lastItem = (stateItems.size()>0)?stateItems?.last():null
				if(lastItem){
				//	println "on viimane"
					if(lastItem.name == currentItem.name){
					//	println "samad nimed"
						stateItems.last().endDate=currentItem.endDate
					}else{
					//	println "pole samad"
						stateItems << currentItem
					}
				}else{
				//println "pole viimast"
					stateItems<<currentItem
				}
				//println it
			}
		}
		items.addAll(stateItems)
		//new File("testData.json").write((l as JSON).toString())
		[appTrackItemInstanceList: items as JSON, today:today.getTime(), day:day.getTime()]
	}
}
