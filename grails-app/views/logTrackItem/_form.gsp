<%@ page import="timetracker.LogTrackItem" %>



			<div class="control-group fieldcontain ${hasErrors(bean: logTrackItemInstance, field: 'beginDate', 'error')} required">
				<label for="beginDate" class="control-label"><g:message code="logTrackItem.beginDate.label" default="Begin Date" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<bs:datePicker name="beginDate" precision="day"  value="${logTrackItemInstance?.beginDate}"  />
					<span class="help-inline">${hasErrors(bean: logTrackItemInstance, field: 'beginDate', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: logTrackItemInstance, field: 'desc', 'error')} ">
				<label for="desc" class="control-label"><g:message code="logTrackItem.desc.label" default="Desc" /></label>
				<div class="controls">
					<g:textField name="desc" value="${logTrackItemInstance?.desc}"/>
					<span class="help-inline">${hasErrors(bean: logTrackItemInstance, field: 'desc', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: logTrackItemInstance, field: 'endDate', 'error')} required">
				<label for="endDate" class="control-label"><g:message code="logTrackItem.endDate.label" default="End Date" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<bs:datePicker name="endDate" precision="day"  value="${logTrackItemInstance?.endDate}"  />
					<span class="help-inline">${hasErrors(bean: logTrackItemInstance, field: 'endDate', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: logTrackItemInstance, field: 'tag', 'error')} required">
				<label for="tag" class="control-label"><g:message code="logTrackItem.tag.label" default="Tag" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:select id="tag" name="tag.id" from="${timetracker.TrackTag.list()}" optionKey="id" required="" value="${logTrackItemInstance?.tag?.id}" class="many-to-one"/>
					<span class="help-inline">${hasErrors(bean: logTrackItemInstance, field: 'tag', 'error')}</span>
				</div>
			</div>

