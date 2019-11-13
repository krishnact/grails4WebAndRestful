<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="sbadmin2-main" />
        <g:set var="entityName" value="${message(code: 'userInfo.label', default: 'UserInfo')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <!--<a href="#show-userInfo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a> -->
        <div class="nav" role="navigation">
            <a href="${createLink(uri: '/')}" class="home btn btn-primary btn-icon-split ">
                <span class="icon text-white-50">
                    <i class="fas fa-home"></i>
                </span>
                <span class="text"><g:message code="default.home.label"/></span>
            </a>
            <g:link class="btn btn-primary btn-icon-split" action="index">
                <span class="icon text-white-50">
                    <i class="fas fa-list"></i>
                </span>
                <span class="text"><g:message code="default.list.label" args="[entityName]" /></span>
            </g:link>
            <g:link class="create btn btn-primary btn-icon-split" action="create">
                <span class="icon text-white-50">
                    <i class="fas fa-create"></i>
                </span>
                <span class="text"><g:message code="default.new.label" args="[entityName]" /></span>
            </g:link>
            <!--<ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
            -->
        </div>
        <div id="show-userInfo" class="content scaffold-show" role="main">
            <!-- Page Heading -->
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <-- Flash message -->
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>

            <f:display bean="userInfo" except="user" />

            <g:form resource="${this.userInfo}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${this.userInfo}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <!--<input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /> -->
                </fieldset>

            </g:form>
        </div>
    </body>
</html>
