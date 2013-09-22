package timetracker


class AppTrackItem extends TrackItem{
	String title

	static constraints = {
	}
	static mapping = { title type: 'text' }

	//static private idleAppTrackItem
	//static private inactiveAppTrackItem
	
	static AppTrackItem idleInstance(){
		new AppTrackItem(tag:TrackTagService.getOrCreateTrackTag("Status"), title:"Idle")
	}
	
	static AppTrackItem inactiveInstance(){
		new AppTrackItem(tag:TrackTagService.getOrCreateTrackTag("Status"), title:"Inactive")
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
