package timetracker

class ApplicationInfoService {
	ApplicationInfo lastApplicationInfo

	private void saveActiveWindow(ApplicationInfo awInfo){
		if(awInfo){
			
			if(!lastApplicationInfo){
				lastApplicationInfo=awInfo
				lastApplicationInfo.focusedFrom=new Date();
				return
			}
			// Update always so, there is no 5 sec cap between
			lastApplicationInfo.updatedCount++
			lastApplicationInfo.focusedUntil=new Date();
			if(!lastApplicationInfo.save(flush:true)){
				println awInfo.errors
			}
			if(lastApplicationInfo?.name == awInfo.name && lastApplicationInfo?.title == awInfo.title){
				
			}else{
				lastApplicationInfo=awInfo
				lastApplicationInfo.focusedFrom=new Date();
			}
		}else{
			lastApplicationInfo=null
		}
	}
	private void doOnShutdown(){
		println "Got shutdown signal."
		lastApplicationInfo=null
	}
}
