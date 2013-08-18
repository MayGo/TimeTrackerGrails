package timetracker

class AppTrackItemService {
	AppTrackItem lastAppTrackItem
	AppTrackItem beforelastAppTrackItem

	/**
	 *  We dont save new item, we save when last item was same
	 * @param newAppTrackItem
	 */
	private AppTrackItem saveActiveWindow(AppTrackItem newAppTrackItem){
		//hold lastAppTrackItem to be assigned in the end
		AppTrackItem holderAppTrackItem
		println "\n\n"

		if(newAppTrackItem){
			//tempAppTrackItem=newAppTrackItem
			lastAppTrackItem?.endDate=new Date();
			if(!lastAppTrackItem || (lastAppTrackItem?.tag?.name != newAppTrackItem.tag?.name || lastAppTrackItem?.title != newAppTrackItem.title)){
				println "new item ..."
				holderAppTrackItem=newAppTrackItem
				//trackItem different from last one
				//if last item was not saved but before that was item that was saved then use that instead if it was equal to current
				if(beforelastAppTrackItem?.tag?.name == newAppTrackItem.tag?.name && beforelastAppTrackItem?.title == newAppTrackItem.title){
					println "beforelast item is the same"
					holderAppTrackItem=beforelastAppTrackItem
				}else if(lastAppTrackItem?.id){
					println "last item was saved"
					holderAppTrackItem.beginDate=new Date();
				}else {
					println "take last saved beginDate"
					holderAppTrackItem.beginDate=(beforelastAppTrackItem?.beginDate)?:new Date()
				}
				println "... new item -> "+newAppTrackItem
				println "... beforelast -> "+beforelastAppTrackItem
			}else{
				println "old item -> "+lastAppTrackItem
				//last trackItem was same

				if(!lastAppTrackItem.save(flush:true)){
					println lastAppTrackItem.errors
				}

			}
		}else{
			lastAppTrackItem=null
		}

		beforelastAppTrackItem=lastAppTrackItem
		if(holderAppTrackItem)lastAppTrackItem=holderAppTrackItem
		//return for testing
		// make a "copy" so item is not attached to session
		AppTrackItem tempAppTrackItem=new AppTrackItem()
		tempAppTrackItem.properties=lastAppTrackItem?.properties
		return tempAppTrackItem
	}
	private void doOnShutdown(){
		println "Got shutdown signal."
		lastAppTrackItem=null
	}
}
