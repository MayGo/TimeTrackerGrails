package timetracker


class AppTrackItem extends TrackItem{
	String title
	static TAG_STATUS = "Status"
	static TAG_STATUS_IDLE = "Idle"
	static TAG_STATUS_INACTIVE = "Inactive"
	static Map notActiveNameAndTitle = ["Status":[TAG_STATUS_IDLE,TAG_STATUS_INACTIVE] ]//"$TAG_STATUS" does not work as key
	static constraints = {
	}
	static mapping = { title type: 'text' }

	//static private idleAppTrackItem
	//static private inactiveAppTrackItem
	
	static AppTrackItem idleInstance(){
		new AppTrackItem(tag:TrackTagService.getOrCreateTrackTag(TAG_STATUS), title: TAG_STATUS_IDLE)
	}
	
	static AppTrackItem inactiveInstance(){
		new AppTrackItem(tag:TrackTagService.getOrCreateTrackTag(TAG_STATUS), title: TAG_STATUS_INACTIVE)
	}
	
	String toString(){
		return tag?.name+": "+title+ "("+beginDate+" _ "+endDate+")"
	}
	boolean hasSameNameAndTitle(compObj){
		(tag?.name == compObj?.tag?.name && title == compObj?.title)
	}
	static namedQueries = {
		todays {
			def now = new Date()
			now.clearTime()
			gt 'beginDate', now
		}

		fromDate { day ->
			day.clearTime()
			gt 'beginDate',day
		}
		fromDateLimitDay { day ->
			day.clearTime()
			or{
				between "beginDate", day, day+1
				between "endDate", day, day+1
			}
		}
	}
}
