package timetracker

import org.springframework.dao.DataIntegrityViolationException

/**
 * TrackItemController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class TrackItemController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [trackItemInstanceList: TrackItem.list(params), trackItemInstanceTotal: TrackItem.count()]
    }

    def create() {
        [trackItemInstance: new TrackItem(params)]
    }

    def save() {
        def trackItemInstance = new TrackItem(params)
        if (!trackItemInstance.save(flush: true)) {
            render(view: "create", model: [trackItemInstance: trackItemInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'trackItem.label', default: 'TrackItem'), trackItemInstance.id])
        redirect(action: "show", id: trackItemInstance.id)
    }

    def show() {
        def trackItemInstance = TrackItem.get(params.id)
        if (!trackItemInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'trackItem.label', default: 'TrackItem'), params.id])
            redirect(action: "list")
            return
        }

        [trackItemInstance: trackItemInstance]
    }

    def edit() {
        def trackItemInstance = TrackItem.get(params.id)
        if (!trackItemInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'trackItem.label', default: 'TrackItem'), params.id])
            redirect(action: "list")
            return
        }

        [trackItemInstance: trackItemInstance]
    }

    def update() {
        def trackItemInstance = TrackItem.get(params.id)
        if (!trackItemInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'trackItem.label', default: 'TrackItem'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (trackItemInstance.version > version) {
                trackItemInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'trackItem.label', default: 'TrackItem')] as Object[],
                          "Another user has updated this TrackItem while you were editing")
                render(view: "edit", model: [trackItemInstance: trackItemInstance])
                return
            }
        }

        trackItemInstance.properties = params

        if (!trackItemInstance.save(flush: true)) {
            render(view: "edit", model: [trackItemInstance: trackItemInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'trackItem.label', default: 'TrackItem'), trackItemInstance.id])
        redirect(action: "show", id: trackItemInstance.id)
    }

    def delete() {
        def trackItemInstance = TrackItem.get(params.id)
        if (!trackItemInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'trackItem.label', default: 'TrackItem'), params.id])
            redirect(action: "list")
            return
        }

        try {
            trackItemInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'trackItem.label', default: 'TrackItem'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'trackItem.label', default: 'TrackItem'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
