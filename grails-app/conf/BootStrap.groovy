import timetracker.TestDataService
import timetracker.WindowsDataCollector
class BootStrap {
	def testDataService
    def init = { servletContext ->
		//testDataService.buildTestData()
    }
    def destroy = {
    }
}
