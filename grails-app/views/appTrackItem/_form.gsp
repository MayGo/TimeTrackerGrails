<%@ page import="timetracker.AppTrackItem" %>



<div class="fieldcontain ${hasErrors(bean: appTrackItemInstance, field: 'beginDate', 'error')} required">
	<label for="beginDate">
		<g:message code="appTrackItem.beginDate.label" default="Begin Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="beginDate" precision="day"  value="${appTrackItemInstance?.beginDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: appTrackItemInstance, field: 'endDate', 'error')} required">
	<label for="endDate">
		<g:message code="appTrackItem.endDate.label" default="End Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="endDate" precision="day"  value="${appTrackItemInstance?.endDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: appTrackItemInstance, field: 'tag', 'error')} required">
	<label for="tag">
		<g:message code="appTrackItem.tag.label" default="Tag" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="tag" name="tag.id" from="${timetracker.TrackTag.list()}" optionKey="id" required="" value="${appTrackItemInstance?.tag?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: appTrackItemInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="appTrackItem.title.label" default="Title" />
		
	</label>
	<g:textField name="title" value="${appTrackItemInstance?.title}"/>
</div>

