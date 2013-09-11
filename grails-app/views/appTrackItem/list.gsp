
<%@ page import="timetracker.AppTrackItem" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'appTrackItem.label', default: 'AppTrackItem')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>
	
<section id="list-appTrackItem" class="first">

	<table class="table table-bordered">
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
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
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
		<bs:paginate total="${appTrackItemInstanceTotal}" />
	</div>
</section>

</body>

</html>
