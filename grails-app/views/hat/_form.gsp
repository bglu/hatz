<%@ page import="de.bglu.hatz.Hat" %>



<div class="fieldcontain ${hasErrors(bean: hatInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="hat.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${hatInstance?.name}"/>

</div>

