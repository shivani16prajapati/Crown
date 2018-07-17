<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.unicef.domain.IdeaRate"%>
<%@page import="com.unicef.service.IdeaRateService"%>
<%@include file="../../init.jsp"%>
<portlet:renderURL var="ideaRateChartURL" windowState="<%= LiferayWindowState.MAXIMIZED.toString() %>">
	<portlet:param name="view" value="ideaRateChart"/>
	<portlet:param name="ideaId" value="${ideaId}"/>
	<portlet:param name="tab" value="rating-this-idea"/>
</portlet:renderURL>

<div class="np-rounded-box clearfix">
	<div class="cg-box01 clearfix">
    	<div class="cg-icon-box"><img src="<%=request.getContextPath() %>/images/info-icon.png" width="28" height="28" /></div>
    	<div class="cg-text-box01">You can rate the idea in this area in the following areas. Mouse over the criteria for more info. And don't worry if you can't provide a rating in all the fields, just enter the feedback you feel confident about. You can change your ratings later.</div>
	</div>
	<div class="clearfix">
	<div class="idearate-box01">
	<div class="clearfix cg-table-01">
		<table width="100%" border="1" cellspacing="0" cellpadding="4" bordercolor="#e0e0e0">
  			<tr>
    			<th scope="col" style="text-align:center;">Criteria</th>
    			<th scope="col" style="text-align:center;">Rating</th>
  			</tr>
  			
  			<c:forEach var="ideaRate" items="${ideaRateCriterias}">
  				<tr>
    				<td style="vertical-align:middle; !important">
    					<div title="${ideaRate.description}" class="cg-idea-rate-criteria-tooltip">${ideaRate.ideaRateCriteriaName}</div>
    				</td>
			    	<td style="vertical-align:middle; !important">
			    		<div class="cg-linkbox01" id='<portlet:namespace/>ideaRating_${ideaRate.ideaRateCriteriaId}'>
							<c:set value="${ideaRate.ideaRateCriteriaId}" var="ideaRateCriteriaId" />
							<%
								long ideaId = (Long)request.getAttribute("ideaId");
								long ideaRateCriteriaId = (Long)pageContext.getAttribute("ideaRateCriteriaId");
								IdeaRateService ideaRateService = (IdeaRateService)request.getAttribute("ideaRateService");
								IdeaRate ideaRate =	ideaRateService.findByUserIdIdeaIdAndCriteriaId(themeDisplay.getUserId(), ideaId, ideaRateCriteriaId);
								pageContext.setAttribute("ideaRateT", ideaRate);
							%>			    		
			    			<c:choose>
			    				<c:when test="${(not empty ideaRateT) && (ideaRateT.rateValue == 1) }">
			    					<a title="Negligible" class="cg-idea-rate-criteria-tooltip active" href="javascript:void(0);" id='<portlet:namespace/>ideaRate1' onclick="javascript:<portlet:namespace />updateIdeaRate('${ideaId}', '${ideaRate.ideaRateCriteriaId}', '1', this.id);">1</a>
			    				</c:when>
			    				<c:otherwise>
			    					<a title="Negligible" class="cg-idea-rate-criteria-tooltip" href="javascript:void(0);" id='<portlet:namespace/>ideaRate1' onclick="javascript:<portlet:namespace />updateIdeaRate('${ideaId}', '${ideaRate.ideaRateCriteriaId}', '1', this.id);">1</a>
			    				</c:otherwise>
			    			</c:choose>
			    			<c:choose>
			    				<c:when test="${(not empty ideaRateT) && (ideaRateT.rateValue == 2) }">
			    					<a title="Barely Acceptable" class="cg-idea-rate-criteria-tooltip active" href="javascript:void(0);" id='<portlet:namespace/>ideaRate2' onclick="javascript:<portlet:namespace />updateIdeaRate('${ideaId}','${ideaRate.ideaRateCriteriaId}','2', this.id);">2</a>
			    				</c:when>
			    				<c:otherwise>
			    					<a title="Barely Acceptable" class="cg-idea-rate-criteria-tooltip" href="javascript:void(0);" id='<portlet:namespace/>ideaRate2' onclick="javascript:<portlet:namespace />updateIdeaRate('${ideaId}','${ideaRate.ideaRateCriteriaId}','2', this.id);">2</a>
			    				</c:otherwise>
			    			</c:choose>
			    			<c:choose>
			    				<c:when test="${(not empty ideaRateT) && (ideaRateT.rateValue == 3) }">
			    					<a title="Good" class="cg-idea-rate-criteria-tooltip active" href="javascript:void(0);" id='<portlet:namespace/>ideaRate3' onclick="javascript:<portlet:namespace />updateIdeaRate('${ideaId}','${ideaRate.ideaRateCriteriaId}','3', this.id);">3</a>
			    				</c:when>
			    				<c:otherwise>
			    					<a title="Good" class="cg-idea-rate-criteria-tooltip" href="javascript:void(0);" id='<portlet:namespace/>ideaRate3' onclick="javascript:<portlet:namespace />updateIdeaRate('${ideaId}','${ideaRate.ideaRateCriteriaId}','3', this.id);">3</a>
			    				</c:otherwise>
			    			</c:choose>
			    			<c:choose>
			    				<c:when test="${(not empty ideaRateT) && (ideaRateT.rateValue == 5) }">
			    					<a title="Exceptional" class="cg-idea-rate-criteria-tooltip active" href="javascript:void(0);" id='<portlet:namespace/>ideaRate5' onclick="javascript:<portlet:namespace />updateIdeaRate('${ideaId}','${ideaRate.ideaRateCriteriaId}','5', this.id);">5</a>
			    				</c:when>
			    				<c:otherwise>
			    					<a title="Exceptional" class="cg-idea-rate-criteria-tooltip" href="javascript:void(0);" id='<portlet:namespace/>ideaRate5' onclick="javascript:<portlet:namespace />updateIdeaRate('${ideaId}','${ideaRate.ideaRateCriteriaId}','5', this.id);">5</a>
			    				</c:otherwise>
			    			</c:choose>
			    			<c:choose>
			    				<c:when test="${(not empty ideaRateT) && (ideaRateT.rateValue == 8) }">
			    					<a title="Amazing" class="cg-idea-rate-criteria-tooltip active" href="javascript:void(0);" id='<portlet:namespace/>ideaRate8' onclick="javascript:<portlet:namespace />updateIdeaRate('${ideaId}','${ideaRate.ideaRateCriteriaId}','8', this.id);">8</a>
			    				</c:when>
			    				<c:otherwise>
			    					<a title="Amazing" class="cg-idea-rate-criteria-tooltip" href="javascript:void(0);" id='<portlet:namespace/>ideaRate8' onclick="javascript:<portlet:namespace />updateIdeaRate('${ideaId}','${ideaRate.ideaRateCriteriaId}','8', this.id);">8</a>
			    				</c:otherwise>
			    			</c:choose>
    					</div>
   					</td>
  				</tr>
  			</c:forEach>
		</table>
 	 </div>
 	 </div>
 	 <div class="idearate-box02">
	    <a href="<%= ideaRateChartURL.toString()%>"><img src="<%=request.getContextPath() %>/images/CurrScores.png" title="Current Score"></a>
	</div>
 	 </div>
