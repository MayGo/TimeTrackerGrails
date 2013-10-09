package timetracker

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import static javax.servlet.http.HttpServletResponse.*

class AppTrackItemController {

    static final int SC_UNPROCESSABLE_ENTITY = 422

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() { }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
		params.sort = params.sort?:'beginDate'
		params.order = params.order?:'desc'
		def today = new Date()
		def day = (params.day)?new Date(params.day as long):today
		
		response.setIntHeader('X-Pagination-Total', AppTrackItem.fromDateLimitDay(day).count())
		render AppTrackItem.fromDateLimitDay(day).list(params) as JSON
    }

    def save() {
        def appTrackItemInstance = new AppTrackItem(request.JSON)
        def responseJson = [:]
        if (appTrackItemInstance.save(flush: true)) {
            response.status = SC_CREATED
            responseJson.id = appTrackItemInstance.id
            responseJson.message = message(code: 'default.created.message', args: [message(code: 'appTrackItem.label', default: 'AppTrackItem'), appTrackItemInstance.id])
        } else {
            response.status = SC_UNPROCESSABLE_ENTITY
            responseJson.errors = appTrackItemInstance.errors.fieldErrors.collectEntries {
                [(it.field): message(error: it)]
            }
        }
        render responseJson as JSON
    }

    def get() {
        def appTrackItemInstance = AppTrackItem.get(params.id)
        if (appTrackItemInstance) {
			render appTrackItemInstance as JSON
        } else {
			notFound params.id
		}
    }

    def update() {
        def appTrackItemInstance = AppTrackItem.get(params.id)
        if (!appTrackItemInstance) {
            notFound params.id
            return
        }

        def responseJson = [:]

        if (request.JSON.version != null) {
            if (appTrackItemInstance.version > request.JSON.version) {
				response.status = SC_CONFLICT
				responseJson.message = message(code: 'default.optimistic.locking.failure',
						args: [message(code: 'appTrackItem.label', default: 'AppTrackItem')],
						default: 'Another user has updated this AppTrackItem while you were editing')
				cache false
				render responseJson as JSON
				return
            }
        }

        appTrackItemInstance.properties = request.JSON

        if (appTrackItemInstance.save(flush: true)) {
            response.status = SC_OK
            responseJson.id = appTrackItemInstance.id
            responseJson.message = message(code: 'default.updated.message', args: [message(code: 'appTrackItem.label', default: 'AppTrackItem'), appTrackItemInstance.id])
        } else {
            response.status = SC_UNPROCESSABLE_ENTITY
            responseJson.errors = appTrackItemInstance.errors.fieldErrors.collectEntries {
                [(it.field): message(error: it)]
            }
        }

        render responseJson as JSON
    }

    def delete() {
        def appTrackItemInstance = AppTrackItem.get(params.id)
        if (!appTrackItemInstance) {
            notFound params.id
            return
        }

        def responseJson = [:]
        try {
            appTrackItemInstance.delete(flush: true)
            responseJson.message = message(code: 'default.deleted.message', args: [message(code: 'appTrackItem.label', default: 'AppTrackItem'), params.id])
        } catch (DataIntegrityViolationException e) {
            response.status = SC_CONFLICT
            responseJson.message = message(code: 'default.not.deleted.message', args: [message(code: 'appTrackItem.label', default: 'AppTrackItem'), params.id])
        }
        render responseJson as JSON
    }

    private void notFound(id) {
        response.status = SC_NOT_FOUND
        def responseJson = [message: message(code: 'default.not.found.message', args: [message(code: 'appTrackItem.label', default: 'AppTrackItem'), params.id])]
        render responseJson as JSON
    }
}
