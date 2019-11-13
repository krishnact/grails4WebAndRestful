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
	<title>SB Admin 2 - Forgot password</title>
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
						<s2ui:formContainer type='forgotPassword' focus='username'  >
							<s2ui:form beanName='forgotPasswordCommand' useToken="true">
								<g:if test='${emailSent}'>
									<br/>
									<g:message code='spring.security.ui.forgotPassword.sent'/>
								</g:if>
								<g:else>
									<br/>
									<h4><g:message code='spring.security.ui.forgotPassword.description'/></h4>
									<div class="form-group row">
										<div class="col-sm-6 mb-3 mb-sm-0">
											<input type="text" class="form-control form-control-user" name="username"
												   id="username" placeholder="Username">
										</div>
									</div>
									<input type="submit" value="Submit" id="submit"
										   class="btn btn-primary btn-user btn-block"/>
								</g:else>
							</s2ui:form>
						</s2ui:formContainer>
						<hr/>

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
