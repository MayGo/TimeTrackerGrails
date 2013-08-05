
<%@ page import="timetracker.LogTrackItem" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'logTrackItem.label', default: 'LogTrackItem')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-logTrackItem" class="first">

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="logTrackItem.beginDate.label" default="Begin Date" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${logTrackItemInstance?.beginDate}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="logTrackItem.dateCreated.label" default="Date Created" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${logTrackItemInstance?.dateCreated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="logTrackItem.desc.label" default="Desc" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: logTrackItemInstance, field: "desc")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="logTrackItem.endDate.label" default="End Date" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${logTrackItemInstance?.endDate}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="logTrackItem.lastUpdated.label" default="Last Updated" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${logTrackItemInstance?.lastUpdated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="logTrackItem.tag.label" default="Tag" /></td>
				
				<td valign="top" class="value"><g:link controller="trackTag" action="show" id="${logTrackItemInstance?.tag?.id}">${logTrackItemInstance?.tag?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
