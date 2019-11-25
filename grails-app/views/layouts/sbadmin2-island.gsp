<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SB Admin 2 - Login</title>

    <!-- Custom fonts for this template
	<link href="/currentTheme/vendor/fontawesome-free/css/all.css" rel="stylesheet" type="text/css">
	-->
    <asset:stylesheet src="/currentTheme/vendor/fontawesome-free/css/all.css"/>
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

    <!-- Custom styles for this template
	<link href="/currentTheme/css/sb-admin-2.min.css" rel="stylesheet">
	-->
    <asset:stylesheet src="/currentTheme/css/sb-admin-2.min.css"/>
    <g:layoutHead/>
</head>

<body class="bg-gradient-primary test-lay">

<script>
    window.fbAsyncInit = function() {
        FB.init({
            appId      : '1007457236270312',
            cookie     : true,
            xfbml      : true,
            version    : '5'
        });

        FB.AppEvents.logPageView();

    };

    (function(d, s, id){
        var js, fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id)) {return;}
        js = d.createElement(s); js.id = id;
        js.src = "https://connect.facebook.net/en_US/sdk.js";
        fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));
</script>
<g:layoutBody/>
<!-- Bootstrap core JavaScript-->
<asset:javascript src="/currentTheme/vendor/jquery/jquery.min.js"></asset:javascript>
<asset:javascript src="/currentTheme/vendor/bootstrap/js/bootstrap.bundle.min.js"></asset:javascript>

<!-- Core plugin JavaScript-->
<asset:javascript src="/currentTheme/vendor/jquery-easing/jquery.easing.min.js"></asset:javascript>

<!-- Custom scripts for all pages-->
<asset:javascript src="/currentTheme/js/sb-admin-2.min.js"></asset:javascript>

</body>

</html>
