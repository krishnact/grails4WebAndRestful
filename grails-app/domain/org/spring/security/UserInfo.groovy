package org.spring.security

class UserInfo {

    String firstName
    String lastName
    String email
    String phoneNumber
    /**
     * A String to store any other details in JSON format
     */
    String jsonInfo



    static belongsTo = [user: User]
    static constraints = {
        jsonInfo     nullable: true
        phoneNumber  nullable: true
        email        nullable: true
    }

}
