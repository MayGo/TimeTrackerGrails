package timetracker


class TrackItem {
	TrackTag tag
	
	Date beginDate
	Date endDate
	Date dateCreated
	Date lastUpdated
	public setTagByName(String name){
		TrackTag trackTag = TrackTag.findByName(name)
		if(!trackTag){
			trackTag=new TrackTag(name)
			if(!trackTag.save()){
				println trackTag.errors
			}
		}
		this.tag=trackTag
	}

	static constraints = {
	}
}
