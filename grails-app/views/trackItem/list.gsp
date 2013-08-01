
<%@ page import="timetracker.TrackItem" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'trackItem.label', default: 'TrackItem')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>
	
<section id="list-trackItem" class="first">

	<table class="table table-bordered">
		<thead>
			<tr>
			
				<g:sortableColumn property="beginDate" title="${message(code: 'trackItem.beginDate.label', default: 'Begin Date')}" />
			
				<g:sortableColumn property="dateCreated" title="${message(code: 'trackItem.dateCreated.label', default: 'Date Created')}" />
			
				<g:sortableColumn property="endDate" title="${message(code: 'trackItem.endDate.label', default: 'End Date')}" />
			
				<g:sortableColumn property="lastUpdated" title="${message(code: 'trackItem.lastUpdated.label', default: 'Last Updated')}" />
			
				<th><g:message code="trackItem.tag.label" default="Tag" /></th>
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${trackItemInstanceList}" status="i" var="trackItemInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${trackItemInstance.id}">${fieldValue(bean: trackItemInstance, field: "beginDate")}</g:link></td>
			
				<td><g:formatDate date="${trackItemInstance.dateCreated}" /></td>
			
				<td><g:formatDate date="${trackItemInstance.endDate}" /></td>
			
				<td><g:formatDate date="${trackItemInstance.lastUpdated}" /></td>
			
				<td>${fieldValue(bean: trackItemInstance, field: "tag")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bs:paginate total="${trackItemInstanceTotal}" />
	</div>
</section>

</body>

</html>
