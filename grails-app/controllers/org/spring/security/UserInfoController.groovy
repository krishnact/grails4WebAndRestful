package org.spring.security

import grails.plugin.springsecurity.SpringSecurityService
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class UserInfoController {

    UserInfoService userInfoService
    SpringSecurityService springSecurityService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond userInfoService.list(params), model:[userInfoCount: userInfoService.count()]
    }


    def show() {
        UserInfo uInfo = UserInfo.findByUser((User)springSecurityService.getCurrentUser())
        if (uInfo == null){
            uInfo = new UserInfo()
            uInfo.user = (User)springSecurityService.getCurrentUser()
            uInfo.firstName = uInfo.user.username
            uInfo.lastName  = uInfo.user.username;
            userInfoService.save(uInfo)
        }
        respond uInfo
    }


    def create() {
        respond new UserInfo(params)
    }

    def save(UserInfo userInfo) {
        if (userInfo == null) {
            notFound()
            return
        }

        try {
            userInfoService.save(userInfo)
        } catch (ValidationException e) {
            respond userInfo.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'userInfo.label', default: 'UserInfo'), userInfo.id])
                redirect userInfo
            }
            '*' { respond userInfo, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond userInfoService.get(id)
    }

    def update(UserInfo userInfo) {
        if (userInfo == null) {
            notFound()
            return
        }

        try {
            userInfoService.save(userInfo)
        } catch (ValidationException e) {
            respond userInfo.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'userInfo.label', default: 'UserInfo'), userInfo.id])
                redirect userInfo
            }
            '*'{ respond userInfo, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        userInfoService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'userInfo.label', default: 'UserInfo'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'userInfo.label', default: 'UserInfo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
