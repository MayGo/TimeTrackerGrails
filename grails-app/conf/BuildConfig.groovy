grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6

// uncomment (and adjust settings) to fork the JVM to isolate classpaths
//grails.project.fork = [
//   run: [maxMemory:1024, minMemory:64, debug:false, maxPerm:256]
//]

grails.project.dependency.resolution = {
	// inherit Grails' default dependencies
	inherits("global") { // specify dependency exclusions here; for example, uncomment this to disable ehcache:
		excludes 'jna' }
	log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
	checksums true // Whether to verify checksums on resolve
	legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

	repositories {
		inherits true // Whether to inherit repository definitions from plugins

		grailsPlugins()
		grailsHome()
		grailsCentral()

		mavenLocal()
		mavenCentral()

		// uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
		mavenRepo "http://snapshots.repository.codehaus.org/"
		mavenRepo "http://repository.codehaus.org/"
		mavenRepo "http://download.java.net/maven/2/"
		mavenRepo "http://repository.jboss.com/maven2/"
		mavenRepo "http://oss.sonatype.org/content/repositories/vaadin-snapshots/"
		mavenRepo "http://maven.vaadin.com/vaadin-addons/"
		mavenRepo "http://repo.springsource.org/release/"
	}

	dependencies {
		// specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.

		// runtime 'mysql:mysql-connector-java:5.1.22'
		compile 'net.java.dev.jna:jna:4.0.0'
		compile 'net.java.dev.jna:jna-platform:4.0.0'
	}

	plugins {
		runtime ":hibernate:$grailsVersion"
		runtime ":jquery:1.8.3"
		runtime ":resources:1.2"
		compile ":quartz:1.0-RC9"

		// Uncomment these (or add new ones) to enable additional resources capabilities
		//runtime ":zipped-resources:1.0"
		//runtime ":cached-resources:1.0"
		//runtime ":yui-minify-resources:0.1.5"

		build ":tomcat:$grailsVersion"

		runtime ":database-migration:1.3.2"

		compile ':cache:1.0.1'
		compile ":build-test-data:2.0.5"
		

	}
}
