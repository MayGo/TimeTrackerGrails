class UrlMappings {

	static mappings = {


		"/"	{
			controller	= 'timeline'
			action		= { 'timeline' }
		}

		"/$controller/$action?/$id?"{ constraints { // apply constraints here
			} }
		/* 
		 * System Pages without controller 
		 */
		"403"	(view:'/_errors/403')
		"404"	(view:'/_errors/404')
		"500"	(view:'/_errors/error')
		"503"	(view:'/_errors/503')
	}
}
