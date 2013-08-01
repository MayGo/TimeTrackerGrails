<%@ page import="timetracker.TrackItem" %>



			<div class="control-group fieldcontain ${hasErrors(bean: trackItemInstance, field: 'beginDate', 'error')} required">
				<label for="beginDate" class="control-label"><g:message code="trackItem.beginDate.label" default="Begin Date" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<bs:datePicker name="beginDate" precision="day"  value="${trackItemInstance?.beginDate}"  />
					<span class="help-inline">${hasErrors(bean: trackItemInstance, field: 'beginDate', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: trackItemInstance, field: 'endDate', 'error')} required">
				<label for="endDate" class="control-label"><g:message code="trackItem.endDate.label" default="End Date" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<bs:datePicker name="endDate" precision="day"  value="${trackItemInstance?.endDate}"  />
					<span class="help-inline">${hasErrors(bean: trackItemInstance, field: 'endDate', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: trackItemInstance, field: 'tag', 'error')} required">
				<label for="tag" class="control-label"><g:message code="trackItem.tag.label" default="Tag" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:select id="tag" name="tag.id" from="${timetracker.TrackTag.list()}" optionKey="id" required="" value="${trackItemInstance?.tag?.id}" class="many-to-one"/>
					<span class="help-inline">${hasErrors(bean: trackItemInstance, field: 'tag', 'error')}</span>
				</div>
			</div>

