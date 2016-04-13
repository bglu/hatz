package de.bglu.hatz



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class HatController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Hat.list(params), model:[hatInstanceCount: Hat.count()]
    }

    def show(Hat hatInstance) {
        respond hatInstance
    }

    def create() {
        respond new Hat(params)
    }

    @Transactional
    def save(Hat hatInstance) {
        if (hatInstance == null) {
            notFound()
            return
        }

        if (hatInstance.hasErrors()) {
            respond hatInstance.errors, view:'create'
            return
        }

        hatInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'hat.label', default: 'Hat'), hatInstance.id])
                redirect hatInstance
            }
            '*' { respond hatInstance, [status: CREATED] }
        }
    }

    def edit(Hat hatInstance) {
        respond hatInstance
    }

    @Transactional
    def update(Hat hatInstance) {
        if (hatInstance == null) {
            notFound()
            return
        }

        if (hatInstance.hasErrors()) {
            respond hatInstance.errors, view:'edit'
            return
        }

        hatInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Hat.label', default: 'Hat'), hatInstance.id])
                redirect hatInstance
            }
            '*'{ respond hatInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Hat hatInstance) {

        if (hatInstance == null) {
            notFound()
            return
        }

        hatInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Hat.label', default: 'Hat'), hatInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'hat.label', default: 'Hat'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
