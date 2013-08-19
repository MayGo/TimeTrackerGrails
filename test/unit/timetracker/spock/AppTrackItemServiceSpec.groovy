package timetracker.spock;

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

import timetracker.AppTrackItemService
import timetracker.AppTrackItem
import timetracker.TrackItem
import timetracker.TrackTag
import timetracker.Color

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

	def "End dates should all be unique"(){
		given:
		def appTrackItems=[]
		(1..2).each { i -> appTrackItems<<service.saveActiveWindow(createAppTrackItem("Tag1", "Title")) }
		(1..5).each { i -> appTrackItems<<service.saveActiveWindow(createAppTrackItem("Tag${i}", "Title")) }
		(1..3).each { i -> appTrackItems<<service.saveActiveWindow(createAppTrackItem("Tag2", "Title")) }
		expect:
		appTrackItems.size()==10

		appTrackItems.collect{it.endDate}.unique().size==10
	}
	def "There should be no date cap between saved items, even when unique items in between."(){
		given:
		def appTrackItems=[]
		(1..2).each { i -> appTrackItems<<service.saveActiveWindow(createAppTrackItem("Tag1", "Title")) }
		(1..5).each { i -> appTrackItems<<service.saveActiveWindow(createAppTrackItem("Tag${i}", "Title")) }
		(1..3).each { i -> appTrackItems<<service.saveActiveWindow(createAppTrackItem("Tag2", "Title")) }
		expect:
		appTrackItems.size()==10

		//check if returned items endDate and beginDate matches
		appTrackItems.eachWithIndex() { obj, i ->
			if(i>0){
				appTrackItems[i-1].endDate==obj.beginDate
			}
		}
		
		//check if saved items endDate and beginDate matches
		AppTrackItem.list().eachWithIndex() { obj, i ->
			if(i>0){
				appTrackItems[i-1].endDate==obj.beginDate
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
}
