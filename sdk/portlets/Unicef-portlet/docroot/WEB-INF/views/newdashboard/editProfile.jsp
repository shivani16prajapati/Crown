<%@page pageEncoding="UTF-8"%>
<%@include file="../init.jsp"%>


<div class="col-md-12">
    <section class="widget widget-chart-stats-simple">
      <div class="widget-body Np-table-responsive">
        <table class="table mi-tablefix02">
  		<tbody> 
        <tr>
		      <td class="mi-table-text01">Name:</td>
		      <td class="mi-table-text02"><input type="text" disabled="disabled" value="${user.getFullName()}" name="name" id="name"/></td>
			    </tr>
			    <tr>
			      <td class="mi-table-text01">Current Role:</td>
			      <td class="mi-table-text02"><input type="text" value="${userData.getCurrent_role()}"  name="current_role" id="current_role"/></td>
			    </tr>
			    <%-- <tr>
			      <td class="mi-table-text01">LOB:</td>
			      <td class="mi-table-text02"><input type="text" value="${userData.getLob()}"  name="lob" id="lob" /></td>
			    </tr> --%>
			    <tr>
			      <td class="mi-table-text01">Location:</td>
			      <td class="mi-table-text02">
			      
			         <select name="userCountry" id="userCountry">
			           <option value="">select location</option>
			           <option value="USA" <c:if test="${userData.getUserCountry() eq 'USA'}">selected="selected"</c:if> >North America</option>
			           <option value="UKR" <c:if test="${userData.getUserCountry() eq 'UKR'}">selected="selected"</c:if> >Europe</option>
			           <option value="GBR" <c:if test="${userData.getUserCountry() eq 'GBR'}">selected="selected"</c:if> >United Kingdom</option>
			           <option value="ARG" <c:if test="${userData.getUserCountry() eq 'ARG'}">selected="selected"</c:if> >Argentina</option>
			           <option value="CHN" <c:if test="${userData.getUserCountry() eq 'CHN'}">selected="selected"</c:if> >China</option>
			           <option value="DEU" <c:if test="${userData.getUserCountry() eq 'DEU'}">selected="selected"</c:if> >Germany</option>       
			         </select>
			      </td>
		        </tr>
			    <%-- <tr>
			      <td class="mi-table-text01">Department:</td>
			      <td class="mi-table-text02"><input type="text" value="${userData.getDepartment()}"  name="department" id="department" /></td>
			    </tr> --%>
			    <tr>
				    <td class="mi-table-text01">Department:</td>
				    <td class="mi-table-text02">
				      <select name="department" id="department">
				        <option value="">select department</option>
				        <option value="Engineering" <c:if test="${userData.getDepartment() eq 'Engineering'}">selected="selected"</c:if> >Engineering</option>
				        <option value="Sales" <c:if test="${userData.getDepartment() eq 'Sales'}">selected="selected"</c:if> >Sales</option>
				        <option value="Administration" <c:if test="${userData.getDepartment() eq 'Administration'}">selected="selected"</c:if> >Administration</option>
				        <option value="Supply Chain" <c:if test="${userData.getDepartment() eq 'Supply Chain'}">selected="selected"</c:if> >Supply Chain</option>
				        <option value="Aftermarket" <c:if test="${userData.getDepartment() eq 'Aftermarket'}">selected="selected"</c:if> >Aftermarket</option>
				      </select>
				    </td>
			    </tr>
			    <tr>
			      <td class="mi-table-text01">Years with Crown:</td>
			      <td class="mi-table-text02"><input type="text" value="${years}" name="year_crown" id="year_crown" disabled/></td>
			    </tr>
			   <%--  <tr>
			      <td class="mi-table-text01">Works for:</td>
			      <td class="mi-table-text02"><input type="text" value="${userData.getWorks_for()}"  name="works" id="works"/></td>
			    </tr> --%>
			    <tr>
			      <td class="mi-table-text01">Email:</td>
			      <td class="mi-table-text02"><input type="text" disabled="disabled" value="${user.getEmailAddress()}"  name="contact" id="contact" /></td>
			    </tr>
			    <tr>
			      <td class="mi-table-text01">Expertise:</td>
			      <td class="mi-table-text02"><input type="text" value="${userData.getExpertise()}"  name="expertise" id="expertise" /></td>
			    </tr>
			    <tr>
			      <td class="mi-table-text01">Experience:</td>
			      <td class="mi-table-text02"><input type="text" value="${userData.getExperience()}"   name="experience" id="experience" /></td>
			    </tr>
			    <tr>
			      <td class="mi-table-text01">Skillset:</td>
			      <td class="mi-table-text02"><input type="text" value="${userData.getSkill_set()}"  name="skillset" id="skillset"/></td> 
			    </tr>
			    <tr>
			      <td class="mi-table-text01">Patents:</td>
			      <td class="mi-table-text02"><input type="text" value="${userData.getPatents()}"  name="patents" id="patents"/></td>
			    </tr>
			    <tr>
			      <td class="mi-table-text01">What I do for fun:</td>
			      <td class="mi-table-text02"><input type="text" value="${userData.getFun()}" name="fun" id="fun"/></td>
		        </tr>
		        <tr>
		          <td class="mi-table-text01"><input type="checkbox" name="landingCheckbox"  id="landingCheckbox" <c:if test="${landPage}">checked="checked"</c:if> ></td>
		          <td class="mi-table-text02" style="color: #e4ab32 !important;"> Make this my landing page</td>
		        </tr>
		        
		        
		        <%-- <tr>
			      <td class="mi-table-text01">Country:</td>
			      <td class="mi-table-text02">
			      
			         <select name="userCountry" id="userCountry">
			           <option value="">select country</option>
			           <option value="USA" <c:if test="${userData.getUserCountry() eq 'USA'}">selected="selected"</c:if> >USA</option>
			           <option value="GBR" <c:if test="${userData.getUserCountry() eq 'GBR'}">selected="selected"</c:if> >United Kingdom</option>
			           <option value="ARG" <c:if test="${userData.getUserCountry() eq 'ARG'}">selected="selected"</c:if> >Argentina</option>
			           <option value="BRA" <c:if test="${userData.getUserCountry() eq 'BRA'}">selected="selected"</c:if> >BRAZIL</option>
			           <option value="CHN" <c:if test="${userData.getUserCountry() eq 'CHN'}">selected="selected"</c:if> >CHINA</option>
			           <option value="DEU" <c:if test="${userData.getUserCountry() eq 'DEU'}">selected="selected"</c:if> >GERMANY</option>       
			         </select>
			      </td>
		        </tr> --%>

  		</tbody>

		</table>
		<div style="text-align: center;">
		 <input type="button"  value="Save" class="btn btn-success" id="saveButton" onclick="updateUserDetail()"/></span>
		 
		 <input type="button"  value="Cancel" id="" class="btn btn-danger" onclick="$.fancybox.close('#profileUserDetails');"/></span>
		</div>
      </div>
    </section>
  </div>
  
