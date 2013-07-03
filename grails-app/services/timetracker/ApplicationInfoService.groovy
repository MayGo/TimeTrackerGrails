package timetracker

class ApplicationInfoService {
	ApplicationInfo lastApplicationInfo

	private void saveActiveWindow(ApplicationInfo awInfo){
		if(awInfo){
			if(lastApplicationInfo?.name == awInfo.name && lastApplicationInfo?.title == awInfo.title){
				lastApplicationInfo.updatedCount++
				if(!lastApplicationInfo.save(flush:true)){
					println awInfo.errors
				}
			}else{
				lastApplicationInfo=awInfo
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
