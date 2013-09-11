package timetracker

import org.springframework.dao.DataIntegrityViolationException

/**
 * AppTrackItemController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class AppTrackItemController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
				def today = new Date()
				def day = (params.day)?new Date(params.day as long):today
				println day
				println AppTrackItem.fromDateLimitDay(day).list(params)
        [appTrackItemInstanceList: AppTrackItem.fromDateLimitDay(day).list(params), appTrackItemInstanceTotal: AppTrackItem.fromDateLimitDay(day).count()]
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

		flash.message = message(code: 'default.created.message', args: [message(code: 'appTrackItem.label', default: 'AppTrackItem'), appTrackItemInstance.id])
        redirect(action: "show", id: appTrackItemInstance.id)
    }

    def show() {
        def appTrackItemInstance = AppTrackItem.get(params.id)
        if (!appTrackItemInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'appTrackItem.label', default: 'AppTrackItem'), params.id])
            redirect(action: "list")
            return
        }

        [appTrackItemInstance: appTrackItemInstance]
    }

    def edit() {
        def appTrackItemInstance = AppTrackItem.get(params.id)
        if (!appTrackItemInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'appTrackItem.label', default: 'AppTrackItem'), params.id])
            redirect(action: "list")
            return
        }

        [appTrackItemInstance: appTrackItemInstance]
    }

    def update() {
        def appTrackItemInstance = AppTrackItem.get(params.id)
        if (!appTrackItemInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'appTrackItem.label', default: 'AppTrackItem'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (appTrackItemInstance.version > version) {
                appTrackItemInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'appTrackItem.label', default: 'AppTrackItem')] as Object[],
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

		flash.message = message(code: 'default.updated.message', args: [message(code: 'appTrackItem.label', default: 'AppTrackItem'), appTrackItemInstance.id])
        redirect(action: "show", id: appTrackItemInstance.id)
    }

    def delete() {
        def appTrackItemInstance = AppTrackItem.get(params.id)
        if (!appTrackItemInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'appTrackItem.label', default: 'AppTrackItem'), params.id])
            redirect(action: "list")
            return
        }

        try {
            appTrackItemInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'appTrackItem.label', default: 'AppTrackItem'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'appTrackItem.label', default: 'AppTrackItem'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
