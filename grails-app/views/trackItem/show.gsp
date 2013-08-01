
<%@ page import="timetracker.TrackItem" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'trackItem.label', default: 'TrackItem')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-trackItem" class="first">

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="trackItem.beginDate.label" default="Begin Date" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${trackItemInstance?.beginDate}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="trackItem.dateCreated.label" default="Date Created" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${trackItemInstance?.dateCreated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="trackItem.endDate.label" default="End Date" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${trackItemInstance?.endDate}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="trackItem.lastUpdated.label" default="Last Updated" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${trackItemInstance?.lastUpdated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="trackItem.tag.label" default="Tag" /></td>
				
				<td valign="top" class="value"><g:link controller="trackTag" action="show" id="${trackItemInstance?.tag?.id}">${trackItemInstance?.tag?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
