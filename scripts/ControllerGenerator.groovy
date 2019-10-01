@Grapes([
        @GrabResolver(name='jitpack.io', root='https://jitpack.io'),
        @Grab('org.slf4j:slf4j-log4j12:1.7.7'),
        @Grab('com.h2database:h2:1.4.196'),
        @Grab('com.github.krishnact:commandlinetool-base:0.4.10'),
        @GrabExclude(group = 'org.codehaus.groovy', module='groovy-sql') ,
        @GrabExclude(group = 'org.codehaus.groovy', module='groovy-cli-commons')  ,
        @GrabExclude(group = 'org.codehaus.groovy', module='groovy-json')         ,
        @GrabExclude(group = 'org.codehaus.groovy', module='groovy-xml')           ,
        @GrabExclude(group = 'org.codehaus.groovy', module='groovy-templates')
])
import org.himalay.commandline.Option;
import org.himalay.commandline.CLTBase;
import groovy.cli.commons.OptionAccessor;

class ControllerGenerator extends CLTBase{

    @Option(required= true)
    String packageName

    @Option(required= true)
    String resourceName


    @Override
    protected void realMain(OptionAccessor arg0) {
        String resourceNameCamel = resourceName[0].toLowerCase() + resourceName.substring(1)
        Map params = [packageName: packageName,resourceName:resourceName,resourceNameCamel:resourceNameCamel]
        println (makeTemplate(controllerTemplate,params));
    }

    public static void main(String [] args) {
        CLTBase._main(new ControllerGenerator(), args);
    }


    String controllerTemplate =
            '''
package ${packageName}
import grails.converters.JSON

import grails.gorm.transactions.Transactional
import grails.rest.RestfulController
import grails.web.http.HttpHeaders
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import grails.plugin.springsecurity.annotation.Secured
import static org.springframework.http.HttpStatus.*
import org.himalay.auth.Roles

@Api(value = '/api/v1/${resourceNameCamel}', description = '${resourceName} resource', tags =['${resourceName}'])
@Secured([Roles.ROLE_ACCOUNTADMIN, Roles.ROLE_ACCOUNTUSER])
class ${resourceName}Controller extends RestfulController{
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", show: 'GET', index: 'GET']

    //${resourceName}Service ${resourceNameCamel}Service

    ${resourceName}Controller() {
        super(${resourceName})
    }

    /**
     * @param max
     * @return
     */
    @ApiOperation(
            value = "List ${resourceName}s",
            nickname = "/",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "GET",
            response = ${resourceName}.class,
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
        def retVal = ${resourceName}.list(params)
        String accepts = request.getHeader('Accept')
        switch (accepts){
            case "application/json":  render retVal.collectEntries{it->
                return [it.id, it]
            } as JSON; break;
            default: respond retVal, model:[${resourceNameCamel}Count: ${resourceName}.count()]
        }
    }


    @ApiOperation(
            value = "Show ${resourceName}",
            nickname = "/{id}",
            produces = "application/json",
            httpMethod = "GET",
            response = ${resourceName}.class
    )
    @ApiResponses([
            @ApiResponse(code = 405, message = "Method Not Allowed. Only GET is allowed"),
            @ApiResponse(code = 404, message = "Resource Not Found")
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(name = "Authorization",
                    paramType = "header",
                    required = true,
                    defaultValue = "Bearer XXXXXX",
                    value = "Authorization",
                    dataType = "string"),
            @ApiImplicitParam(name = 'id',
                    value = 'Resource id',
                    paramType = 'path',
                    dataType = 'string',
                    required = true)
    ])
    @Transactional
    def show() {
        ${resourceName} instance = (${resourceName}) queryForResource(params.id)

        String accepts = request.getHeader('Accept')
        switch (accepts){
            case "application/json":  render instance as JSON; break;
            default:
                respond instance
        }
    }


    @ApiOperation(
            value = "Create ${resourceName}",
            notes = "Creates a new ${resourceName}. Accepts a ${resourceName} json.",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "POST",
            nickname = "/",
            response = ${packageName}.${resourceName}.class
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
                    value = "Requires ${resourceName} Details",
                    dataType = "${packageName}.${resourceName}")
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
            value = "Delete ${resourceName}",
            nickname = "/{id}",
            httpMethod = "DELETE",
            response = ${resourceName}.class
    )
    @ApiResponses([
            @ApiResponse(code = 405, message = "Method Not Allowed. Only GET is allowed"),
            @ApiResponse(code = 404, message = "Resource Not Found")
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(name = "Authorization",
                    paramType = "header",
                    required = true,
                    defaultValue = "Bearer XXXXXX",
                    value = "Authorization",
                    dataType = "string"),
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
                flash.message = message(code: 'default.not.found.message', args: [message(code: '${resourceNameCamel}.label', default: '${resourceName}'), params.id])
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
            value = "Update ${resourceName}",
            notes = "Updates an existing ${resourceName}. Accepts a ${resourceName} json.",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "PUT",
            nickname = "/{id}",
            response = ${packageName}.${resourceName}.class
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
            @ApiImplicitParam(name = 'id',
                    value = 'Resource id',
                    paramType = 'path',
                    dataType = 'string',
                    required = true),
            @ApiImplicitParam(name = "body",
                    paramType = "body",
                    required = true,
                    value = "Requires ${resourceName} Details",
                    dataType = "${packageName}.${resourceName}")
    ])
    @Transactional
    def update() {
        if(handleReadOnly()) {
            return
        }

        ${resourceName} instance = queryForResource(params.id)
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

'''
}