package org.spring.security

import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.context.HttpSessionSecurityContextRepository

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

@Api(value = '/api/login', description = 'Bearer Token Creator', tags =['Authenticate'])
class AuthenticateController {
    AuthenticationManager authenticationManager
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

    private void login(HttpServletRequest req, String user, String pass) {
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(user, pass);
        Authentication auth = authenticationManager.authenticate(authReq);

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        HttpSession session = req.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
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
