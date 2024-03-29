package org.spring.security


import grails.plugin.springsecurity.annotation.Secured
import grails.plugin.springsecurity.ui.RegistrationCode
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class RegisterController extends grails.plugin.springsecurity.ui.RegisterController {
    @Override
    protected void sendVerifyRegistrationMail(RegistrationCode registrationCode, Object user, String email) {
       println ('Registration link is '+ 'register/verifyRegistration?t='+registrationCode.token)
       super.sendVerifyRegistrationMail(registrationCode, user, email);

    }
    @Secured(['permitAll'])
    def postRegister(){
        User au = (User)getAuthenticatedUser();
        render (view: "postRegister.gsp", model :[t:params.t])
    }

    public static void myLogoff(HttpServletRequest request, HttpServletResponse response) {
        CookieClearingLogoutHandler cookieClearingLogoutHandler = new CookieClearingLogoutHandler(AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY);
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        cookieClearingLogoutHandler.logout(request, response, null);
        securityContextLogoutHandler.logout(request, response, null);
    }

    def verifyRegistration(){
        super.verifyRegistration();
        myLogoff(request,response);
    }
}
