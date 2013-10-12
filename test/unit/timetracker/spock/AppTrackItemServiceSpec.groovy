package timetracker.spock;

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

import timetracker.AppTrackItemService
import timetracker.AppTrackItem
import timetracker.TrackItem
import timetracker.TrackTag
import timetracker.Color
import grails.util.Holders

@TestFor(AppTrackItemService)
@Mock([AppTrackItem,TrackItem, TrackTag, Color])
class AppTrackItemServiceSpec  extends Specification{

	AppTrackItem createAppTrackItem(name, title){
		AppTrackItem appTrackItem= new AppTrackItem(tag:new TrackTag(name:name, color:null), title:title)

		return appTrackItem
	}

	def "Unique items should not be saved"() {

		given:
		(1..3).each { i -> service.saveActiveWindow(createAppTrackItem("Tag${i}", "Title")) }
		(1..3).each { i -> service.saveActiveWindow(createAppTrackItem("Tag${i}", "Title${i}")) }
		expect:
		AppTrackItem.count()==0
	}

	def "Nonunique items in a row should be saved"() {

		given:
		(1..2).each { i -> service.saveActiveWindow(createAppTrackItem("Tag1", "Title")) }
		(1..3).each { i -> service.saveActiveWindow(createAppTrackItem("Tag2", "Title")) }
		(1..3).each { i -> service.saveActiveWindow(createAppTrackItem("Tag1", "Title")) }
		expect:
		AppTrackItem.count()==3
	}


	def "One unique between nonuniques should not make new item"() {

		given:
		(1..2).each { i -> service.saveActiveWindow(createAppTrackItem("Tag1", "Title")) }
		service.saveActiveWindow(createAppTrackItem("Tag2", "Title"))
		(1..3).each { i -> service.saveActiveWindow(createAppTrackItem("Tag1", "Title")) }
		expect:
		AppTrackItem.count()==1
	}

	def "If there is unique inbetween then beginDate should come from beforelast item (first item is not saved)"() {

		given:
		def processed1=service.saveActiveWindow(createAppTrackItem("Tag1", "Title"))
		def processed2unique=service.saveActiveWindow(createAppTrackItem("Tag2", "Title"))
		def processed3=service.saveActiveWindow(createAppTrackItem("Tag1", "Title"))
		def processed4=service.saveActiveWindow(createAppTrackItem("Tag1", "Title"))
		expect:
		processed1.beginDate==processed3.beginDate
		processed1.beginDate==processed4.beginDate
	}

	def "Saved items beginDates and endDates should be unique"(){
		(1..2).each { i -> service.saveActiveWindow(createAppTrackItem("Tag1", "Title")) }
		(1..5).each { i -> service.saveActiveWindow(createAppTrackItem("Tag${i}", "Title")) }
		(1..3).each { i -> service.saveActiveWindow(createAppTrackItem("Tag2", "Title")) }
		(1..2).each { i -> service.saveActiveWindow(createAppTrackItem("Tag3", "Title")) }
		expect:
		def appTrackItems=AppTrackItem.list()
		appTrackItems.size()==3
		appTrackItems.collect{it.beginDate}.unique().size==3
		appTrackItems.collect{it.endDate}.unique().size==3
	}
	def "There should be no date cap between saved items, even when unique items in between."(){
		given:
		(1..2).each { i -> service.saveActiveWindow(createAppTrackItem("Tag1", "Title1")) }
		(1..2).each { i -> service.saveActiveWindow(createAppTrackItem("Tag1", "Title2")) }
		(1..1).each { i -> service.saveActiveWindow(createAppTrackItem("Tag${i}", "Title${i}")) }
		(1..2).each { i -> service.saveActiveWindow(createAppTrackItem("Tag1", "Title3")) }
		(1..5).each { i -> service.saveActiveWindow(createAppTrackItem("Tag${i}", "Title")) }
		(1..3).each { i -> service.saveActiveWindow(createAppTrackItem("Tag2", "Title")) }
		(1..1).each { i -> service.saveActiveWindow(createAppTrackItem("Tag${i}", "Title${i}")) }
		(1..2).each { i -> service.saveActiveWindow(createAppTrackItem("Tag3", "Title")) }
		expect:
		//check if saved items endDate and beginDate matches
		def appTrackItems=AppTrackItem.list()
		appTrackItems.size()==5
		appTrackItems.eachWithIndex() { obj, i ->
			if(i>0){
				assert appTrackItems[i-1].endDate==obj.beginDate
			}
		}
	}
	def "Two same items should be merged when unique items in between."(){
		given:
		def appTrackItems=[]
		(1..2).each { i -> appTrackItems<<service.saveActiveWindow(createAppTrackItem("Tag1", "Title")) }
		(1..5).each { i -> appTrackItems<<service.saveActiveWindow(createAppTrackItem("Tag${i}", "Title")) }
		(1..3).each { i -> appTrackItems<<service.saveActiveWindow(createAppTrackItem("Tag1", "Title")) }
		expect:
		AppTrackItem.count()==1
	}
	def "When computer has been shutdown, there should be Inactive period between same items"(){
		given:
		//Insert one
		service.saveActiveWindow(createAppTrackItem("Tag1", "Title"))
		AppTrackItem a1 = service.saveActiveWindow(createAppTrackItem("Tag1", "Title"))
		
		//Modify configuration so, Idle item is created on next saveActiveWindow
		Holders.config.collectIntervalInMs=0.1
		Holders.config.addShutdownStatusAfterMs=0.1
		
		AppTrackItem a2 = service.saveActiveWindow(createAppTrackItem("Tag1", "Title"))
		Holders.config.collectIntervalInMs=1000
		Holders.config.addShutdownStatusAfterMs=1000
		service.saveActiveWindow(createAppTrackItem("Tag1", "Title"))
		expect:
		AppTrackItem.count()==3
		a1.beginDate != a2.beginDate
		a1.endDate != a2.endDate
	}
	def "When computer has been shutdown, there should be Inactive period between different items"(){
		given:
		//Insert one
		service.saveActiveWindow(createAppTrackItem("Tag1", "Title"))
		AppTrackItem a1 = service.saveActiveWindow(createAppTrackItem("Tag1", "Title"))
		
		//Modify configuration so, Idle item is created on next saveActiveWindow
		Holders.config.collectIntervalInMs=0.1
		Holders.config.addShutdownStatusAfterMs=0.1
		
		AppTrackItem a2 = service.saveActiveWindow(createAppTrackItem("Tag2", "Title"))
		Holders.config.collectIntervalInMs=1000
		Holders.config.addShutdownStatusAfterMs=1000
		service.saveActiveWindow(createAppTrackItem("Tag2", "Title"))
		expect:
		AppTrackItem.count()==3
		a1.beginDate != a2.beginDate
		a1.endDate != a2.endDate
	}
}
