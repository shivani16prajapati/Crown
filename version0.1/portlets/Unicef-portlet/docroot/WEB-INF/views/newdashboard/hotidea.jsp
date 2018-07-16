<%@include file="../init.jsp"%>


                <tr>
			      <th scope="col">IDEA NAME</th>
			      <th scope="col">INVENTOR</th>
			      <th scope="col">DATE SUBMITTED</th>
			      <th scope="col">UPVOTES</th>
			      <th scope="col">COMMENTS</th>
			    </tr>

<c:if test="${not empty hotIdeasList}">
<c:forEach items="${hotIdeasList}" var="recentHot">
<tr>
				      <td>${recentHot.getIdeaName()}</td>
				      <td>${recentHot.getInventorName()}</td>
				      <fmt:formatDate pattern="dd-MM-yyyy" value="${recentHot.getSubmissionDate()}" var="SubmissionDate" />
				      <td>${SubmissionDate}</td>
				      <td>${recentHot.getUpVotes()}</td>
				      <td>${recentHot.getComments()}</td>
</tr>
</c:forEach>
<c:if test="${see10 eq 1}">
<tr>
	<td colspan="5"><span style="float:right"><a href="javascript:void(0)" onclick="showhotidea(0,10)">See Top 10</a></span></td>			      
</tr>

</c:if>
</c:if>
<c:if test="${empty hotIdeasList}">
<tr>
	<td colspan="5">No Data Available</td>			      
</tr>

</c:if>