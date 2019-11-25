package org.spring.security

import grails.compiler.GrailsCompileStatic

import java.lang.reflect.Field
import java.lang.reflect.Modifier
import org.h2.tools.Server
import org.springframework.beans.factory.annotation.Value

@GrailsCompileStatic
class BootStrap {

    //AnnouncementService announcementService
    Server tcpServer;
    Server webServer;
    @Value('${dataSource.url}')
    String databaseURL

    /**
     * Define this value as environment.development.app.h2dbArgs
     */
    @Value('${app.h2dbArgs}')
    String h2dbArgs;


    def init = { servletContext ->
        startH2DB(h2dbArgs.split(/\s+/))
        populateData();
    }

    private void startH2DB(String[] args){

        log.info "Starting H2 DB with arguments: ${args}"
        Thread.start {
            Server.main(args)
            log.info("JDBC URL : ${databaseURL}")
        }
    }

    private void populateData(){

            Account.SEED_ACCOUNT = Account.createANewAccount();
            Account.SEED_ACCOUNT.save()

            def  usersAndRoles = [
                    'root'      : 'ROLE_ROOT',
                    'admin'     : 'ROLE_ADMIN',
                    'acctadmin' : 'ROLE_ACCOUNTADMIN',
                    'acctuser1' : 'ROLE_ACCOUNTUSER',
                    'acctuser2' : 'ROLE_ACCOUNTUSER',
            ]
            Roles.populateAllAuthorities();
            Roles.populateAllSecurityRoles()


            Map<String,User> users = (Map<String,User>)usersAndRoles.collectEntries{user, roles->
                User aUser = new User(username: user, password: 'app123')
                aUser.newUserAction().save();
                roles.split('[, ]+').collect{it.trim()}.findAll{it!=''}.each{String aRole->
                    SecurityRole role = Roles.SecurityRoles[aRole]
                    if ( role == null){
                        String msg = "Role ${aRole} not found."
                        log.error(msg)
                        throw new RuntimeException(msg)
                    }else {
                        UserSecurityRole usr = new UserSecurityRole(user: aUser, securityRole: role)
                        usr.save()
                    }
                }
                return [user, aUser]
            }
    }
    def destroy = {
    }
}
