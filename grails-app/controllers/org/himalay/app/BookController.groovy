package org.himalay.app

import org.spring.security.User
import grails.converters.JSON

import grails.gorm.transactions.Transactional
import grails.rest.RestfulController
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import grails.plugin.springsecurity.annotation.Secured
import static org.springframework.http.HttpStatus.*
import org.spring.security.Roles

@Api(value = '/api/v1/book', description = 'Book resource', tags =['Book'])
@Secured([Roles.ROLE_ACCOUNTADMIN, Roles.ROLE_ACCOUNTUSER])
class BookController extends RestfulController{
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", show: 'GET', index: 'GET']

    //BookService bookService

    BookController() {
        super(Book)
    }

    /**
     * @param max
     * @return
     */
    @ApiOperation(
            value = "List Books",
            nickname = "/",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "GET",
            response = Book.class,
            responseContainer = 'map'
    )
    @ApiResponses([
            @ApiResponse(code = 405, message = "Method Not Allowed. Only GET is allowed"),
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(name = "Authorization",
                    paramType = "header",
                    required = true,
                    defaultValue = "Bearer XXXXXX",
                    value = "Authorization",
                    dataType = "string"),
            @ApiImplicitParam(name = 'offset',
                    value = 'Records to skip',
                    defaultValue = '0',
                    paramType = 'query',
                    dataType = 'int'),
            @ApiImplicitParam(name = 'max',
                    value = 'Max records to return',
                    defaultValue = '10',
                    paramType = 'query',
                    dataType = 'int'),
            @ApiImplicitParam(name = 'sort',
                    value = 'Field to sort by',
                    defaultValue = 'id',
                    paramType = 'query',
                    dataType = 'string'),
            @ApiImplicitParam(name = 'order',
                    value = 'Order to sort by',
                    defaultValue = 'asc',
                    paramType = 'query',
                    dataType = 'string')
    ])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def retVal = Book.list(params)
        String accepts = request.getHeader('Accept')
        switch (accepts){
            case "application/json":  render retVal.collectEntries{it->
                return [it.id, it]
            } as JSON; break;
            default: respond retVal, model:[bookCount: Book.count()]
        }
    }


    @ApiOperation(
            value = "Show Book",
            nickname = "/{id}",
            produces = "application/json",
            httpMethod = "GET",
            response = Book.class
    )
    @ApiResponses([
            @ApiResponse(code = 405, message = "Method Not Allowed. Only GET is allowed"),
            @ApiResponse(code = 404, message = "Resource Not Found")
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(name = 'id',
                    value = 'Resource id',
                    paramType = 'path',
                    dataType = 'string',
                    required = true)
    ])
    @Transactional
    def show() {
        Book instance = (Book) queryForResource(params.id)

        String accepts = request.getHeader('Accept')
        switch (accepts){
            case "application/json":  render instance as JSON; break;
            default:
                respond instance
        }
    }


    @ApiOperation(
            value = "Create Book",
            notes = "Creates a new Book. Accepts a Book json.",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "POST",
            nickname = "/",
            response = org.himalay.app.Book.class
    )
    @ApiResponses([
            @ApiResponse(code = 405,
                    message = "Method Not Allowed. Only POST is allowed"),

            @ApiResponse(code = 404,
                    message = "Method Not Found")
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(name = "Authorization",
                    paramType = "header",
                    required = true,
                    defaultValue = "Bearer XXXXXX",
                    value = "Authorization",
                    dataType = "string"),
            @ApiImplicitParam(name = "body",
                    paramType = "body",
                    required = true,
                    value = "Requires Book Details",
                    dataType = "org.himalay.app.Book")
    ])
    @Transactional
    def save() {
        if(handleReadOnly()) {
            return
        }
        def instance = createResource()

        instance.validate()
        if (instance.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond instance.errors, view:'create' // STATUS CODE 422
            return
        }

        saveResource instance

        String accepts = request.getHeader('Accept')
        switch (accepts){
            case "application/json":  render instance as JSON; break;
            default:
                request.withFormat {
                    form multipartForm {
                        flash.message = message(code: 'default.created.message', args: [classMessageArg, instance.id])
                        redirect instance
                    }
                    json {   render instance as JSON }
                    '*' { respond instance, [status: OK] }
                }
        }



    }


    @ApiOperation(
            value = "Delete Book",
            nickname = "/{id}",
            httpMethod = "DELETE",
            response = Book.class
    )
    @ApiResponses([
            @ApiResponse(code = 405, message = "Method Not Allowed. Only GET is allowed"),
            @ApiResponse(code = 404, message = "Resource Not Found")
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(name = 'id',
                    value = 'Resource id',
                    paramType = 'path',
                    dataType = 'string',
                    required = true)
    ])
    @Transactional
    def delete() {

        if(handleReadOnly()) {
            return
        }

        def instance = queryForResource(params.id)
        if (instance == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        deleteResource instance

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [classMessageArg, instance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT } // NO CONTENT STATUS CODE
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'book.label', default: 'Book'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    /**
     * Updates a resource for the given id
     * @param id
     */
    @ApiOperation(
            value = "Update Book",
            notes = "Updates an existing Book. Accepts a Book json.",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "PUT",
            nickname = "/{id}",
            response = org.himalay.app.Book.class
    )
    @ApiResponses([
            @ApiResponse(code = 405,
                    message = "Method Not Allowed. Only POST is allowed"),

            @ApiResponse(code = 404,
                    message = "Method Not Found")
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(name = 'id',
                    value = 'Resource id',
                    paramType = 'path',
                    dataType = 'string',
                    required = true),
            @ApiImplicitParam(name = "body",
                    paramType = "body",
                    required = true,
                    value = "Requires Book Details",
                    dataType = "org.himalay.app.Book")
    ])
    @Transactional
    def update() {
        if(handleReadOnly()) {
            return
        }

        Book instance = queryForResource(params.id)
        if (instance == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        instance.properties = getObjectToBind()
        instance.validate()
        if (instance.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond instance.errors, view:'edit' // STATUS CODE 422
            return
        }

        instance = updateResource instance
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [classMessageArg, instance.id])
                redirect instance
            }
            json {   render instance as JSON }
            '*' { respond instance, [status: OK] }
        }
    }

}
class BookCmd{
    String title
    String isbn
    String author

    static belongsTo = [user: User]

    static constraints = {
        title blank: false
        isbn nullable: true
        author nullable: true
    }

}