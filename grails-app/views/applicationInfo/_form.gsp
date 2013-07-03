<%@ page import="timetracker.ApplicationInfo" %>



<div class="fieldcontain ${hasErrors(bean: applicationInfoInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="applicationInfo.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${applicationInfoInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: applicationInfoInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="applicationInfo.title.label" default="Title" />
		
	</label>
	<g:textField name="title" value="${applicationInfoInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: applicationInfoInstance, field: 'updatedCount', 'error')} required">
	<label for="updatedCount">
		<g:message code="applicationInfo.updatedCount.label" default="Updated Count" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="updatedCount" type="number" value="${applicationInfoInstance.updatedCount}" required=""/>
</div>

