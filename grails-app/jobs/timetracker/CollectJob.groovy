package timetracker
import  grails.util.Holders

class CollectJob {
	def appTrackItemService
	def grailsApplication
	DataCollector dataCollector
	static triggers = { 
		simple repeatInterval: Holders.config.collectIntervalInMs
	}

	def execute() {
		//LinuxAppTrackItem.hasComputerBeenDown()
		//if(false)applicationInfoService.doOnShutdown()
		dataCollector=new WindowsDataCollector();
		AppTrackItem appTrackItem =dataCollector.getFocusedWindow()
		long idleTimeMs = dataCollector.getIdleTimeSinceLastUserInput()
		if(idleTimeMs > Holders.config.isIdleAfterMs){
			appTrackItem=AppTrackItem.idleInstance()
		}
		appTrackItemService.saveActiveWindow(appTrackItem)
	}
}
