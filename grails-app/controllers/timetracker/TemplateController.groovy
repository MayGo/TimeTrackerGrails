package timetracker

class TemplateController {



	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def serve(){
		println "................."
		println $contr
		println $act
		render(template:"/$contr/$act")
	}

}
