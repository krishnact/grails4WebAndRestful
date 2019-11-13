<%--
  Created by IntelliJ IDEA.
  User: krishna
  Date: 11/9/2019
  Time: 6:09 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta name="layout" content="sbadmin2-island">
    <title>SB Admin 2 - Register</title>
</head>

<body class="bg-gradient-primary">

<div class="container">

    <div class="card o-hidden border-0 shadow-lg my-5">
        <div class="card-body p-0">
            <!-- Nested Row within Card Body -->
            <div class="row">
                <div class="col-lg-5 d-none d-lg-block bg-register-image"></div>

                <div class="col-lg-7">
                    <div class="p-5">

                        <s2ui:formContainer type='register' focus='username' >
                            <s2ui:form beanName='registerCommand'>
                                <div class="form-group row">
                                    <div class="col-sm-6 mb-3 mb-sm-0">
                                        <input type="text" class="form-control form-control-user" name="username"
                                               id="username" placeholder="Username">
                                    </div>
                                    <!--
                                <div class="col-sm-6">
                                    <input type="text" class="form-control form-control-user" id="exampleLastName" placeholder="Last Name">
                                </div>
                                -->
                                </div>
                                <!--
                                <div class="form-group">
                                    <input type="email" class="form-control form-control-user" id="exampleInputEmail" placeholder="Email Address">
                                </div>
                                -->
                                <div class="form-group row">
                                    <div class="col-sm-6 mb-3 mb-sm-0">
                                        <input type="password" class="form-control form-control-user" name="password"
                                               id="password" placeholder="Password">
                                    </div>

                                    <div class="col-sm-6">
                                        <input type="password" class="form-control form-control-user" name="password2"
                                               id="password2" placeholder="Repeat Password">
                                    </div>
                                </div>

                                <input type="submit" value="Register Account" id="loginButton_submit"
                                       class="btn btn-primary btn-user btn-block"/>
                                <hr>
                                <!--
                                <a href="index.html" class="btn btn-google btn-user btn-block">
                                    <i class="fab fa-google fa-fw"></i> Register with Google
                                </a>
                                <a href="index.html" class="btn btn-facebook btn-user btn-block">
                                    <i class="fab fa-facebook-f fa-fw"></i> Register with Facebook
                                </a>
                                -->
                            </s2ui:form>
                        </s2ui:formContainer>
                        <hr>

                        <div class="text-center">
                            <g:link controller='register' action='forgotPassword'><g:message code='spring.security.ui.login.forgotPassword'/></g:link>
                        </div>

                        <div class="text-center">
                            <g:link controller='login' action='auth'><g:message code='spring.security.ui.login.login'/></g:link>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<!-- Bootstrap core JavaScript-->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="js/sb-admin-2.min.js"></script>

</body>

</html>
