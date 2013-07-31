package timetracker

class TrackTagService {
	
	public static TrackTag getOrCreateTrackTag(name){
		TrackTag trackTag = TrackTag.findByName(name)
		if(!trackTag){
			trackTag=new TrackTag(name)
			if(!trackTag.save(flush:true)){
				println trackTag.errors
			}
		}
		return trackTag
	}
}
