package timetracker

class TemplateController {



	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def serve(){
		println "................."
		println params.contr
		println params.act
		render(template:"/${params.contr}/${params.act}")
	}

}
