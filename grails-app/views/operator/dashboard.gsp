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


        <g:each var="aPanel" in="${panels}">
            <f:field property="panel" bean="${aPanel}"/>
        </g:each>

</body>
</html>
