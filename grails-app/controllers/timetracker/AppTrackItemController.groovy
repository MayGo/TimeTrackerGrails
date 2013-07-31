package timetracker

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class AppTrackItemController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index() {
		redirect(action: "list", params: params)
	}

	def list(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		[appTrackItemInstanceList: AppTrackItem.list(params), appTrackItemInstanceTotal: AppTrackItem.count()]
	}

	def timeline(){
		params.max = 100
		def l=AppTrackItem.list(params).collect{
			[
				taskName:'Application',
				id:it.tag.name,
				name:it.tag.name,
				desc:it.title,
				color: it.tag.color.rgb,
				startDate:it.beginDate.getTime(),
				endDate:it.endDate.getTime()
			]
		}
		[appTrackItemInstanceList: l as JSON, timeBegin: l.max{it.begin}.begin, timeEnd: l.max{it.end}.end]
	}

	def create() {
		[appTrackItemInstance: new AppTrackItem(params)]
	}

	def save() {
		def appTrackItemInstance = new AppTrackItem(params)
		if (!appTrackItemInstance.save(flush: true)) {
			render(view: "create", model: [appTrackItemInstance: appTrackItemInstance])
			return
		}

		flash.message = message(code: 'default.created.message', args: [
			message(code: 'appTrackItem.label', default: 'AppTrackItem'),
			appTrackItemInstance.id
		])
		redirect(action: "show", id: appTrackItemInstance.id)
	}

	def show(Long id) {
		def appTrackItemInstance = AppTrackItem.get(id)
		if (!appTrackItemInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'appTrackItem.label', default: 'AppTrackItem'),
				id
			])
			redirect(action: "list")
			return
		}

		[appTrackItemInstance: appTrackItemInstance]
	}

	def edit(Long id) {
		def appTrackItemInstance = AppTrackItem.get(id)
		if (!appTrackItemInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'appTrackItem.label', default: 'AppTrackItem'),
				id
			])
			redirect(action: "list")
			return
		}

		[appTrackItemInstance: appTrackItemInstance]
	}

	def update(Long id, Long version) {
		def appTrackItemInstance = AppTrackItem.get(id)
		if (!appTrackItemInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'appTrackItem.label', default: 'AppTrackItem'),
				id
			])
			redirect(action: "list")
			return
		}

		if (version != null) {
			if (appTrackItemInstance.version > version) {
				appTrackItemInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						[
							message(code: 'appTrackItem.label', default: 'AppTrackItem')] as Object[],
						"Another user has updated this AppTrackItem while you were editing")
				render(view: "edit", model: [appTrackItemInstance: appTrackItemInstance])
				return
			}
		}

		appTrackItemInstance.properties = params

		if (!appTrackItemInstance.save(flush: true)) {
			render(view: "edit", model: [appTrackItemInstance: appTrackItemInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [
			message(code: 'appTrackItem.label', default: 'AppTrackItem'),
			appTrackItemInstance.id
		])
		redirect(action: "show", id: appTrackItemInstance.id)
	}

	def delete(Long id) {
		def appTrackItemInstance = AppTrackItem.get(id)
		if (!appTrackItemInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'appTrackItem.label', default: 'AppTrackItem'),
				id
			])
			redirect(action: "list")
			return
		}

		try {
			appTrackItemInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [
				message(code: 'appTrackItem.label', default: 'AppTrackItem'),
				id
			])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [
				message(code: 'appTrackItem.label', default: 'AppTrackItem'),
				id
			])
			redirect(action: "show", id: id)
		}
	}
}
