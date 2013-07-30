package timetracker

class CollectJob {
	def appTrackItemService
	DataCollector dataCollector
	static triggers = { simple repeatInterval: 2000l // execute job once in 5 seconds
	}

	def execute() {
		//LinuxAppTrackItem.hasComputerBeenDown()
		//if(false)applicationInfoService.doOnShutdown()
		dataCollector=new WindowsDataCollector();
		appTrackItemService.saveActiveWindow(dataCollector.getFocusedWindow())
	}
}
