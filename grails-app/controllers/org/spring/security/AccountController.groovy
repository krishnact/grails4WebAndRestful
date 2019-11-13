package org.spring.security

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class AccountController {

    AccountService accountService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond accountService.list(params), model:[accountCount: accountService.count()]
    }

    def show(Long id) {
        respond accountService.get(id)
    }

    def create() {
        respond new Account(params)
    }

    def save(Account account) {
        if (account == null) {
            notFound()
            return
        }

        try {
            accountService.save(account)
        } catch (ValidationException e) {
            respond account.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'account.label', default: 'Account'), account.id])
                redirect account
            }
            '*' { respond account, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond accountService.get(id)
    }

    def update(Account account) {
        if (account == null) {
            notFound()
            return
        }

        try {
            accountService.save(account)
        } catch (ValidationException e) {
            respond account.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'account.label', default: 'Account'), account.id])
                redirect account
            }
            '*'{ respond account, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        accountService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'account.label', default: 'Account'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'account.label', default: 'Account'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
