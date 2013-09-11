
<%@ page import="timetracker.AppTrackItem" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'appTrackItem.label', default: 'AppTrackItem')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-appTrackItem" class="first">

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="appTrackItem.beginDate.label" default="Begin Date" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${appTrackItemInstance?.beginDate}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="appTrackItem.dateCreated.label" default="Date Created" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${appTrackItemInstance?.dateCreated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="appTrackItem.endDate.label" default="End Date" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${appTrackItemInstance?.endDate}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="appTrackItem.lastUpdated.label" default="Last Updated" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${appTrackItemInstance?.lastUpdated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="appTrackItem.tag.label" default="Tag" /></td>
				
				<td valign="top" class="value"><g:link controller="trackTag" action="show" id="${appTrackItemInstance?.tag?.id}">${appTrackItemInstance?.tag?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="appTrackItem.title.label" default="Title" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: appTrackItemInstance, field: "title")}</td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
