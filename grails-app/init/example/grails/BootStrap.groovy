package example.grails

import grails.compiler.GrailsCompileStatic
import org.himalay.auth.Roles

@GrailsCompileStatic
class BootStrap {

    //AnnouncementService announcementService

    def init = { servletContext ->

        def authorities = Roles.ALL_ROLES
        authorities.each { String authority ->
            if ( !SecurityRole.findByAuthority(authority) ) {
                new SecurityRole(authority: authority).save()
            }
        }

        if ( !User.findByUsername('dad') ) {
            def u = new User(username: 'dad', password: 'app123')
            u.save()
            new UserSecurityRole(user: u, securityRole: SecurityRole.findByAuthority(Roles.ROLE_ACCOUNTADMIN)).save()
        }

        ['kid1','kid2'].each {String kid->
            if (!User.findByUsername(kid)) {
                def u = new User(username: kid, password: 'app123')
                u.save()
                new UserSecurityRole(user: u, securityRole: SecurityRole.findByAuthority(Roles.ROLE_ACCOUNTADMIN)).save()
            }
        }
       // announcementService.save('The Hound of the Baskervilles')
    }
    def destroy = {
    }
}
