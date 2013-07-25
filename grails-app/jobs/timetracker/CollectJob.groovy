package timetracker

class CollectJob {
	def applicationInfoService
	DataCollector dataCollector
	static triggers = { simple repeatInterval: 2000l // execute job once in 5 seconds
	}

	def execute() {
		//LinuxApplicationInfo.hasComputerBeenDown()
		//if(false)applicationInfoService.doOnShutdown()
		dataCollector=new WindowsDataCollector();
		applicationInfoService.saveActiveWindow(dataCollector.getFocusedWindow())
	}
}
