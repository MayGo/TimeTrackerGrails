<%@ page import="timetracker.AppTrackItem" %>



			<div class="control-group fieldcontain ${hasErrors(bean: appTrackItemInstance, field: 'beginDate', 'error')} required">
				<label for="beginDate" class="control-label"><g:message code="appTrackItem.beginDate.label" default="Begin Date" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<bs:datePicker name="beginDate" precision="day"  value="${appTrackItemInstance?.beginDate}"  />
					<span class="help-inline">${hasErrors(bean: appTrackItemInstance, field: 'beginDate', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: appTrackItemInstance, field: 'endDate', 'error')} required">
				<label for="endDate" class="control-label"><g:message code="appTrackItem.endDate.label" default="End Date" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<bs:datePicker name="endDate" precision="day"  value="${appTrackItemInstance?.endDate}"  />
					<span class="help-inline">${hasErrors(bean: appTrackItemInstance, field: 'endDate', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: appTrackItemInstance, field: 'tag', 'error')} required">
				<label for="tag" class="control-label"><g:message code="appTrackItem.tag.label" default="Tag" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:select id="tag" name="tag.id" from="${timetracker.TrackTag.list()}" optionKey="id" required="" value="${appTrackItemInstance?.tag?.id}" class="many-to-one"/>
					<span class="help-inline">${hasErrors(bean: appTrackItemInstance, field: 'tag', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: appTrackItemInstance, field: 'title', 'error')} ">
				<label for="title" class="control-label"><g:message code="appTrackItem.title.label" default="Title" /></label>
				<div class="controls">
					<g:textField name="title" value="${appTrackItemInstance?.title}"/>
					<span class="help-inline">${hasErrors(bean: appTrackItemInstance, field: 'title', 'error')}</span>
				</div>
			</div>

