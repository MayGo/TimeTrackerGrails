package timetracker
import grails.converters.JSON
class TestDataService {

	def buildTestData() {
		if(!AppTrackItem.count()){
			println "Building testdata...."
			new File("testData.json").eachLine { line ->
				def json=JSON.parse(line)
				json.each{
					AppTrackItem.build(tag:TrackTagService.getOrCreateTrackTag(it.name), title:it.desc, beginDate:new Date(it.startDate), endDate: new Date(it.endDate))
				}
			}
			println "...Building testdata complete!"
		}
	}
}
