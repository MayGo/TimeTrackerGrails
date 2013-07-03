import timetracker.TestDataService
class BootStrap {
	def testDataService
    def init = { servletContext ->
		testDataService.buildTestData()
    }
    def destroy = {
    }
}
