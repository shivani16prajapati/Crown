<%@include file="../init.jsp"%>
<div class="clearfix">
	<div id="loadingDiv" style="display: none;width: 100%;text-align: center;margin-top: 120px;min-height:250px;">
  			<img  src="<%=request.getContextPath()%>/images/loading.GIF" width="50px" height="50px"/>
	</div>
	<div class="table-responsive np-tablefix01" id="container">
    	<table border="1" class="table" bordercolor="#e3e3e3">
       		<thead>
          		<tr>
            		<th style="text-align: center;"><liferay-ui:message key="idea"/></th>
            		<th style="text-align: center;"><liferay-ui:message key="inventer"/></th>
            		<th style="text-align: center;" ><liferay-ui:message key="start-date"/></th>
            		<th style="text-align: center;"><liferay-ui:message key="end-date"/></th>
            		<th style="text-align: center;"><liferay-ui:message key="percentage-complete"/></th>
            		<th style="text-align: center;"><liferay-ui:message key="idea-type"/></th>
            		<th style="text-align: center;">Hotness</th>
            		<!-- <th align="center">Launch icon</th> -->
         		</tr>
        	</thead>
        	<tbody id="ideaScrumTable"></tbody>
		 </table>
   </div>
  </div>    
  <%@ include file="view_js.jsp"%>
  