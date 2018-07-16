<%@include file="../init.jsp"%>
 <div>
			<span  id="acceptTaskMessage" style="float:left;"><liferay-ui:message key="confirmation-message"/> </span>
			<span>
				<select id ="<portlet:namespace />noOfDays" name="<portlet:namespace />noOfDays" style="width: 56px; margin-top: 0px;margin-left: 12px;">
					<option value="1" >1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="5">5</option>
					<option value="8">8</option>
					<option value="13">13</option>
					<option value="21">21</option>
					<option value="30">30</option>
				</select>
			</span>
	<div>
		<input type="button" value='<liferay-ui:message key="ok"/>' onclick="javascript:<portlet:namespace />changeTaskStatus();">
		<input type="button" value='<liferay-ui:message key="cancel"/>'  onclick="javascript:<portlet:namespace />closePopUp();">
	</div>
</div> 
