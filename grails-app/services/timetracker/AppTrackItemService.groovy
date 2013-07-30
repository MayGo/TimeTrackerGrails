package timetracker

class AppTrackItemService {
	AppTrackItem lastAppTrackItem
	
	private void saveActiveWindow(AppTrackItem awInfo){
		if(awInfo){
			
			if(!lastAppTrackItem){
				lastAppTrackItem=awInfo
				lastAppTrackItem.beginDate=new Date();
				return
			}
			// Update always. so there is no 5 sec cap between
			lastAppTrackItem.endDate=new Date();
			if(!lastAppTrackItem.save(flush:true)){
				println lastAppTrackItem.errors
			}
			if(lastAppTrackItem?.tag == awInfo.tag && lastAppTrackItem?.title == awInfo.title){
				
			}else{
				lastAppTrackItem=awInfo
				lastAppTrackItem.beginDate=new Date();
			}
		}else{
			lastAppTrackItem=null
		}
	}
	private void doOnShutdown(){
		println "Got shutdown signal."
		lastAppTrackItem=null
	}
}
