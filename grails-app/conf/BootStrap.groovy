import timetracker.TestDataService
import timetracker.WindowsDataCollector
class BootStrap {
	def testDataService
	def init = { servletContext ->
		//testDataService.buildTestData()
		//printClassPath this.class.classLoader
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
