package timetracker

import org.springframework.dao.DataIntegrityViolationException

/**
 * LogTrackItemController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class LogTrackItemController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [logTrackItemInstanceList: LogTrackItem.list(params), logTrackItemInstanceTotal: LogTrackItem.count()]
    }

    def create() {
        [logTrackItemInstance: new LogTrackItem(params)]
    }

    def save() {
        def logTrackItemInstance = new LogTrackItem(params)
        if (!logTrackItemInstance.save(flush: true)) {
            render(view: "create", model: [logTrackItemInstance: logTrackItemInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'logTrackItem.label', default: 'LogTrackItem'), logTrackItemInstance.id])
        redirect(action: "show", id: logTrackItemInstance.id)
    }

    def show() {
        def logTrackItemInstance = LogTrackItem.get(params.id)
        if (!logTrackItemInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'logTrackItem.label', default: 'LogTrackItem'), params.id])
            redirect(action: "list")
            return
        }

        [logTrackItemInstance: logTrackItemInstance]
    }

    def edit() {
        def logTrackItemInstance = LogTrackItem.get(params.id)
        if (!logTrackItemInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'logTrackItem.label', default: 'LogTrackItem'), params.id])
            redirect(action: "list")
            return
        }

        [logTrackItemInstance: logTrackItemInstance]
    }

    def update() {
        def logTrackItemInstance = LogTrackItem.get(params.id)
        if (!logTrackItemInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'logTrackItem.label', default: 'LogTrackItem'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (logTrackItemInstance.version > version) {
                logTrackItemInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'logTrackItem.label', default: 'LogTrackItem')] as Object[],
                          "Another user has updated this LogTrackItem while you were editing")
                render(view: "edit", model: [logTrackItemInstance: logTrackItemInstance])
                return
            }
        }

        logTrackItemInstance.properties = params

        if (!logTrackItemInstance.save(flush: true)) {
            render(view: "edit", model: [logTrackItemInstance: logTrackItemInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'logTrackItem.label', default: 'LogTrackItem'), logTrackItemInstance.id])
        redirect(action: "show", id: logTrackItemInstance.id)
    }

    def delete() {
        def logTrackItemInstance = LogTrackItem.get(params.id)
        if (!logTrackItemInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'logTrackItem.label', default: 'LogTrackItem'), params.id])
            redirect(action: "list")
            return
        }

        try {
            logTrackItemInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'logTrackItem.label', default: 'LogTrackItem'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'logTrackItem.label', default: 'LogTrackItem'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
