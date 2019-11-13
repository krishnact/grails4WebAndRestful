package org.himalay.app

import org.spring.security.Account
import org.spring.security.Constants

class Alert {
    Date    alertTime = Constants.NULL_DATE;
    String  message;
    String  tags = ''
    Account account;

    static constraints = {
        account nullable: true
    }

}
