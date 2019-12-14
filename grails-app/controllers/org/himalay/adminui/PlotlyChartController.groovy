package org.himalay.adminui

import grails.converters.JSON

import grails.gorm.transactions.Transactional
import grails.rest.RestfulController
import org.grails.web.servlet.mvc.GrailsDispatcherServlet
import org.springframework.beans.factory.annotation.Value
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import groovy.json.JsonSlurper;
import groovy.json.JsonBuilder

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.*

@Api(value = '/api/v1/plotlyChart', description = 'PlotlyChart resource', tags = ['PlotlyChart'])
class PlotlyChartController extends RestfulController {
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", show: 'GET', index: 'GET']
    @Value('${app.plotlyPlotsCache}')
    private String plotlyPlotsCache;
    @Value('${app.plotlyPlotsMocks}')
    private String plotlyPlotsMocks;

    PlotlyChartController() {
        super(PlotlyChart)
    }

/**
 * @param max
 * @return
 */
    @ApiOperation(
            value = "List PlotlyCharts",
            nickname = "/",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "GET",
            response = PlotlyChart.class,
            responseContainer = 'array'
    )
    @ApiResponses([
            @ApiResponse(code = 405, message = "Method Not Allowed. Only GET is allowed"),
    ])
    @ApiImplicitParams([
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
        GrailsDispatcherServlet
        params.max = Math.min(max ?: 10, 100)
        String serverBase = this.getServerBaseURL(request);
        def retVal = new File(this.plotlyPlotsCache).listFiles().collect {
            new PlotlyChart(it, serverBase).toMap()
        }

        String accepts = request.getHeader('Accept')
        response.setHeader('Access-Control-Allow-Origin', '*');
        switch (accepts) {
            case "application/vnd.github.v3.raw":
            case "application/json": render retVal as JSON; break;
            default: respond retVal, model: [plotlyChartCount: retVal.size()]
        }
    }


    @ApiOperation(
            value = "Show PlotlyChart",
            nickname = "/{id}",
            produces = "application/json",
            httpMethod = "GET",
            response = PlotlyChart.class
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
    def show(String id) {

        def jsonMap = new JsonSlurper().parse(new File(new File(plotlyPlotsCache), id));

        response.setHeader('Access-Control-Allow-Origin', '*');
        String accepts = request.getHeader('Accept')
        switch (accepts) {
            case "application/vnd.github.v3.raw":
            case "application/json": render jsonMap as JSON;
                break;
            default:
                respond instance
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'plotlyChart.label', default: 'PlotlyChart'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

/**
 * Updates a resource for the given id
 * @param id
 */
    @ApiOperation(
            value = "Update PlotlyChart",
            notes = "Updates an existing PlotlyChart. Accepts a PlotlyChart json.",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "PUT",
            nickname = "/{id}",
            response = PlotlyChartDef.class
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
                    value = "Requires PlotlyChart Details",
                    dataType = "org.himalay.app.PlotlyChartDef")
    ])
    @Transactional
    def update(String id) {
        File file = new File(new File(plotlyPlotsCache), id)

        PlotlyChartDef instance = PlotlyChartDef.newInstance()
        bindData instance, getObjectToBind()
        String jsText = new JsonBuilder(instance).toString()
        file.text = jsText

        response.setHeader('Access-Control-Allow-Origin', '*');
        String accepts = request.getHeader('Accept')
        switch (accepts) {
            case "application/vnd.github.v3.raw":
            case "application/json": render instance as JSON;
                break;
            default:
                request.withFormat {
                    form multipartForm {
                        flash.message = message(code: 'default.created.message', args: [classMessageArg, instance.id])
                        redirect instance
                    }
                    json { render instance as JSON }
                    '*' { respond instance, [status: OK] }
                }
        }
    }
    private String getServerBaseURL(HttpServletRequest request){
        return (request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath())
    }
}

/**
 A class that will return JSON in this format:{"name": "0.json",
 "path": "test/image/mocks/0.json",
 "sha": "ee15ef0424e3ceea534a2313f91c52a174a440bb",
 "size": 33542,
 "url": "https://api.github.com/repos/plotly/plotly.js/contents/test/image/mocks/0.json?ref=master",
 "html_url": "https://github.com/plotly/plotly.js/blob/master/test/image/mocks/0.json",
 "git_url": "https://api.github.com/repos/plotly/plotly.js/git/blobs/ee15ef0424e3ceea534a2313f91c52a174a440bb",
 "download_url": "https://raw.githubusercontent.com/plotly/plotly.js/master/test/image/mocks/0.json",
 "type": "file",
 "_links": {"self": "https://api.github.com/repos/plotly/plotly.js/contents/test/image/mocks/0.json?ref=master",
 "git": "https://api.github.com/repos/plotly/plotly.js/git/blobs/ee15ef0424e3ceea534a2313f91c52a174a440bb",
 "html": "https://github.com/plotly/plotly.js/blob/master/test/image/mocks/0.json"}},
 */
class PlotlyChart {
    String name;
    String path = '';
    String sha = ''
    String url;
    String html_url;
    String git_url;
    String download_url;
    String type;
    long size = 0;
    Map _links;

    public PlotlyChart(File file, String serverBase) {
        this.name = file.name;
        url = "${serverBase}/api/v1/plotlyChart/${name}"
        html_url = "${serverBase}/api/v1/plotlyChart/${name}"
        git_url = "${serverBase}/api/v1/plotlyChart/${name}"
        download_url = "${serverBase}/api/v1/plotlyChart/${name}"
        type = 'file'
        size = file.length();
        _links = [self: html_url, git: git_url, html: html_url]

    }

    public Map toMap() {
        [
                name        : name,
                path        : path,
                sha         : sha,
                url         : url,
                html_url    : html_url,
                git_url     : git_url,
                download_url: download_url,
                type        : type,
                size        : size,
                _links      : _links
        ]
    }
}

class PlotlyChartDef {
    Object data;
    Object layout;
    def metadata = [key: ['val1','val2']];
}
