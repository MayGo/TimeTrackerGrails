package timetracker

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class ApplicationInfoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [applicationInfoInstanceList: ApplicationInfo.list(params), applicationInfoInstanceTotal: ApplicationInfo.count()]
    }
	
	def timeline(){
		params.max = 100
		def l=ApplicationInfo.list(params).collect{['Application',it.name, it.focusedFrom.getTime() , it.focusedUntil.getTime() ]}
		[applicationInfoInstanceList: l as JSON, applicationInfoInstanceTotal: ApplicationInfo.count()]
	}

    def create() {
        [applicationInfoInstance: new ApplicationInfo(params)]
    }

    def save() {
        def applicationInfoInstance = new ApplicationInfo(params)
        if (!applicationInfoInstance.save(flush: true)) {
            render(view: "create", model: [applicationInfoInstance: applicationInfoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'applicationInfo.label', default: 'ApplicationInfo'), applicationInfoInstance.id])
        redirect(action: "show", id: applicationInfoInstance.id)
    }

    def show(Long id) {
        def applicationInfoInstance = ApplicationInfo.get(id)
        if (!applicationInfoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'applicationInfo.label', default: 'ApplicationInfo'), id])
            redirect(action: "list")
            return
        }

        [applicationInfoInstance: applicationInfoInstance]
    }

    def edit(Long id) {
        def applicationInfoInstance = ApplicationInfo.get(id)
        if (!applicationInfoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'applicationInfo.label', default: 'ApplicationInfo'), id])
            redirect(action: "list")
            return
        }

        [applicationInfoInstance: applicationInfoInstance]
    }

    def update(Long id, Long version) {
        def applicationInfoInstance = ApplicationInfo.get(id)
        if (!applicationInfoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'applicationInfo.label', default: 'ApplicationInfo'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (applicationInfoInstance.version > version) {
                applicationInfoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'applicationInfo.label', default: 'ApplicationInfo')] as Object[],
                          "Another user has updated this ApplicationInfo while you were editing")
                render(view: "edit", model: [applicationInfoInstance: applicationInfoInstance])
                return
            }
        }

        applicationInfoInstance.properties = params

        if (!applicationInfoInstance.save(flush: true)) {
            render(view: "edit", model: [applicationInfoInstance: applicationInfoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'applicationInfo.label', default: 'ApplicationInfo'), applicationInfoInstance.id])
        redirect(action: "show", id: applicationInfoInstance.id)
    }

    def delete(Long id) {
        def applicationInfoInstance = ApplicationInfo.get(id)
        if (!applicationInfoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'applicationInfo.label', default: 'ApplicationInfo'), id])
            redirect(action: "list")
            return
        }

        try {
            applicationInfoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'applicationInfo.label', default: 'ApplicationInfo'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'applicationInfo.label', default: 'ApplicationInfo'), id])
            redirect(action: "show", id: id)
        }
    }
}
