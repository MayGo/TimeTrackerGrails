package timetracker

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import static javax.servlet.http.HttpServletResponse.*

class LogTrackItemController {

    static final int SC_UNPROCESSABLE_ENTITY = 422

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() { }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
		response.setIntHeader('X-Pagination-Total', LogTrackItem.count())
		render LogTrackItem.list(params) as JSON
    }

    def save() {
        def logTrackItemInstance = new LogTrackItem(request.JSON)
        def responseJson = [:]
        if (logTrackItemInstance.save(flush: true)) {
            response.status = SC_CREATED
            responseJson.id = logTrackItemInstance.id
            responseJson.message = message(code: 'default.created.message', args: [message(code: 'logTrackItem.label', default: 'LogTrackItem'), logTrackItemInstance.id])
        } else {
            response.status = SC_UNPROCESSABLE_ENTITY
            responseJson.errors = logTrackItemInstance.errors.fieldErrors.collectEntries {
                [(it.field): message(error: it)]
            }
        }
        render responseJson as JSON
    }

    def get() {
        def logTrackItemInstance = LogTrackItem.get(params.id)
        if (logTrackItemInstance) {
			render logTrackItemInstance as JSON
        } else {
			notFound params.id
		}
    }

    def update() {
        def logTrackItemInstance = LogTrackItem.get(params.id)
        if (!logTrackItemInstance) {
            notFound params.id
            return
        }

        def responseJson = [:]

        if (request.JSON.version != null) {
            if (logTrackItemInstance.version > request.JSON.version) {
				response.status = SC_CONFLICT
				responseJson.message = message(code: 'default.optimistic.locking.failure',
						args: [message(code: 'logTrackItem.label', default: 'LogTrackItem')],
						default: 'Another user has updated this LogTrackItem while you were editing')
				cache false
				render responseJson as JSON
				return
            }
        }

        logTrackItemInstance.properties = request.JSON

        if (logTrackItemInstance.save(flush: true)) {
            response.status = SC_OK
            responseJson.id = logTrackItemInstance.id
            responseJson.message = message(code: 'default.updated.message', args: [message(code: 'logTrackItem.label', default: 'LogTrackItem'), logTrackItemInstance.id])
        } else {
            response.status = SC_UNPROCESSABLE_ENTITY
            responseJson.errors = logTrackItemInstance.errors.fieldErrors.collectEntries {
                [(it.field): message(error: it)]
            }
        }

        render responseJson as JSON
    }

    def delete() {
        def logTrackItemInstance = LogTrackItem.get(params.id)
        if (!logTrackItemInstance) {
            notFound params.id
            return
        }

        def responseJson = [:]
        try {
            logTrackItemInstance.delete(flush: true)
            responseJson.message = message(code: 'default.deleted.message', args: [message(code: 'logTrackItem.label', default: 'LogTrackItem'), params.id])
        } catch (DataIntegrityViolationException e) {
            response.status = SC_CONFLICT
            responseJson.message = message(code: 'default.not.deleted.message', args: [message(code: 'logTrackItem.label', default: 'LogTrackItem'), params.id])
        }
        render responseJson as JSON
    }

    private void notFound(id) {
        response.status = SC_NOT_FOUND
        def responseJson = [message: message(code: 'default.not.found.message', args: [message(code: 'logTrackItem.label', default: 'LogTrackItem'), params.id])]
        render responseJson as JSON
    }
}
