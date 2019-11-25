<%@ page import="org.himalay.grafana.Plot" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="sbadmin2-main" />

        <title>Dashboard</title>
    </head>
<%
%>
    <body>
        Panel count ${panels.size()}
<div class="container-fluid">
<div class="row">

        <!--g:each var="aPanel" in="${panels}"-->
            <div class="col-xl-6 col-lg-7">
            <f:field property="value" bean="${panels['Doughnut.json']}" templates="plot"/>
            </div>
        <!--/g:each  -->
</div>
</div>
</body>
</html>
