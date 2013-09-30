
<%@ page import="timetracker.LogTrackItem" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'logTrackItem.label', default: 'LogTrackItem')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-logTrackItem" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-logTrackItem" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="beginDate" title="${message(code: 'logTrackItem.beginDate.label', default: 'Begin Date')}" />
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'logTrackItem.dateCreated.label', default: 'Date Created')}" />
					
						<g:sortableColumn property="desc" title="${message(code: 'logTrackItem.desc.label', default: 'Desc')}" />
					
						<g:sortableColumn property="endDate" title="${message(code: 'logTrackItem.endDate.label', default: 'End Date')}" />
					
						<g:sortableColumn property="lastUpdated" title="${message(code: 'logTrackItem.lastUpdated.label', default: 'Last Updated')}" />
					
						<th><g:message code="logTrackItem.tag.label" default="Tag" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${logTrackItemInstanceList}" status="i" var="logTrackItemInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${logTrackItemInstance.id}">${fieldValue(bean: logTrackItemInstance, field: "beginDate")}</g:link></td>
					
						<td><g:formatDate date="${logTrackItemInstance.dateCreated}" /></td>
					
						<td>${fieldValue(bean: logTrackItemInstance, field: "desc")}</td>
					
						<td><g:formatDate date="${logTrackItemInstance.endDate}" /></td>
					
						<td><g:formatDate date="${logTrackItemInstance.lastUpdated}" /></td>
					
						<td>${fieldValue(bean: logTrackItemInstance, field: "tag")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${logTrackItemInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
