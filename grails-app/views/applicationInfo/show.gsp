
<%@ page import="timetracker.ApplicationInfo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'applicationInfo.label', default: 'ApplicationInfo')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-applicationInfo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-applicationInfo" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list applicationInfo">
			
				<g:if test="${applicationInfoInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="applicationInfo.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${applicationInfoInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${applicationInfoInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="applicationInfo.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${applicationInfoInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${applicationInfoInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="applicationInfo.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${applicationInfoInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${applicationInfoInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="applicationInfo.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${applicationInfoInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${applicationInfoInstance?.updatedCount}">
				<li class="fieldcontain">
					<span id="updatedCount-label" class="property-label"><g:message code="applicationInfo.updatedCount.label" default="Updated Count" /></span>
					
						<span class="property-value" aria-labelledby="updatedCount-label"><g:fieldValue bean="${applicationInfoInstance}" field="updatedCount"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${applicationInfoInstance?.id}" />
					<g:link class="edit" action="edit" id="${applicationInfoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
