
<%@ page import="timetracker.AppTrackItem" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'appTrackItem.label', default: 'AppTrackItem')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-appTrackItem" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-appTrackItem" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="beginDate" title="${message(code: 'appTrackItem.beginDate.label', default: 'Begin Date')}" />
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'appTrackItem.dateCreated.label', default: 'Date Created')}" />
					
						<g:sortableColumn property="endDate" title="${message(code: 'appTrackItem.endDate.label', default: 'End Date')}" />
					
						<g:sortableColumn property="lastUpdated" title="${message(code: 'appTrackItem.lastUpdated.label', default: 'Last Updated')}" />
					
						<th><g:message code="appTrackItem.tag.label" default="Tag" /></th>
					
						<g:sortableColumn property="title" title="${message(code: 'appTrackItem.title.label', default: 'Title')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${appTrackItemInstanceList}" status="i" var="appTrackItemInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${appTrackItemInstance.id}">${fieldValue(bean: appTrackItemInstance, field: "beginDate")}</g:link></td>
					
						<td><g:formatDate date="${appTrackItemInstance.dateCreated}" /></td>
					
						<td><g:formatDate date="${appTrackItemInstance.endDate}" /></td>
					
						<td><g:formatDate date="${appTrackItemInstance.lastUpdated}" /></td>
					
						<td>${fieldValue(bean: appTrackItemInstance, field: "tag")}</td>
					
						<td>${fieldValue(bean: appTrackItemInstance, field: "title")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${appTrackItemInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
