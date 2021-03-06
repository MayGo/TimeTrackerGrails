package timetracker
import grails.util.Holders

class AppTrackItemService {
	AppTrackItem lastAppTrackItem
	AppTrackItem lastAppTrackItemSaved
	AppTrackItem beforelastAppTrackItem
	
	Date lastRunTime=new Date()
	
	private void addInactivePeriod(beginDate, endDate){
		println "adding inactive"
		AppTrackItem appTrackItem = AppTrackItem.inactiveInstance()
		appTrackItem.beginDate=beginDate
		appTrackItem.endDate=endDate
		
		if(!appTrackItem.save(flush:true)){
			log.error appTrackItem.errors
		}
	}

	/**
	 *  We dont save new item, we save when last item was same
	 * @param newAppTrackItem
	 */
	private AppTrackItem saveActiveWindow(AppTrackItem newAppTrackItem){
		//hold lastAppTrackItem to be assigned in the end
		AppTrackItem holderAppTrackItem
		Date now=new Date()
		// If it has been to long from last run, then computer has been in sleep/hibernate
		if((now.getTime()-lastRunTime.getTime())>(Holders.config.collectIntervalInMs + Holders.config.addShutdownStatusAfterMs)){
			lastAppTrackItem = null
			addInactivePeriod(lastRunTime, now)
		}
		
		if(newAppTrackItem){
			if(lastAppTrackItem?.hasSameNameAndTitle(newAppTrackItem)){	//last trackItem was same
				if(lastAppTrackItem.id)lastAppTrackItem.refresh()
				lastAppTrackItem.endDate=now
				log.debug "Old item -> "+lastAppTrackItem
				if(lastAppTrackItem.save(flush:true)){
					lastAppTrackItemSaved=lastAppTrackItem
				}else{
					log.error lastAppTrackItem.errors
				}

			}else{//trackItem different from last one
				//update last items endDate to get rid of cap between items
				if(lastAppTrackItem?.id){
					lastAppTrackItem.endDate=now
					lastAppTrackItem.save(flush:true)
				}

				log.debug "New item -> "+newAppTrackItem
				log.debug "Beforelast item-> "+beforelastAppTrackItem
				holderAppTrackItem=newAppTrackItem
				
				//if last item was not saved but before that was item that was saved then use that instead if it was equal to current
				if(beforelastAppTrackItem?.hasSameNameAndTitle(newAppTrackItem)){
					log.debug "Beforelast item is the same with new item."
					holderAppTrackItem=beforelastAppTrackItem
				}else if(lastAppTrackItemSaved?.hasSameNameAndTitle(newAppTrackItem)){
					log.debug "LastAppTrackItemSaved item is the same with new item."
					holderAppTrackItem=lastAppTrackItemSaved
				}else if(lastAppTrackItem?.id){
					log.debug "Last item was saved so new items date will be now."
					holderAppTrackItem.beginDate=now;
				}else {
					log.debug "Last item was not saved, so take beforelastAppTrackItem beginDate or now if it does not exist."
					holderAppTrackItem.beginDate=(beforelastAppTrackItem?.beginDate)?:now
				}
			}
		}else{
			lastAppTrackItem=null
		}

		

		if(holderAppTrackItem)lastAppTrackItem=holderAppTrackItem
		
		// make a "copy" so item is not attached to session
		AppTrackItem tempAppTrackItem=new AppTrackItem()
		tempAppTrackItem.properties=lastAppTrackItem?.properties
		
		//asign tempItem because else it would change in session
		beforelastAppTrackItem=tempAppTrackItem
		
		lastRunTime = now
		//return for testing
		return tempAppTrackItem
	}
	private void doOnShutdown(){
		println "Got shutdown signal."
		lastAppTrackItem=null
	}
}
