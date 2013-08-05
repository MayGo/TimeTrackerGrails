
<%@ page import="timetracker.LogTrackItem" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'logTrackItem.label', default: 'LogTrackItem')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>
	
<section id="list-logTrackItem" class="first">

	<table class="table table-bordered">
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
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
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
		<bs:paginate total="${logTrackItemInstanceTotal}" />
	</div>
</section>

</body>

</html>
