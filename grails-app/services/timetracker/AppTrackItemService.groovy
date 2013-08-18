package timetracker

class AppTrackItemService {
	AppTrackItem lastAppTrackItem
	//hold item that has not been saved yet
	AppTrackItem lastAppTrackItemSaved

	/**
	 *  We dont save new item, we save when last item was same
	 * @param newAppTrackItem
	 */
	private void saveActiveWindow(AppTrackItem newAppTrackItem){
		if(newAppTrackItem){

			//tempAppTrackItem=newAppTrackItem

			if(!lastAppTrackItem || (lastAppTrackItem?.tag != newAppTrackItem.tag && lastAppTrackItem?.title != newAppTrackItem.title)){
				println "new item"+lastAppTrackItem
				//trackItem different from last one
				println  lastAppTrackItem?.id
				//if last item was not saved but before that was item that was saved then use that instead if it was equal to current
				if(lastAppTrackItem?.id){
					println "normale"
					lastAppTrackItem=newAppTrackItem
					lastAppTrackItem.beginDate=new Date();
				}else if(lastAppTrackItemSaved?.tag == newAppTrackItem.tag && lastAppTrackItemSaved?.title == newAppTrackItem.title){
					println "last same"
					lastAppTrackItem=lastAppTrackItemSaved
				}else{
				println "take last saved"
					lastAppTrackItem=newAppTrackItem
					lastAppTrackItem.beginDate=(lastAppTrackItemSaved?.beginDate)?:new Date()
				}
				
			}else{
			println "old item"
				//last trackItem was same
				
				if(!lastAppTrackItem.save(flush:true)){
					println lastAppTrackItem.errors
				}
				lastAppTrackItemSaved=lastAppTrackItem
			}
			// Update always. so there is no 5 sec cap between
			lastAppTrackItem.endDate=new Date();
			


		}else{
			lastAppTrackItem=null
		}
	}
	private void doOnShutdown(){
		println "Got shutdown signal."
		lastAppTrackItem=null
	}
}
