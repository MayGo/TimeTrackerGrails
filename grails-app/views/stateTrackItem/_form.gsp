<%@ page import="timetracker.StateTrackItem" %>



<div class="fieldcontain ${hasErrors(bean: stateTrackItemInstance, field: 'beginDate', 'error')} required">
	<label for="beginDate">
		<g:message code="stateTrackItem.beginDate.label" default="Begin Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="beginDate" precision="day"  value="${stateTrackItemInstance?.beginDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: stateTrackItemInstance, field: 'endDate', 'error')} required">
	<label for="endDate">
		<g:message code="stateTrackItem.endDate.label" default="End Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="endDate" precision="day"  value="${stateTrackItemInstance?.endDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: stateTrackItemInstance, field: 'tag', 'error')} required">
	<label for="tag">
		<g:message code="stateTrackItem.tag.label" default="Tag" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="tag" name="tag.id" from="${timetracker.TrackTag.list()}" optionKey="id" required="" value="${stateTrackItemInstance?.tag?.id}" class="many-to-one"/>
</div>

