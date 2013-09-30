
<%@ page import="timetracker.LogTrackItem" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'logTrackItem.label', default: 'LogTrackItem')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-logTrackItem" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-logTrackItem" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list logTrackItem">
			
				<g:if test="${logTrackItemInstance?.beginDate}">
				<li class="fieldcontain">
					<span id="beginDate-label" class="property-label"><g:message code="logTrackItem.beginDate.label" default="Begin Date" /></span>
					
						<span class="property-value" aria-labelledby="beginDate-label"><g:formatDate date="${logTrackItemInstance?.beginDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${logTrackItemInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="logTrackItem.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${logTrackItemInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${logTrackItemInstance?.desc}">
				<li class="fieldcontain">
					<span id="desc-label" class="property-label"><g:message code="logTrackItem.desc.label" default="Desc" /></span>
					
						<span class="property-value" aria-labelledby="desc-label"><g:fieldValue bean="${logTrackItemInstance}" field="desc"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${logTrackItemInstance?.endDate}">
				<li class="fieldcontain">
					<span id="endDate-label" class="property-label"><g:message code="logTrackItem.endDate.label" default="End Date" /></span>
					
						<span class="property-value" aria-labelledby="endDate-label"><g:formatDate date="${logTrackItemInstance?.endDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${logTrackItemInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="logTrackItem.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${logTrackItemInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${logTrackItemInstance?.tag}">
				<li class="fieldcontain">
					<span id="tag-label" class="property-label"><g:message code="logTrackItem.tag.label" default="Tag" /></span>
					
						<span class="property-value" aria-labelledby="tag-label"><g:link controller="trackTag" action="show" id="${logTrackItemInstance?.tag?.id}">${logTrackItemInstance?.tag?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${logTrackItemInstance?.id}" />
					<g:link class="edit" action="edit" id="${logTrackItemInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
