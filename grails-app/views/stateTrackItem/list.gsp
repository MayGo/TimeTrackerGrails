
<%@ page import="timetracker.StateTrackItem" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'stateTrackItem.label', default: 'StateTrackItem')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-stateTrackItem" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-stateTrackItem" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="beginDate" title="${message(code: 'stateTrackItem.beginDate.label', default: 'Begin Date')}" />
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'stateTrackItem.dateCreated.label', default: 'Date Created')}" />
					
						<g:sortableColumn property="endDate" title="${message(code: 'stateTrackItem.endDate.label', default: 'End Date')}" />
					
						<g:sortableColumn property="lastUpdated" title="${message(code: 'stateTrackItem.lastUpdated.label', default: 'Last Updated')}" />
					
						<th><g:message code="stateTrackItem.tag.label" default="Tag" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${stateTrackItemInstanceList}" status="i" var="stateTrackItemInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${stateTrackItemInstance.id}">${fieldValue(bean: stateTrackItemInstance, field: "beginDate")}</g:link></td>
					
						<td><g:formatDate date="${stateTrackItemInstance.dateCreated}" /></td>
					
						<td><g:formatDate date="${stateTrackItemInstance.endDate}" /></td>
					
						<td><g:formatDate date="${stateTrackItemInstance.lastUpdated}" /></td>
					
						<td>${fieldValue(bean: stateTrackItemInstance, field: "tag")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${stateTrackItemInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
