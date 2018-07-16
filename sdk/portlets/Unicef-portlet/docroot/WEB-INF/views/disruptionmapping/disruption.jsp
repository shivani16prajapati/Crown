<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<portlet:defineObjects />
<portlet:resourceURL var="resourceUrl1" id="resourceUrl1">
</portlet:resourceURL>
<portlet:resourceURL var="resourceUrl2" id="resourceUrl2">
</portlet:resourceURL>
<style>
.app-modal-window .modal-dialog {
  width: 900px;
  height:500px;
 
}
.modal-xl{
 width: 900px;
}
.menu-verticals-open .dis-map-menu svg {  left:0}
.pad{
		padding-left:5px;
		padding-right:5px;
}
            .width20{
                width:25%
            }
            
            .width6{
                width:6%
            }
            
            .width80{
                width:75%
            }
            
            .width94{
                width:94%
            }
            .row1{
                margin-right: -15px;
                margin-left: 0px;
            }
</style>

<body ng-app="disruption" style="center" ng-controller="DisruptionController as vm" class="ng-scope nav-static  chat-sidebar-container pace-done">    
<main ng-view  id="content" style="width:103%"class="content view-animate fade-up ng-scope" role="main" data-ui-view >
<h1 class="page-title">Disruption Map</h1>
<section ng-controller="DisruptionController as vm" class="widget disruption-map">
  <div class="widget-body" >
    <div class="menu-verticals-open"  ng-class="(vm.menuOpened==true) ? 'hover width20' : 'width6'"  style="float:left;background-color:#fff;padding-left:10px">
      <div class="dis-map-menu">
        <svg ng-click="vm.openMenu()" width="55" height="70">
          <rect x="15" y="20" width="4" height="45" fill="#5879A6"></rect>
          <rect x="22" y="20" width="4" height="45" fill="#5879A6"></rect>
          <rect x="29" y="20" width="4" height="45" fill="#5879A6"></rect>
          <rect x="36" y="20" width="4" height="45" fill="#5879A6"></rect>
        </svg>
        <h3 ng-click="vm.toggleMenu()">VERTICALS</h3>
        <div style="padding-top:60px;padding-left:0px">
         <ul class="dis-map-verticals">
          <li ng-click="vm.setActiveVertical(vertical)" ng-repeat="vertical in vm.verticals"
              ng-class="(vm.activeVertical === vertical.name || vm.selectedVertical === vertical.name) ? 'active' : ''">
            <span class="vertical-icon">
              <img ng-src="{{ vertical.icon }}">
            </span>
            <span class="VerticalList" style="display:none">{{ vertical.name | ClearUnderscore}}</span>  
          </li>
        </ul>
      </div>
         </div>
      </div>
      
    <div class="main-window" ng-class="(vm.menuOpened==true) ? 'width80' : 'width94' " style="float:left">
        
         <div class="dis-map-all-signals">
          <div class="row1 dis-map-current-signals" ng-repeat="vertical in vm.verticals" id="{{vertical.name}}"
               ng-class="(vm.activeVertical==vertical.name && vm.selectedVertical !== vertical.name) ? 'active' : ''">
                <div class="col-md-2">
                <h4>{{ vertical.name | ClearUnderscore}}</h4>
                </div>
                <div test-Signal vertical="vertical" class="col-md-10"></div>
          </div>
            
        <div class="dis-map-all-verticals row1">
            <div class="col-md-2">
              <ol class="nya-bs-select" ng-model="vm.selectedVertical">
                <li class="nya-bs-option" data-value="all">
                  <a ng-click="vm.comparedVertical = vm.allVerticals">ALL VERTICALS</a>
                </li>
                <li ng-repeat="vertical in vm.verticals" class="nya-bs-option" data-value="{{vertical.name}}" ng-if="vertical.name != vm.activeVertical">
                  <a ng-click="vm.comparedVertical = vertical">{{vertical.name | ClearUnderscore}}</a>
                </li>
              </ol>
            </div>
                <div test-Signal vertical="vm.comparedVertical" class="col-md-10"></div>
          </div>
        </div>
        
        <div class="dis-map-trends row1">
          <div class="row1">
            <div class="axis-width col-md-10 col-md-offset-2 col-sm-10 col-xs-10 col-xs-offset-2 col-sm-offset-2 col-lg-10 col-lg-offset-2"></div>
          </div>
           <div test-Directive ng-repeat="trend in trends"  dataa="trend" color="backgrounds[$index]"></div>
           <div time-Line class="row1 time-Line"></div>
           
              </div>
             
      </div>
        </div>
    </section>
    
    </main>
    </body>
    
    <script>
    var resourceURL1 = '${resourceUrl1}';
    var resourceURL2 = '${resourceUrl2}';
    </script>