</div>
<style>
body{ margin:0px; padding:0px;}
.cg-box01 {
	font: normal 12px/20px Arial, Helvetica, sans-serif;
	padding: 8px 12px;
	border-radius: 4px;
	background-color: #f3f3f3;
	border: 1px solid #e0e0e0;
	text-align: justify; position:relative;
	margin-bottom:30px;
}
.cg-icon-box {position:absolute; left:6px; top:8px;}
.cg-text-box01 {padding:0 0 0 30px;}

.cg-table-01{ border-radius:4px; overflow:hidden;text-align:center;}
.cg-table-01 table{  border-radius:4px; border-collapse:collapse; border:1px solid #e0e0e0; font:bold 14px/20px Arial, Helvetica, sans-serif; }
.cg-table-01 table th{ background-color:#F0F0F0;}

.cg-linkbox01{ font:bold 16px/20px Arial, Helvetica, sans-serif; text-align:center; width:205px; margin:0 auto;}
.cg-linkbox01 a{ width:26px; display:block; float:left; line-height:25px; padding:4px 4px; color:#000; text-decoration:none; background-color:#ebebeb; margin-right:5px; border-radius:4px; }
.cg-linkbox01 a.active{ color:#fff; text-decoration:none; background-color:#0c71c7; }

.idearate-box01{ float:left; width:80%;}
.idearate-box02{ float:left; width:20%; text-align:center;}

 @media only screen and (min-width:300px) and (max-width:600px) {
  .idearate-box01{ width:100%; margin-bottom:10px;}
  .idearate-box02{ width:100%;}
 }

.clearfix:before, .clearfix:after {	content: "";display: table;}
.clearfix:after {clear: both;}
.clearfix {zoom: 1;}


.ui-tooltip, .arrow:after {background: black;border: 2px solid white;}
.ui-tooltip {
    padding: 10px 20px;
    color: white;
    border-radius: 20px;
    font: bold 14px "Helvetica Neue", Sans-Serif;
    text-transform: uppercase;
    box-shadow: 0 0 7px black;
  }
.arrow {
    width: 70px;
    height: 16px;
    overflow: hidden;
    position: absolute;
    left: 50%;
    margin-left: -35px;
    bottom: -16px;
  }
.arrow.top {top: -16px;bottom: auto;}
.arrow.left {left: 20%;}
.arrow:after {
    content: "";
    position: absolute;
    left: 20px;
    top: -20px;
    width: 25px;
    height: 25px;
    box-shadow: 6px 5px 9px -9px black;
    -webkit-transform: rotate(45deg);
    -ms-transform: rotate(45deg);
    transform: rotate(45deg);
  }
.arrow.top:after {bottom: -20px;top: auto;}
</style>

<%@ include file="view_js.jsp"%>