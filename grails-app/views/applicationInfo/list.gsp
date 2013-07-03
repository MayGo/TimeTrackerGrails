
<%@ page import="timetracker.ApplicationInfo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'applicationInfo.label', default: 'ApplicationInfo')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-applicationInfo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><a class="timeline" href="${createLink(action: 'timeline')}"><g:message code="default.timeline.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-applicationInfo" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'applicationInfo.dateCreated.label', default: 'Date Created')}" />
					
						<g:sortableColumn property="lastUpdated" title="${message(code: 'applicationInfo.lastUpdated.label', default: 'Last Updated')}" />
					
						<g:sortableColumn property="name" title="${message(code: 'applicationInfo.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="title" title="${message(code: 'applicationInfo.title.label', default: 'Title')}" />
					
						<g:sortableColumn property="updatedCount" title="${message(code: 'applicationInfo.updatedCount.label', default: 'Updated Count')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${applicationInfoInstanceList}" status="i" var="applicationInfoInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${applicationInfoInstance.id}">${fieldValue(bean: applicationInfoInstance, field: "dateCreated")}</g:link></td>
					
						<td><g:formatDate date="${applicationInfoInstance.lastUpdated}" /></td>
					
						<td>${fieldValue(bean: applicationInfoInstance, field: "name")}</td>
					
						<td>${fieldValue(bean: applicationInfoInstance, field: "title")}</td>
					
						<td>${fieldValue(bean: applicationInfoInstance, field: "updatedCount")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${applicationInfoInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
