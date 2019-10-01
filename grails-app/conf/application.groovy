grails {
    plugin {
        springsecurity {
            rest {
                token {
                    storage {
                        jwt {
                            secret = 'pleaseChangeThisSecretForANewOne'
                        }
                    }
                }
            }
            securityConfigType = "InterceptUrlMap"  // <1>
            filterChain {
                chainMap = [
                [pattern: '/api/**',filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'],// <2>
                [pattern: '/**', filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'] // <3>
                ]
            }
            userLookup {
                userDomainClassName = 'example.grails.User' // <4>
                authorityJoinClassName = 'example.grails.UserSecurityRole' // <4>
            }
            authority {
                className = 'example.grails.SecurityRole' // <4>
            }
            interceptUrlMap = [
                    [pattern: '/',                      access: ['permitAll']],
                    [pattern: '/error',                 access: ['permitAll']],
                    [pattern: '/index',                 access: ['permitAll']],
                    [pattern: '/index.gsp',             access: ['permitAll']],
                    [pattern: '/shutdown',              access: ['permitAll']],
                    [pattern: '/assets/**',             access: ['permitAll']],
                    [pattern: '/**/js/**',              access: ['permitAll']],
                    [pattern: '/**/css/**',             access: ['permitAll']],
                    [pattern: '/**/images/**',          access: ['permitAll']],
                    [pattern: '/**/favicon.ico',        access: ['permitAll']],
                    [pattern: '/webjars/**',  access: ['permitAll']],
                    [pattern: '/apidoc',                access: ['permitAll']],
                    [pattern: '/login/**',              access: ['permitAll']], // <5>
                    [pattern: '/logout',                access: ['permitAll']],
                    [pattern: '/logout/**',             access: ['permitAll']],            
                    [pattern: '/*',                     access: ['ROLE_ACCOUNTADMIN', 'ROLE_ACCOUNTUSER']],
                    [pattern: '/*/index',               access: ['ROLE_ACCOUNTADMIN', 'ROLE_ACCOUNTUSER']],  // <6>
                    [pattern: '/*/create',              access: ['ROLE_ACCOUNTADMIN']],
                    [pattern: '/*/save',                access: ['ROLE_ACCOUNTADMIN']],
                    [pattern: '/*/update',              access: ['ROLE_ACCOUNTADMIN']],
                    [pattern: '/*/delete/*',            access: ['ROLE_ACCOUNTADMIN']],
                    [pattern: '/*/edit/*',              access: ['ROLE_ACCOUNTADMIN']],            
                    [pattern: '/*/show/*',              access: ['ROLE_ACCOUNTADMIN', 'ROLE_ACCOUNTUSER']],
                    [pattern: '/api/login',             access: ['ROLE_ANONYMOUS']], // <7>
                    [pattern: '/oauth/access_token',    access: ['ROLE_ANONYMOUS']], // <8>
                    [pattern: '/api/v1',                access: ['ROLE_ACCOUNTADMIN', 'ROLE_ACCOUNTUSER'], httpMethod: 'GET'],  // <9>
                    [pattern: '/api/v1/*',              access: ['ROLE_ACCOUNTADMIN', 'ROLE_ACCOUNTUSER'], httpMethod: 'GET'],
                    [pattern: '/api/v1/*',              access: ['ROLE_ACCOUNTADMIN'], httpMethod: 'DELETE'],
                    [pattern: '/api/v1/*',              access: ['ROLE_ACCOUNTADMIN'], httpMethod: 'POST'],
                    [pattern: '/api/v1/*',              access: ['ROLE_ACCOUNTADMIN'], httpMethod: 'PUT']
            ]
        }
    }
}

