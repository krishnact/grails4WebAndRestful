<!DOCTYPE html>
<html lang="en">
<%
    //    String themeVariation = "vertical-dark";
//    String bodyClass = "darklayout";
    String themeVariation = "adminLTE";
    String bodyClass = "materialdesign";

    session.setAttribute('themeVariation',themeVariation)
    session.setAttribute('bodyClass',bodyClass)
%>
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

    <asset:stylesheet src="/${themeVariation}/dist/css/adminlte.min.css"/>
    <g:layoutHead/>
    <style>
    ._bg-login-mage {
        /*background: url('https://source.unsplash.com/fJb9CeTGjvQ/600x800');*/
        background: repeating-linear-gradient(45deg, rgb(200,220,240), transparent 400px);
        background-position: center;
        background-size: cover;
    }

    ._bg-register-image {
        background: url('https://source.unsplash.com/of_4PDMQ3wk/600x800');
        background-position: center;
        background-size: cover;
    }

    ._bg-password-image {
        background: url('https://source.unsplash.com/OokBLPrkCNk/600x800');
        background-position: center;
        background-size: cover;
    }
    .bg-login-image {
        background: url('https://source.unsplash.com/K4mSJ7kc0As/600x800');
        background-position: center;
        background-size: cover;
    }

    .bg-register-image {
        background: url('https://source.unsplash.com/Mv9hjnEUHR4/600x800');
        background-position: center;
        background-size: cover;
    }

    .bg-password-image {
        background: url('https://source.unsplash.com/oWTW-jNGl9I/600x800');
        background-position: center;
        background-size: cover;
    }
    .login-window{
        background: rgb(240,240,240);
        background: -moz-linear-gradient(left, rgba(240,240,240,0.0) 0%, rgba(200,200,200,0.9) 50%, rgba(240,240,240,0.0) 100%);
        background: -webkit-linear-gradient(left, rgba(240,240,240,0.0) 0%, rgba(200,200,200,0.9) 50%, rgba(240,240,240,0.0) 100%);
        background: linear-gradient(to right, rgba(240,240,240,0.0) 0%, rgba(200,200,200,0.9) 50%, rgba(240,240,240,0.0) 100%);
    }
    </style>
</head>

<body class="test-lay"  style="background-image: url('https://source.unsplash.com/PJD6DRrqz_w');background-size: cover;">

<script>
    /**
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
    */
</script>
<g:layoutBody/>
<!-- Bootstrap 4 -->
<asset:javascript src="/${themeVariation}/plugins/bootstrap/js/bootstrap.bundle.min.js"></asset:javascript>

<!-- AdminLTE App -->
<asset:javascript src="/${themeVariation}/dist/js/adminlte.js"></asset:javascript>

</body>

</html>
