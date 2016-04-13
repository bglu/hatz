import de.bglu.hatz.Hat

class BootStrap {

    def init = { servletContext ->
    	if (Hat.count() < 1) {

    		['Fedora', 'Stretson', 'County'].each {
    			new Hat(name: it).save(flush: true, failOnError: true)	
    		}
    	}
    }
    def destroy = {
    }
}
