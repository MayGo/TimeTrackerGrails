<%@ page import="timetracker.LogTrackItem"%>
<g:hiddenField id="beginDate" name="beginDate" value="${logTrackItemInstance?.beginDate}" />
<g:hiddenField id="endDate" name="endDate" value="${logTrackItemInstance?.endDate}" />
<div class="control-group fieldcontain ${hasErrors(bean: logTrackItemInstance, field: 'tag', 'error')} required">
  <label for="tag" class="control-label"><g:message code="logTrackItem.tag.label" default="Tag" /><span class="required-indicator">*</span></label>
  <div class="controls">
    <g:textField id="tag" name="tagByName" value="${logTrackItemInstance?.tag?.name}" />
    <span class="hide help-inline"><g:message code="logTrackItem.tag.blank.message" default="Tag cannot be blank" /> </span>
  </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: logTrackItemInstance, field: 'desc', 'error')} ">
  <label for="desc" class="control-label"><g:message code="logTrackItem.desc.label" default="Desc" /></label>
  <div class="controls">
    <g:textArea name="desc" value="${logTrackItemInstance?.desc}" />
    <span class=" hide help-inline"> </span>
  </div>
</div>




