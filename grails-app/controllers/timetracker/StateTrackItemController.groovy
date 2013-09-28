package timetracker

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import static javax.servlet.http.HttpServletResponse.*

class StateTrackItemController {

    static final int SC_UNPROCESSABLE_ENTITY = 422

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() { }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
		response.setIntHeader('X-Pagination-Total', StateTrackItem.count())
		render StateTrackItem.list(params) as JSON
    }

    def save() {
        def stateTrackItemInstance = new StateTrackItem(request.JSON)
        def responseJson = [:]
        if (stateTrackItemInstance.save(flush: true)) {
            response.status = SC_CREATED
            responseJson.id = stateTrackItemInstance.id
            responseJson.message = message(code: 'default.created.message', args: [message(code: 'stateTrackItem.label', default: 'StateTrackItem'), stateTrackItemInstance.id])
        } else {
            response.status = SC_UNPROCESSABLE_ENTITY
            responseJson.errors = stateTrackItemInstance.errors.fieldErrors.collectEntries {
                [(it.field): message(error: it)]
            }
        }
        render responseJson as JSON
    }

    def get() {
        def stateTrackItemInstance = StateTrackItem.get(params.id)
        if (stateTrackItemInstance) {
			render stateTrackItemInstance as JSON
        } else {
			notFound params.id
		}
    }

    def update() {
        def stateTrackItemInstance = StateTrackItem.get(params.id)
        if (!stateTrackItemInstance) {
            notFound params.id
            return
        }

        def responseJson = [:]

        if (request.JSON.version != null) {
            if (stateTrackItemInstance.version > request.JSON.version) {
				response.status = SC_CONFLICT
				responseJson.message = message(code: 'default.optimistic.locking.failure',
						args: [message(code: 'stateTrackItem.label', default: 'StateTrackItem')],
						default: 'Another user has updated this StateTrackItem while you were editing')
				cache false
				render responseJson as JSON
				return
            }
        }

        stateTrackItemInstance.properties = request.JSON

        if (stateTrackItemInstance.save(flush: true)) {
            response.status = SC_OK
            responseJson.id = stateTrackItemInstance.id
            responseJson.message = message(code: 'default.updated.message', args: [message(code: 'stateTrackItem.label', default: 'StateTrackItem'), stateTrackItemInstance.id])
        } else {
            response.status = SC_UNPROCESSABLE_ENTITY
            responseJson.errors = stateTrackItemInstance.errors.fieldErrors.collectEntries {
                [(it.field): message(error: it)]
            }
        }

        render responseJson as JSON
    }

    def delete() {
        def stateTrackItemInstance = StateTrackItem.get(params.id)
        if (!stateTrackItemInstance) {
            notFound params.id
            return
        }

        def responseJson = [:]
        try {
            stateTrackItemInstance.delete(flush: true)
            responseJson.message = message(code: 'default.deleted.message', args: [message(code: 'stateTrackItem.label', default: 'StateTrackItem'), params.id])
        } catch (DataIntegrityViolationException e) {
            response.status = SC_CONFLICT
            responseJson.message = message(code: 'default.not.deleted.message', args: [message(code: 'stateTrackItem.label', default: 'StateTrackItem'), params.id])
        }
        render responseJson as JSON
    }

    private void notFound(id) {
        response.status = SC_NOT_FOUND
        def responseJson = [message: message(code: 'default.not.found.message', args: [message(code: 'stateTrackItem.label', default: 'StateTrackItem'), params.id])]
        render responseJson as JSON
    }
}
