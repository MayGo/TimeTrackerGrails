import timetracker.TestDataService
import timetracker.TrackTagService
import timetracker.WindowsDataCollector
class BootStrap {
	def testDataService
	def init = { servletContext ->
		//testDataService.buildTestData()
		//printClassPath this.class.classLoader
		
		println "Creating tags"
		TrackTagService.getOrCreateTrackTag("Status", "#FF2424")
	}
	def printClassPath(classLoader) {
		println "$classLoader"
		classLoader.getURLs().each {url-> println "- ${url.toString()}" }
		if (classLoader.parent) {
			printClassPath(classLoader.parent)
		}
		
	}

	def destroy = {
	}
}
