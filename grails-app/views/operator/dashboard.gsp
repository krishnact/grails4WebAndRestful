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

        <g:each var="aPanel" in="${panels}">
            <div class="col-xl-6 col-lg-7">
            aPanel is of type ${aPanel.class} and contains ${aPanel.value.class}
            <f:field property="value" bean="${aPanel}" templates="plot"/>
            </div>
        </g:each>
</div>
</div>
</body>
</html>
