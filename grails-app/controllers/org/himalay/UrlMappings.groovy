package org.himalay

class UrlMappings {

    static mappings = {

        /**
         * Example of a general CRUD API
         */
        "/api/v1/book/$id"(controller: 'book', action: "show", method: "GET")
        "/api/v1/book/"(controller: 'book', action: "index", method: "GET")
        "/api/v1/book/"(controller: 'book', action: "save", method: "POST")
        "/api/v1/book/$id"(controller:  'book', action: "update", method: "PUT")
        "/api/v1/book/$id"(controller: 'book', action: "delete", method: "DELETE")

        "/api/v1/plotlyChart/$id"(controller: 'plotlyChart', action: "show", method: "GET")
        "/api/v1/plotlyChart/"(controller: 'plotlyChart', action: "index", method: "GET")
        "/api/v1/plotlyChart/$id"(controller: 'plotlyChart', action: "update", method: "PUT")

/**
        "/api/v1/$controller/$id"(action: "show", method: "GET")
        "/api/v1/$controller/"(action: "index", method: "GET")
        "/api/v1/$controller/"(action: "save", method: "POST")
        "/api/v1/$controller/$id"(action: "update", method: "PUT")
        "/api/v1/$controller/$id"(action: "delete", method: "DELETE")

 Defining a mapping like this is important.
 "/book/$action?/$id?(.$format)?" {controller = 'book'}

 If this is not mapped then best mapping ends up being the one defined for rest URLs
 -->"/api/v1/$controller/$id"(action: "show", method: "GET")<--

 because they are more specific.

 During the reverse search for controller/action, they match because ControllerAction/ HTTP Verb
 combination hit before the general maping shown hits match.
 "/$controller/$action?/$id?(.$format)?" {
 constraints {
 // apply constraints here
 }
 }

 As per
 org.grails.web.mapping.DefaultUrlMappingsHolder.groovy
 Function: resolveUrlCreator:
 Line :  363

 Why is it important? Because of link rewriting feature (eg. g:link tag) . When tags try to compose a link based upon resorce/controller/action/id etc
 they do a reverse search to find what could be best matching controller action and end up mapping CRUD control buttons to Rest API end points and
 mess up everything..., to search for stuff like bearer token.

 */

        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        '/'(view: '/index')
        "/grails"(view:"/grails")
        "/sb-admin"(view:"/sb-admin")
        "/facetti"(view:"/facetti")
        "/plotly"(view:"/plotly")
        '500'(view: '/error')
        '404'(view: '/notFound')
    }
}
