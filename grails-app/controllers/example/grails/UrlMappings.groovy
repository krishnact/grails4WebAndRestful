package example.grails

class UrlMappings {

    static mappings = {

        "/api/v1/$controller/$id"(action: "show", method: "GET")
        "/api/v1/$controller/"(action: "index", method: "GET")
        "/api/v1/$controller/"(action: "save", method: "POST")
        "/api/v1/$controller/$id"(action: "update", method: "PUT")
        "/api/v1/$controller/$id"(action: "delete", method: "DELETE")

/**

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
        "/book/$action?/$id?(.$format)?" {controller = 'book'}
        "/giftCard/$action?/$id?(.$format)?" {controller = 'vehicle'}

        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }


        '/'(view: '/index')
        '500'(view: '/error')
        '404'(view: '/notFound')
    }
}
