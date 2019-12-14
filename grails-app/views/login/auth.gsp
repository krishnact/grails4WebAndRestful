<g:set var='securityConfig' value='${applicationContext.springSecurityService.securityConfig}'/>
<!DOCTYPE html>
<html lang="en">

<head>
	<meta name="layout" content="adminLTE-island">
	<title>SB Admin 2 - Login</title>
</head>

<body>

<div class="container">

	<!-- Outer Row -->
	<div class="row justify-content-center">

		<div class="col-xl-10 col-lg-12 col-md-9">

			<div class="card o-hidden border-0 shadow-lg my-5">
				<div class="card-body p-0">
					<!-- Nested Row within Card Body -->
					<div class="row">
						<div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
						<div class="col-lg-6">
							<div class="p-5">
								<div class="text-center">
									<h1 class="h4 text-gray-900 mb-4">Welcome Back!</h1>
								</div>
								<s2ui:form type='login' focus='username' class="user">
									<div class="form-group">
										<input type="text" class="form-control form-control-user" name="${securityConfig.apf.usernameParameter}" id="username" aria-describedby="emailHelp" placeholder="Enter username..." value="acctadmin">
									</div>
									<div class="form-group">
										<input type="password" class="form-control form-control-user" name="${securityConfig.apf.passwordParameter}" id="password" placeholder="Password" value="app123">
									</div>
									<div class="form-group">
										<div class="custom-control custom-checkbox small">
											<input type="checkbox" class="custom-control-input" id="customCheck">
											<label class="custom-control-label" for="customCheck"><g:message code='spring.security.ui.login.rememberme'/></label>
										</div>
									</div>

									<input type="submit" value="Login" id="loginButton_submit" class="btn btn-primary btn-user btn-block">
									<!--
									<hr>
									<a href="index.html" class="btn btn-google btn-user btn-block">
										<i class="fab fa-google fa-fw"></i> Login with Google
									</a>


									<a href="https://www.facebook.com/v5.0/dialog/oauth?client_id=1007457236270312&redirect_uri=http://localhost:8080/oauth2/facebook/callback&state=${Math.random()*10000}" class="btn btn-facebook btn-user btn-block">
										<i class="fab fa-facebook-f fa-fw"></i> Login with Facebook
									</a>
									-->

								</s2ui:form>
								<hr>
								<div class="text-center">
									<s2ui:linkButton elementId='register' controller='register' messageCode='spring.security.ui.login.register'/>
								</div>
								<div class="text-center">
									<g:link controller='register' action='forgotPassword'><g:message code='spring.security.ui.login.forgotPassword'/></g:link>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>

	</div>

</div>


</body>

</html>
