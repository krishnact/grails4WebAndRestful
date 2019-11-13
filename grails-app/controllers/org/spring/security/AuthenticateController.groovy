package org.spring.security

import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

@Api(value = '/api/login', description = 'Bearer Token Creator', tags =['Authenticate'])
class AuthenticateController {
    static allowedMethods = [index: "POST"]
    /**
     * @param max
     * @return
     */
    @ApiOperation(
            value = "Generate bearer token",
            nickname = "/",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "POST",
            response = BearerTokenCmd.class,
            responseContainer = 'map'
    )
    @ApiResponses([
            @ApiResponse(code = 405, message = "Method Not Allowed. Only POST is allowed"),
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(name = "body",
                    paramType = "body",
                    required = true,
                    value = "Requires CreditCard Details",
                    dataType = "org.grails.springsec.BearerTokenRequest")
    ])

    def index() {

        throw new RuntimeException('Show not ever come here')
    }
}


class BearerTokenRequest {
    String username
    String password
}

class BearerTokenCmd {
    String accessToken
    String refreshToken
    List<String> roles
    String username
}
