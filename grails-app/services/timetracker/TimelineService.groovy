package timetracker

/**
 * TimelineService
 * A service class encapsulates the core business logic of a Grails application
 */
class TimelineService {

    static transactional = true

	/**
	 * Gets TrackItem-s in between dates, if endDate is null, then items from beginDate.
	 * beginDate cannot be null
	 * @param beginDate
	 * @param endDate
	 * @return List<TrackItem> 
	 */
    def trackItemsInBetween(Date beginDate, Date endDate) {
		if(!beginDate) return null
		
		def results = TrackItem.createCriteria().list {
			if(beginDate && endDate){
				or{
					between("beginDate", beginDate, endDate)
					between("endDate", beginDate, endDate)
				}
			}else if(beginDate){
				ge("beginDate", beginDate)
			}
			
			//maxResults(10)
			order("beginDate", "asc")
		}
    }
}
