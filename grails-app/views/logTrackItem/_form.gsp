<%@ page import="timetracker.LogTrackItem" %>



<div class="fieldcontain ${hasErrors(bean: logTrackItemInstance, field: 'beginDate', 'error')} required">
	<label for="beginDate">
		<g:message code="logTrackItem.beginDate.label" default="Begin Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="beginDate" precision="day"  value="${logTrackItemInstance?.beginDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: logTrackItemInstance, field: 'desc', 'error')} ">
	<label for="desc">
		<g:message code="logTrackItem.desc.label" default="Desc" />
		
	</label>
	<g:textField name="desc" value="${logTrackItemInstance?.desc}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: logTrackItemInstance, field: 'endDate', 'error')} required">
	<label for="endDate">
		<g:message code="logTrackItem.endDate.label" default="End Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="endDate" precision="day"  value="${logTrackItemInstance?.endDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: logTrackItemInstance, field: 'tag', 'error')} required">
	<label for="tag">
		<g:message code="logTrackItem.tag.label" default="Tag" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="tag" name="tag.id" from="${timetracker.TrackTag.list()}" optionKey="id" required="" value="${logTrackItemInstance?.tag?.id}" class="many-to-one"/>
</div>

