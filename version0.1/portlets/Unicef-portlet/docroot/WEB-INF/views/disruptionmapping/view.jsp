<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />


<style>
.content{
	min-height: 100%;
	padding: 40px 40px 40px;
    padding-top: 40px;
    padding-right: 40px;
    padding-bottom: 40px;
    padding-left: 40px;
	background-color: #c5c2c2;
	position: absolute;
	width: 100%;
}
.fdf { background:url(file:///C:/Users/BAPS/Desktop/disruptionBG.png) left top repeat-x;}

</style>

	<main id="content" class="content">

		<h1 class="page-title ng-scope">Disruption Map</h1>
		
		<div class="fdf">
			<img src="<%=request.getContextPath()%>/images/disruption_map.png"/>
		</div>
	</main>

