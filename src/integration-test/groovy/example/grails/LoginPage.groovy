//package example.grails
//
//import geb.Page
//
//class LoginPage extends Page {
//    static url = '/login/auth'
//
//    static at = {
//        title == 'Login'
//    }
//
//    static content = {
//        loginButton { $('.ui-button-text', 0) }
//        usernameInputField { $('#username', 0) }
//        passwordInputField { $('#password', 0) }
//    }
//
//    void login(String username, String password) {
//        usernameInputField << username
//        passwordInputField << password
//        loginButton.click()
//    }
//}
