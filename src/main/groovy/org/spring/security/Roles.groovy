package org.spring.security

import org.spring.security.SecurityRole

import java.lang.reflect.Field
import java.lang.reflect.Modifier

class Roles {
    public static final String ROLE_ROOT         = 'ROLE_ROOT'
    public static final String ROLE_ADMIN        = 'ROLE_ADMIN'
    public static final String ROLE_ACCOUNTADMIN = 'ROLE_ACCOUNTADMIN'
    public static final String ROLE_ACCOUNTUSER  = 'ROLE_ACCOUNTUSER'
    public static final List<String> ALL_ROLES = []

    public static Map<String, SecurityRole> SecurityRoles = [:] as Map<String, SecurityRole>;

    public static void populateAllAuthorities(){
        Roles.declaredFields.findAll{ Field aField->
            aField.name.startsWith('ROLE_') && aField.type == String.class && Modifier.isFinal(aField.getModifiers())
        }.each{Field aField->
            String val = aField.get(Roles.class).toString()
            ALL_ROLES << val;
        }
    }

    public static void populateAllSecurityRoles(){
        ALL_ROLES.each { String authority ->
            if ( !SecurityRole.findByAuthority(authority) ) {
                Roles.SecurityRoles[authority] = new SecurityRole(authority: authority)
                Roles.SecurityRoles[authority].save()
            }
        }
    }
}



