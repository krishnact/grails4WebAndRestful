<%@ page import="org.himalay.grafana.Plot" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="adminLTE-main"/>
    <!--meta name="layout" content="bootswatch-main"/-->
    <!--meta name="layout" content="adminPro-main"/-->
    <!--meta name="layout" content="sbadmin2-main"/-->
    <title>Dashboard</title>
</head>
<%
%>
<body>
Panel count ${panels.size()}
<div class="container-fluid">
    <div class="row">
        <div class="col-xl-6 col-lg-7">
            <f:field property="value" bean="${panels['MeanCPU.json']}" templates="plot"/>
        </div>
        <div class="col-xl-6 col-lg-7">
            <f:field property="value" bean="${panels['CpuUsage-Doughnut.json']}" templates="plot"/>
        </div>

        <!--/g:each  -->
    </div>


</div>
</body>
</html>
