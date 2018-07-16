
var svgPath = themeDisplay.getPathThemeImages(); 

console.log(" IMAGE DIR PATH : " + svgPath );

console.log(" ICON PATH : " + svgPath+"/json/disruption/'+ id + '.json" );

var verticalsData =  [  
                  	{
                        "name": "Retail",
                        "icon": svgPath + "/svg/Retail.svg",
                        "signals": [
                          {
                            "id": "retail_one",
                            "name": "one",
                            "startDate": 1457228800,
                            "endDate": 1577836800,
                            "impact": 4
                          },
                          {
                            "id": "retail_two",
                            "name": "two",
                            "startDate": 1735689600,
                            "endDate": 1893456000,
                            "impact": 2
                          }
                        ]
                      },
                      {
                        "name": "Financial_Services",
                        "icon": svgPath + "/svg/FinancialServices.svg",
                        "signals": [
                          {
                            "id": "financial_service_three",
                            "name": "three",
                            "startDate": 1462228800,
                            "endDate": 1514764800,
                            "impact": 4
                          },
                          {
                            "id": "financial_service_four",
                            "name": "four",
                            "startDate": 1577836800,
                            "endDate": 1735689600,
                            "impact": 1
                          }
                        ]
                      },
                      {
                        "name": "Engineering",
                        "icon": svgPath + "/svg/Engineering.svg",
                        "signals": [
                          {
                            "id": "engineering_five",
                            "name": "five",
                            "startDate": 1461228800,
                            "endDate": 1514764800,
                            "impact": 4
                          },
                          {
                            "id": "engineering_six",
                            "name": "six",
                            "startDate": 1577836800,
                            "endDate": 1735689600,
                            "impact": 1
                          }
                        ]
                      },
                      {
                        "name": "Healthcare",
                        "icon": svgPath +"/svg/Healthcare.svg",
                        "signals": [
                          {
                            "id": "healthcare_five",
                            "name": "five",
                            "startDate": 1453228800,
                            "endDate": 1514764800,
                            "impact": 4
                          },
                          {
                            "id": "healthcare_six",
                            "name": "six",
                            "startDate": 1577836800,
                            "endDate": 1735689600,
                            "impact": 1
                          }
                        ]
                      },
                      {
                        "name": "Media",
                        "icon": svgPath +"/svg/Media.svg",
                        "signals": [
                          {
                            "id": "media_five",
                            "name": "five",
                            "startDate": 1441228800,
                            "endDate": 1514764800,
                            "impact": 4
                          },
                          {
                            "id": "media_six",
                            "name": "six",
                            "startDate": 1577836800,
                            "endDate": 1735689600,
                            "impact": 1
                          }
                        ]
                      },
                      {
                        "name": "Manufacturing",
                        "icon": svgPath +"/svg/Manufacturing.svg",
                        "signals": [
                          {
                            "id": "manufacturing_five",
                            "name": "five",
                            "startDate": 1442228800,
                            "endDate": 1514764800,
                            "impact": 4
                          },
                          {
                            "id": "manufacturing_six",
                            "name": "six",
                            "startDate": 1577836800,
                            "endDate": 1735689600,
                            "impact": 1
                          }
                        ]
                      },
                      {
                        "name": "Automotive",
                        "icon": svgPath + "/svg/Automotive.svg",
                        "signals": [
                          {
                            "id": "automotive_five",
                            "name": "five",
                            "startDate": 1443228800,
                            "endDate": 1514764800,
                            "impact": 4
                          },
                          {
                            "id": "automotive_six",
                            "name": "six",
                            "startDate": 1577836800,
                            "endDate": 1735689600,
                            "impact": 1
                          }
                        ]
                      },
                      {
                        "name": "Public_Sector",
                        "icon": svgPath +"/svg/PublicSector.svg",
                        "signals": [
                          {
                            "id": "public_sector_five",
                            "name": "five",
                            "startDate": 1488228800,
                            "endDate": 1514764800,
                            "impact": 4
                          },
                          {
                            "id": "public_sector_six",
                            "name": "six",
                            "startDate": 1577836800,
                            "endDate": 1735689600,
                            "impact": 1
                          }
                        ]
                      },
                      {
                        "name": "Utility_and_Energy",
                        "icon": svgPath +"/svg/UtilitiesAndEnergy.svg",
                        "signals": [
                          {
                            "id": "utility_and_energy_five",
                            "name": "five",
                            "startDate": 1478228800,
                            "endDate": 1514764800,
                            "impact": 3
                          },
                          {
                            "id": "utility_and_energy_six",
                            "name": "six",
                            "startDate": 1577836800,
                            "endDate": 1735689600,
                            "impact": 1
                          }
                        ]
                      },
                      {
                        "name": "Hightech",
                        "icon": svgPath +"/svg/Hightech.svg",
                        "signals": [
                          {
                            "id": "hightech_five",
                            "name": "five",
                            "startDate": 1448228800,
                            "endDate": 1514764800,
                            "impact": 4
                          },
                          {
                            "id": "hightech_six",
                            "name": "six",
                            "startDate": 1577836800,
                            "endDate": 1735689600,
                            "impact": 1
                          }
                        ]
                      },
                      {
                        "name": "Diversified",
                        "icon": svgPath +"/svg/Diversified.svg",
                        "signals": [
                          {
                            "id": "diversified_five",
                            "name": "five",
                            "startDate": 1458228800,
                            "endDate": 1514764800,
                            "impact": 4
                          },
                          {
                            "id": "diversified_six",
                            "name": "six",
                            "startDate": 1577836800,
                            "endDate": 1735689600,
                            "impact": 1
                          }
                        ]
                      }
                    ];

var trendData = [
                 {
                     "name": "national",
                     "icon": svgPath +"/svg/National.svg",
                     "subtrends": [
                       {
                         "name": "First subtrend",
                         "icon": svgPath +"/svg/MEMs.svg",
                         "disruptions": [
                           {
                             "id": "mobile_recept",
                             "name": "Broadcast reception in Phones",
                             "date": 1609459200,
                             "icon": svgPath +"/svg/MobileRecept.svg"
                           },
                           {
                             "id": "eye_tracking",
                             "name": "Eye Tracking",
                             "date": 1498228800,
                             "icon": svgPath +"/svg/EyeTracking.svg",
                             "connections": {
                               "disruptions": [
                                 {
                                   "id": "smart_antenna",
                                   "ratio": 0.4
                                 },
                                 {
                                   "id": "biometric_iris",
                                   "ratio": 1
                                 }
                               ],
                               "verticals": [
                                 {
                                   "id": "distributed_ip",
                                   "ratio": 0.3
                                 },
                                 {
                                   "id": "power_harvesting",
                                   "ratio": 0.5
                                 }
                               ]
                             }
                           },
                           {
                             "id": "dig_money",
                             "name": "No physical money",
                             "date": 1893456000,
                             "icon": svgPath +"/svg/DigMoney.svg"
                           }
                         ]
                       },
                       {
                         "name": "Second subtrend",
                         "icon": svgPath +"/svg/Idea.svg",
                         "disruptions": [
                           {
                             "id": "video_phone",
                             "name": "Video Phone & Identity",
                             "date": 1447890400,
                             "icon": svgPath +"/svg/Identity.svg",
                             "connections": {
                               "disruptions": [
                                 {
                                   "id": "palm_vowel_kb",
                                   "ratio": 0.7
                                 }
                               ],
                               "verticals": [
                                 {
                                   "id": "distributed_ip",
                                   "ratio": 0.2
                                 },
                                 {
                                   "id": "power_harvesting",
                                   "ratio": 0.4
                                 },
                                 {
                                   "id": "retail_one",
                                   "ratio": 0.3
                                 },
                                 {
                                   "id": "retail_two",
                                   "ratio": 0.5
                                 }
                               ]
                             }
                           },
                           {
                             "id": "biometric_iris",
                             "name": "Biometric Iris",
                             "date": 1546300800,
                             "icon": svgPath +"/svg/BioIris.svg"
                           }
                         ]
                       }
                     ]
                   },
                   {
                     "name": "corporate",
                     "icon": svgPath +"/svg/Corporate.svg",
                     "subtrends": [
                       {
                         "name": "subtrend",
                         "icon": svgPath +"/svg/Battery.svg",
                         "disruptions": [
                           {
                             "id": "t9",
                             "name": "T9",
                             "date": 1447890400,
                             "icon": svgPath +"/svg/Idea.svg"
                           },
                           {
                             "id": "palm_vowel_kb",
                             "name": "Palm vowel KB",
                             "date": 1489228800,
                             "icon": svgPath +"/svg/Idea.svg"
                           },
                           {
                             "id": "fear_privacy_progressive",
                             "name": "Fear privacy progressive relationships",
                             "date": 1537300800,
                             "icon": svgPath +"/svg/Privacy.svg"
                           },
                           {
                             "id": "no_more_physical_mail",
                             "name": "No more physical mail - no more use of paper",
                             "date": 1609459200,
                             "icon": svgPath +"/svg/Paperless.svg"
                           },
                           {
                             "id": "monacle_display",
                             "name": "\"Monacle\" display",
                             "date": 1893456000,
                             "icon": svgPath +"/svg/Monacle.svg"
                           },
                           {
                             "id": "dna_lock",
                             "name": "DNA Lock w/IP",
                             "date": 2208988800,
                             "icon": svgPath +"/svg/DNA.svg"
                           }
                         ]
                       }
                     ]
                   },
                   {
                     "name": "personal",
                     "icon": svgPath +"/svg/Personal.svg",
                     "subtrends": [
                       {
                         "name": "subtrend",
                         "icon": svgPath +"/svg/Idea.svg",
                         "disruptions": [
                           {
                             "id": "realtime_logistic_tracking",
                             "name": "Realtime Logistics Tracking",
                             "date": 1447890400,
                             "icon": svgPath +"/svg/Logistics.svg"
                           },
                           {
                             "id": "barcode_machine",
                             "name": "\"barcode\" machine active ID RF tags F-10",
                             "date": 1502228800,
                             "icon": svgPath +"/svg/Barcode.svg"
                           },
                           {
                             "id": "smart_antenna",
                             "name": "Smart Antenna (aim and receive)",
                             "date": 1546300800,
                             "icon": svgPath +"/svg/Antenna.svg"
                           },
                           {
                             "id": "the_mems_revolution",
                             "name": "The MEMs Revolution Voice cancellation, heat, humidity",
                             "date": 1609459200,
                             "icon": svgPath +"/svg/MEMs.svg"
                           },
                           {
                             "id": "gsr",
                             "name": "GSR",
                             "date": 1893456000,
                             "icon": svgPath +"/svg/Idea.svg"
                           }
                         ]
                       }
                     ]
                   },
                   {
                     "name": "computing",
                     "icon": svgPath +"/svg/Computing.svg",
                     "subtrends": [
                       {
                         "name": "subtrend",
                         "icon": svgPath +"/svg/Antenna.svg",
                         "disruptions": [
                           {
                             "id": "dram_memory_stick",
                             "name": "DRAM Memory Stick Compact Flash Smartmedia",
                             "date": 1447890400,
                             "icon": svgPath +"/svg/DRAM.svg"
                           },
                           {
                             "id": "nimh_nidc",
                             "name": "NiMH NIDC",
                             "date": 1483228800,
                             "icon": svgPath +"/svg/Idea.svg"
                           },
                           {
                             "id": "10w_power_geoposition",
                             "name": "10W Power Geoposition",
                             "date": 1546300800,
                             "icon": svgPath +"/svg/Geolocation.svg"
                           },
                           {
                             "id": "power_harvesting",
                             "name": "Power Harvesting",
                             "date": 1609459200,
                             "icon": svgPath +"/svg/Harvest.svg"
                           }
                         ]
                       }
                     ]
                   },
                   {
                     "name": "network",
                     "icon": svgPath +"/svg/Network.svg",
                     "subtrends": [
                       {
                         "name": "subtrend",
                         "icon": svgPath +"/svg/Harvest.svg",
                         "disruptions": [
                           {
                             "id": "ultra_mda",
                             "name": "Ultra MDA  1.8\"",
                             "date": 1447890400,
                             "icon": svgPath +"/svg/Idea.svg"
                           },
                           {
                             "id": "1_gb",
                             "name": "1\" GB",
                             "date": 1483228800,
                             "icon": svgPath +"/svg/Idea.svg"
                           },
                           {
                             "id": "photo_rechargeable",
                             "name": "Photo Rechargeable AIR Battery",
                             "date": 1521300800,
                             "icon": svgPath +"/svg/Battery.svg"
                           },
                           {
                             "id": "question",
                             "name": "?",
                             "date": 1609459200,
                             "icon": svgPath +"/svg/Idea.svg"
                           },
                           {
                             "id": "distributed",
                             "name": "Distributed",
                             "date": 1893456000,
                             "icon": svgPath +"/svg/Distribute.svg"
                           }
                         ]
                       }
                     ]
                   },
                   {
                     "name": "infrastructure",
                     "icon": svgPath +"/svg/Infrastructure.svg",
                     "subtrends": [
                       {
                         "name": "subtrend",
                         "icon": svgPath +"/svg/Distribute.svg",
                         "disruptions": [
                           {
                             "id": "disrup_1",
                             "name": "disrup 1",
                             "date": 1447890400,
                             "icon": svgPath +"/svg/Battery.svg"
                           },
                           {
                             "id": "disrup_2",
                             "name": "disrup 2",
                             "date": 1683228800,
                             "icon": svgPath +"/svg/Harvest.svg"
                           }
                         ]
                       }
                     ]
                   }
                 ];

var allVerticalData =  [
                        {
                            "id": "distributed_ip",
                            "name": "Distibuted IP",
                            "startDate": 1442870400,
                            "endDate": 1577836800,
                            "impact": 4
                          },
                          {
                            "id": "power_harvesting",
                            "name": "Power Harvesting",
                            "startDate": 1735689600,
                            "endDate": 1893456000,
                            "impact": 2
                          }
                        ]
                      ;

var App = angular.module('disruption', ['nya.bootstrap.select','ui.bootstrap']); 		
        App.controller('DisruptionController',['$scope','$http',function ($scope,$http) {
		
        var vm = this;
       
        vm.activeVertical = '';
        vm.allVerticals = {};
        $scope.backgrounds =['#C8C2C4', '#BAB4B6', '#ADA3A2', '#A49A99', '#998F8E', '#948485'];
        vm.comparedVertical = 'undefined';
        vm.menuOpened = false;
        vm.selectedVertical = 'all';
        $scope.trends = {};
        vm.verticals = [];
        
        //$http.get("/assets/verticals.json").success(function(data){
            vm.verticals=verticalsData;
        //});
            
        //$http.get("/assets/trends.json").success(function(dataa){
                $scope.trends=trendData; 
        //});   
            
        $http.get(svgPath+"/json/allVerticals.json").success(function(data){
            vm.allVerticals=data;
            vm.comparedVertical = vm.allVerticals;
        });    
            
        vm.setActiveVertical = function (vertical) {
            vm.activeVertical = vertical.name;
        }

       vm.toggleMenu = function () {
            vm.menuOpened = !vm.menuOpened;
           if(vm.menuOpened==true){ 
            $('.VerticalList').show();
           }else{
               $('.VerticalList').hide();
           }
        }

        function activate() {
          disruptionsDataService.getDisruptions().success(function(response) {
            vm.allVerticals = response.allVerticals;
            vm.comparedVertical = vm.allVerticals;
            vm.verticals = response.verticals;
            $scope.trends = response.trends;
          });
        }    
            
            //w3TestDirective
        }]).directive('w3TestDirective', function($compile, $rootScope) {
         
        return{
              controller : "DisruptionController",
              controllerAs : "vm",
              scope: {
                user:'=user'},
        template : "<P>Made by a directive! <b>{{vm.deep.firstname}}</b></p>"
    }
    });
        //filter
        App.filter('ClearUnderscore',function(){
            return function(str){
                if(str.indexOf('_')!=-1){
                    str = str.split('_').join(' ');
                }
                return str;
            }
        });
     
        //Signal
        App.directive('testSignal',function(){
        var directive = {};
        directive.restrict = 'EA';
        directive.controller = "DisruptionController";
        directive.controllerAs="vm";
        directive.scope = {
            
            vertical : '=vertical'
        };
        
        directive.link = function(scope,iElement,iAttrs){
            var svg = d3.select(iElement[0])
                .append('svg')
                .attr('x', 0)
                .attr('y', -20)
                .classed('dis-map-vertical-values', true);
         
            
        // on window resize, re-render d3 canvas
        window.onresize = function() {
            
            return scope.$apply();
        };
            
         
        scope.$watch('vertical', function() {
            
        if (scope.vertical !== undefined) {
          scope.$watch(function() {
              
              return angular.element(window)[0].innerWidth;
            }, function() {
              
              return scope.render(scope.vertical);
            }
          );
        }
        });
            
        // watch for data changes and re-render
        scope.$watch('vertical', function(newVals, oldVals) {
            if (scope.vertical !== undefined) {
                return scope.render(newVals);
        }
        }, true);   
        
        // define render function
      scope.render = function(vertical) {
        // remove all previous items before render
        svg.selectAll('*').remove();

        var date = function(x) {
          return new Date(x * 1000);
        };

        var width = angular.element('.axis-width').width();

        var scaleX = d3.time
          .scale()
          .domain([new Date('2016/01/01 00:00:00'), new Date('2020/01/01 00:00:00'), new Date('2050/01/01 00:00:00')])
          .range([70, width / 1.7, width - 50]);


        scope.$on('impact-vertical', function(event, args) {
            
          angular.forEach(args.verticals, function(vert) {
            angular.forEach(scope.vertical.signals, function(obj) {
              if(obj.id === vert.id) {
                var ratio = vert.ratio;
                angular.element('#' + vert.id + ' rect').attr('x', parseFloat(scaleX(date(obj.startDate)) + args.x*ratio));
                angular.element('#' + vert.id + ' .signal-impact').attr('x', parseFloat(scaleX(date(obj.endDate)) - obj.impact * 7 + args.x*ratio));
                angular.element('#' + vert.id + ' .signal-name').attr('x', parseFloat(scaleX(date(obj.startDate)) + (scaleX(date(obj.endDate)) - scaleX(date(obj.startDate))) / 2 + args.x*ratio));
              }
            });
          });
        });
        
        var gradient = svg.append('defs')
          .append('linearGradient')
          .attr('id', 'gradient')
          .attr('x1', '0%')
          .attr('y1', '0%')
          .attr('x2', '100%')
          .attr('y2', '0%');

        gradient.append('stop')
          .attr('offset', '0%')
          .attr('style', 'stop-color:#D2E3F7;stop-opacity:1');

        gradient.append('stop')
          .attr('offset', '100%')
          .attr('style', 'stop-color:#5879A6;stop-opacity:1');

        var signals = svg
          .selectAll('g')
          .data(vertical.signals)
          .enter()
          .append('g')
          .classed('dis-map-signal', true)
          .attr('id', function(d) {return d.id;});

        signals.append('rect')
          .attr('x', function(d) {return scaleX(date(d.startDate));})
          .attr('y', 30)
          .attr('rx', function(d) {return d.impact * 3;})
          .attr('ry', function(d) {return d.impact * 3;})
          .attr('fill', 'url(#gradient)')
          .attr('width', function(d) {return scaleX(date(d.endDate)) - scaleX(date(d.startDate));})
          .attr('height', function(d) {return d.impact * 6 + 6;});

        signals.append('text')
          .attr('class', 'signal-impact')
          .text(function(d) {return d.impact;})
          .attr('x', function(d) {return scaleX(date(d.endDate)) - d.impact * 7;})
          .attr('y', function(d) {return 33 + d.impact * 6;})
          .style('font-size', function(d) {return d.impact * 8;});

        signals.append('text')
          .attr('class', 'signal-name')
          .text(function(d) {return d.name;})
          .attr('x', function(d) {return scaleX(date(d.startDate)) + (scaleX(date(d.endDate)) - scaleX(date(d.startDate))) / 2;})
          .attr('y', function(d) {return d.impact * 6 + 48;});
       
        }
        
    };
    
    
        return directive;
    });
        
        //trend
        App.directive('testDirective',['$compile', '$rootScope',function( $compile, $rootScope) {
      
        var directive = {};
        
        directive.restrict = 'EA';
        directive.controller = "DisruptionController";
        directive.controllerAs="vm";
        directive.scope={
            backgroundColor: '=color',
                trendData : "=dataa"
        };
        directive.link =  function (scope, iElement, iAttrs){
    
            var trendRow = d3.select(iElement[0]).classed('dis-map-trend row1',true);
              
            window.onresize = function() {
                    return scope.$apply();
            };
             
            scope.$watch('trendData', function() {
                if (scope.trendData !== undefined) {
                    scope.$watch(function() { 
                        return angular.element(window)[0].innerWidth;
                        }, function() {
                    return scope.render(scope.trendData);
                        });
                    }
            });
        
            scope.$watch('trendData', function(newVals, oldVals) {
                if (scope.trendData !== undefined) {
                    return scope.render(newVals);
                } }, true);

            scope.render = function(trendData) {
            
                trendRow.selectAll('*').remove();
            
            var width = angular.element('.axis-width').width();

            var scaleX = d3.time.scale()
            .domain([new Date("2016/01/01 00:00:00"), new Date("2020/01/01 00:00:00"), new Date("2050/01/01 00:00:00")])
            .range([70, width / 1.7, width - 50]);

            var draggable = d3.behavior.drag().on('dragstart', function() {
            d3.event
              .sourceEvent
              .stopPropagation();
            }).on('drag', function() {
            if (d3.event.x >= 50 && d3.event.x <= width - 50) {
              var scale = scaleX.invert(d3.event.x - 18);
              d3.select(this).attr('transform', 'translate(' + scaleX(scale) + ',25)');
              scope.$broadcast('send-signal', { transform: scaleX(scale), id: this.id });
            }
            });
            
             scope.$on('send-signal', function(event, args) {
                angular.forEach(trendData.subtrends, function(subtrend) {
                angular.forEach(subtrend.disruptions, function(obj) {
                if(obj.id === args.id) {
                    if (obj.connections) {
                        angular.forEach(obj.connections.disruptions, function(disruption) {
                        var connectedDisruption = angular.element('#' + disruption.id);
                        var ratio = disruption.ratio;
                        connectedDisruption.attr('transform', 'translate(' + parseFloat(scaleX(date(obj.date)) + args.transform*ratio) + ',25)');
                        });
                  // use $rootScope to watch event in signal directive
                  $rootScope.$broadcast('impact-vertical', { x: args.transform, verticals: obj.connections.verticals });
                }
              }
            });
          });
        });
        
            function wrap(text, width) {
                text.each(function() {
                    var text = d3.select(this),
                    words = text.text().split(/\s+/).reverse(),
                    word,
                    line = [],
                    lineNumber = 0,
                    lineHeight = 1.1, // ems
                    y = text.attr('y'),
                    dy = parseFloat(text.attr('dy'));

                var  tspan = text.text(null)
                            .append('tspan')
                            .classed('visible-lg', true)
                            .attr('x', 18)
                            .attr('text-anchor', 'middle')
                            .attr('y', y)
                            .attr('dy', dy + 'em');

                while (word = words.pop()) {
                        line.push(word);
                        tspan.text(line.join(' '));
                    if (tspan.node().getComputedTextLength() > width) {
                        line.pop();
                        tspan.text(line.join(' '));
                        line = [word];
                        tspan = text.append('tspan')
                              .attr('x', 18)
                              .attr('text-anchor', 'middle')
                              .attr('y', y)
                              .attr('dy', ++lineNumber * lineHeight + dy + 'em')
                              .text(word);
              }
            }
          });
        }
            
            var tip = d3.tip()
                .attr('class', 'd3-tip hidden-lg')
                .html(function(d) {
                return '<span>' + d.name + '</span>';
              });

            var date = function(x) {
                return new Date(x * 1000);
                };

            var svg = trendRow.append('div')
                .classed(trendData.subtrendsVisible ?
                      'dis-map-trend-name col-md-1 col-sm-1 col-xs-1 col-lg-1 pad' :
                      'dis-map-trend-name col-md-2 col-sm-2 col-xs-2 col-lg-2 pad', true)
                .style('background', scope.backgroundColor)
                .append('svg');

            svg.append('image')
                .attr('xlink:href', trendData.icon)
                .attr('width', 50)
                .attr('height', 50)
                .attr('y', 15)
                .attr('x', 25)
                .style('cursor', 'pointer')
                .on('click', function() {
                    var row = d3.select(iElement[0]);
                   
                        if (!trendData.subtrendsVisible) {
                            if (trendData.subtrends.length > 1) {
                             row.style('background', scope.backgroundColor)
                                .transition()
                                .style('height', trendData.subtrends.length * 100 + 'px')
                                .each('end', function() {
                                    scope.trendData.subtrendsVisible = true;
                                    scope.$apply(); // trigger $watch function
                                });
                            } else {
                                scope.trendData.subtrendsVisible = true;
                                scope.$apply(); // trigger $watch function
                              }
                        } else {
                              scope.trendData.subtrendsVisible = false;
                              scope.$apply(); // trigger $watch function
                              row.transition()
                                .style('height', '100px');
                            }
                          });

                svg.append('text')
                  .attr('class', 'trend-name')
                  .text(trendData.name)
                  .attr('x', 50)
                  .attr('y', 75);

                
        if (trendData.subtrendsVisible) {

          var isFirstRow = true; // We do not need to append fake block to the first row
          var index = 0; // Index will be a unique identifier of each row
          var length = trendData.subtrends.length;

          trendData.subtrends.forEach(function(subtrend) {
            if (!isFirstRow) {
              trendRow.append('div')
                .classed('dis-map-trend-name col-md-1 col-sm-1 col-xs-1 col-lg-1 pad', true)
                .style('background', scope.backgroundColor)
                .append('svg');
            }

            // Append subtrend title container
            var svg = trendRow.append('div')
              .classed('dis-map-trend-name col-md-1 col-sm-1 col-xs-1 col-lg-1 pad', true)
              .style('background', scope.backgroundColor)
              .append('svg');

            // Append subtrend title image
            svg.append('image')
              .attr('xlink:href', subtrend.icon)
              .attr('width', 50)
              .attr('height', 50)
              .attr('y', 15)
              .attr('x', 25);

            // Append subtrend title text
            svg.append('text')
              .attr('class', 'trend-name')
              .text(subtrend.name)
              .attr('x', 50)
              .attr('y', 75);

            trendRow.append('div')
              .classed('dis-map-trend-disruptions col-md-10 col-xs-10 col-sm-10 col-lg-10s pad', true)
              .classed(index < length - 1 ? 'dis-map-subtrend-disruptions' : '', true) // Add bottom line
              .style('background', scope.backgroundColor)
              .append('svg')
              .classed('dis-map-trend-' + trendData.name + '-' + index, true);

            var trendItem = d3.select('.dis-map-trend-' + trendData.name + '-' + index)
              .selectAll('g')
              .data(subtrend.disruptions)
              .enter()
              .append('g')
              .attr('single-disruption', '')
              .attr('id', function(d) { return d.id; })
              .attr('transform', function(d) {
                var x = scaleX(date(d.date));
                return 'translate(' + x + ',25)';
              })
              .style('cursor', 'pointer')
              .call(draggable);

            trendItem.append('image')
              .attr('xlink:href', function(d) {return d.icon;})
              .attr('width', 36)
              .attr('height', 36)
              .attr('y', 0)
              .attr('x', 0);

            trendItem.append('text')
              .text(function(d) {return d.name;})
              .attr('x', 0)
              .attr('y', 38)
              .attr('dy', 1)
              .call(wrap, 110);

            if (isFirstRow) {
              isFirstRow = false;
            }
            ++index;
          });
        } else {
          trendRow.append('div')
            .classed('dis-map-trend-disruptions col-md-10 col-sm-10 col-xs-10 col-lg-10', true)
            .style('background', scope.backgroundColor)
            .append('svg')
            .classed('dis-map-trend-' + trendData.name, true);

          var allDisruption = [];
          trendData.subtrends.forEach(function(subtrend) {
            allDisruption = allDisruption.concat(subtrend.disruptions);
          });

          var trendItem = d3.select('.dis-map-trend-' + trendData.name)
            .selectAll('g')
            .data(allDisruption)
            .enter()
            .append('g')
            .attr('single-Disruption', '')
            .attr('id', function(d) { return d.id; })
            .attr('transform', function(d) {
              var x = scaleX(date(d.date));
              return 'translate(' + x + ',25)';
            })
            .style('cursor', 'pointer')
            .call(draggable)
            .on('mouseover', tip.show)
            .on('mouseout', tip.hide);

          trendItem.append('image')
            .attr('xlink:href', function(d) {return d.icon;})
            .attr('width', 36)
            .attr('height', 36)
            .attr('y', 0)
            .attr('x', 0);

          trendItem.append('text')
            .text(function(d) {return d.name;})
            .attr('x', 0)
            .attr('y', 38)
            .attr('dy', 1)
            .call(wrap, 110)
            .call(tip);
        }
             $compile(iElement.contents())(scope);
    };
        }
   return directive ;
        
    }                   
   ]);
      //singledisruption
        App.directive('singleDisruption', ['$modal','$http','$timeout',function($modal, $http, $timeout) {
       var directive ={};
        directive.restrict = 'A';
        directive.scope = {
            id: '@'
        };
       
       var p = directive.scope;
       directive.link = function(scope, iElement, iAttrs){
          
            iElement.bind('click', function(event) {
                
               if (event.isDefaultPrevented()) return;
              /* $.ajax({
                   url: resourceURL ,
                   type: 'GET',
                   datatype:'json',
                   success: function(data){
                       var obj = $.parseJSON(data);
                       console.log(obj);
                   }
               });*/ 
           $http.get(resourceURL1).success(function(data){
            	
        	   console.log(data);
        	   
            		scope.data = data;
            		console.log(data);
                    showModal(data);
            	});
                
            /*    $http.get(svgPath+'/json/disruption/'+scope.id+'.json').success(function(data){
                   getDisruptionById(iAttrs.id).then(function (data) {
                   scope.data = data
                        showModal(data);
                   });
      });
              getDisruptionById = function(id){
               return $http.get(svgPath+'/json/disruption/'+ id + '.json')
               .then(getDisruptionByIdComplete)
               .catch(getDisruptionByIdFailed);     
               function getDisruptionByIdComplete(response) {
                   return response.data;
               }
               function getDisruptionByIdFailed(error) {
                   logger.error('Request Failed for getDisruptionById.' + error.data);
                 }
           };*/
        
           showModal = function(data1) {
               var modalInstance = $modal.open({
               animation: true,
               template: '<div analysis-chart class="chart" data={{data1}}></div>',
               size:'lg',
               controller: function($scope, data) {
               $scope.data1 = data1;
               },
               resolve: {
                   data: function() {
                       return scope.data1;
                   }
               }
           });
       
   };
          });
       };
          
        return directive;
           
 }]);
//analysisChart
        App.directive('analysisChart', function($http) {
            
            var directive = {};
                
              directive.restrict= 'A';
              directive.scope= {
                data: '@'
               
              };
            
              directive.link= function(scope, iElement, iAttrs) {
                  
                    var trendItem=null;
                    var chartContainer = d3.select(iElement[0]);

                  window.onresize = function() {
                    return scope.$apply();
                  };

                  scope.$watch('data', function() {
                    if (scope.data !== undefined) {
                      scope.$watch(function() {
                          return angular.element(window)[0].innerWidth;
                        }, function() {
                          return scope.render(scope.data);
                        }
                      );
                    }
                  });

                  scope.$watch('data', function(newVals, oldVals) {
                    if (scope.data !== undefined) {
                      return scope.render(newVals);
                    }
                  
                }| true);

                  scope.render = function(data) {
                    
                    chartContainer.selectAll('*').remove();
                      
                    data = (typeof data === 'string') ? JSON.parse(data) : data;
                    console.log(data);
                    var closeButton = chartContainer.append('button')
                      .attr('class', 'close mr-sm mt-sm')
                        .attr('id', 'but')
                      .append('span')
                      .html('&times;');

                    closeButton.on("click", function() {
                      angular.element('.modal.fade').trigger('click');
                      d3.event.stopPropagation();
                    });
                    
                    
                    $('#but').wrapAll("<div style='min-height:50px;'><div>");
                      var chart = null;
                      var item={};var mainItem={};
                        var values=[];var finalData=[];
                      var ACCELERATED_GROWTH = 200;
                        var NORMAL_GROWTH = 100;
                        var FLAT = 10;
                        var NORMAL_DECAY = 25;
                        var ACCELERATED_DECAY = 500;
                        var MINOR_CHANGE = 0.5;
                        var MEDIUM_CHANGE = 1.5;
                        var MAJOR_CHANGE = 5;
                        var startIndex = 0;
                        var state = 0;
                        var endIndex = 0;
                      var start=0,end=0;
                       var isLast = 0;
                      var id,id1,k;
                      var oneDay = 24*60*60*1000;
                      var formulaForMinor;
                        var formulaForMedium;
                        var formulaForMajor;
                      var scale,impact,diffDays,value,firstDate,secondDate;
                      var sliderDiv = chartContainer.append("div")
                        .attr("id","sliderContainer");
                       var slider1 = sliderDiv.append("div")
                        .attr("id","slider1")
                        .attr("class","Parent_slider");
                      
                    var slider2 = sliderDiv.append("div")
                        .attr("id","slider2")
                        .attr("class","Parent_slider");
                      
                    var slider3 = sliderDiv.append("div")
                        .attr("id","slider3")
                        .attr("class","Parent_slider");
                      
                    var slider4 = sliderDiv.append("div")
                        .attr("id","slider4")
                        .attr("class","Parent_slider");
                      
                    var slider5 = sliderDiv.append("div")
                        .attr("id","slider5")
                        .attr("class","Parent_slider");
                      
                    var slider6 = sliderDiv.append("div")
                        .attr("id","slider6")
                        .attr("class","Parent_slider");
                      
                    var slider7 = sliderDiv.append("div")
                        .attr("id","slider7")
                        .attr("class","Parent_slider");
                      
                    var slider8 = sliderDiv.append("div")
                        .attr("id","slider8")
                        .attr("class","Parent_slider");
                      
                    
                      generateData();
                      $( function() {
                        var slider1 = $('#slider1').slider({    
                            min: 0,
                            max: 500,
                            range: "min",
                            step : 0.1,
                            value: 200,
                            change: function(event, ui1) { 
                                 
                                $('#input_slider1').val(ui1.value);
                                ACCELERATED_GROWTH = ui1.value;
                                generateData();
                                
                            } 
                        });
                        $("<div id='ACCELERATED_GROWTH' class='slider_name'>ACCELERATED GROWTH :</div>").insertBefore('#slider1');
                        $("<div id='text_slider1'><input type='number' class='textBox' id='input_slider1' name='slider_value' min='0' max='500' step='0.1' value='200'></input></div>").insertAfter('#ACCELERATED_GROWTH');
                        $('#slider1,#ACCELERATED_GROWTH,#text_slider1').wrapAll('<div class="Parent_container"></div>');
                        $("#input_slider1").change(function() {
                            var $this = $(this);
                            var sliderValue = $this.val();
                            var min = $('#slider1').slider("option", "min");
                            var max = $('#slider1').slider("option", "max");
                            if(parseInt(sliderValue)>=min && parseInt(sliderValue)<=max){
                                $('#slider1').slider( "option", "value", $this.val() );
                            }
                            else{
                                
                                var preValue = $('#slider1').slider("option", "value");
                                 $('#input_slider1').val(preValue);
                            }
                        });
                       
                        
                        var slider2 = $('#slider2').slider({    
                            min: 0,
                            max: 500,
                            range: "min",
                            step : 0.1,
                            value: 100,
                            change: function(event, ui2) {
                                $('#input_slider2').val(ui2.value);
                                NORMAL_GROWTH = ui2.value;
                                generateData();
                                
                            } 
                        });
                        $("<div id='NORMAL_GROWTH' class='slider_name'>NORMAL GROWTH :</div>").insertBefore('#slider2');
                        $("<div id='text_slider2'><input type='number' id='input_slider2' class='textBox' name='slider_value' min='0' max='500' step='0.1' value='100'></input></div>").insertAfter('#NORMAL_GROWTH');
                        $('#slider2,#NORMAL_GROWTH,#text_slider2').wrapAll('<div class="Parent_container"></div>');
                        $("#input_slider2").change(function() {
                            var $this = $(this);
                            var sliderValue = $this.val();
                            var min = $('#slider2').slider("option", "min");
                            var max = $('#slider2').slider("option", "max");
                            if(parseInt(sliderValue)>=min && parseInt(sliderValue)<=max){
                                $('#slider2').slider( "option", "value", $this.val() );
                            }
                            else{
                                var preValue = $('#slider2').slider("option", "value");
                                 $('#input_slider2').val(preValue);
                            }
                        });
                       
                        var slider3 = $('#slider3').slider({    
                            min: 0,
                            max: 500,
                            range: "min",
                            step : 0.1,
                            value: 10,
                            change: function(event, ui3) { 
                                $('#input_slider3').val(ui3.value);
                                FLAT = ui3.value;
                                generateData();
                            }                 
                        });   
                        $("<div id='FLAT' class='slider_name'>FLAT :</div>").insertBefore('#slider3');
                        $("<div id='text_slider3'><input type='number' id='input_slider3' class='textBox' name='slider_value' min='0' max='500' step='0.1' value='10'></input></div>").insertAfter('#FLAT');
                        $('#slider3,#FLAT,#text_slider3').wrapAll('<div class="Parent_container"></div>');
                        $("#input_slider3").change(function() {
                            var $this = $(this);
                            var sliderValue = $this.val();
                            var min = $('#slider3').slider("option", "min");
                            var max = $('#slider3').slider("option", "max");
                            if(parseInt(sliderValue)>=min && parseInt(sliderValue)<=max){
                                $('#slider3').slider( "option", "value",$this.val() );
                            }
                            else{
                                var preValue = $('#slider3').slider("option", "value");
                                 $('#input_slider3').val(preValue);
                            }
                        });
                       
                        var slider4 = $('#slider4').slider({    
                            min: 0,
                            max: 500,
                            range: "min",
                            step : 0.1,
                            value: 25,
                            change: function(event, ui4) { 
                                $('#input_slider4').val(ui4.value);
                                NORMAL_DECAY = ui4.value;
                                generateData();
                            } 
                        });
                        $("<div id='NORMAL_DECAY' class='slider_name'>NORMAL DECAY :</div>").insertBefore('#slider4');
                        $("<div id='text_slider4'><input type='number' id='input_slider4' class='textBox' name='slider_value' min='0' max='500' step='0.1' value='25'></input></div>").insertAfter('#NORMAL_DECAY');
                        $('#slider4,#NORMAL_DECAY,#text_slider4').wrapAll('<div class="Parent_container"></div>');
                        $("#input_slider4").change(function() {
                            var $this = $(this);
                            var sliderValue = $this.val();
                            var min = $('#slider4').slider("option", "min");
                            var max = $('#slider4').slider("option", "max");
                            if(parseInt(sliderValue)>=min && parseInt(sliderValue)<=max){
                                $('#slider4').slider( "option", "value",$this.val());
                            }
                            else{
                                var preValue = $('#slider4').slider("option", "value");
                                 $('#input_slider4').val(preValue);
                            }
                        });
                       
                        var slider5 = $('#slider5').slider({    
                            min: 0,
                            max: 1000,
                            range: "min",
                            step : 0.1,
                            value: 500,
                            change: function(event, ui5) { 
                                $('#input_slider5').val(ui5.value);
                                ACCELERATED_DECAY = ui5.value;
                                generateData();
                            } 
                        });
                        $("<div id='ACCELERATED_DECAY' class='slider_name'>ACCELERATED DECAY :</div>").insertBefore('#slider5');
                        $("<div id='text_slider5'><input type='number' class='textBox' id='input_slider5' name='slider_value' min='0' max='1000' step='0.1' value='500'></input></div>").insertAfter('#ACCELERATED_DECAY');
                        $('#slider5,#ACCELERATED_DECAY,#text_slider5').wrapAll('<div class="Parent_container"></div>');
                        $("#input_slider5").change(function() {
                            var $this = $(this);
                            var sliderValue = $this.val();
                            var min = $('#slider5').slider("option", "min");
                            var max = $('#slider5').slider("option", "max");
                            if(parseInt(sliderValue)>=min && parseInt(sliderValue)<=max){
                                $('#slider5').slider( "option", "value", $this.val() );
                            }
                            else{
                                var preValue = $('#slider5').slider("option", "value");
                                 $('#input_slider5').val(preValue);
                            }
                        });
                    
                        var slider6 = $('#slider6').slider({    
                            min: 0,
                            max: 100,
                            range: "min",
                            step : 0.1,
                            value: 0.5,
                            change: function(event, ui6) { 
                                $('#input_slider6').val(ui6.value);
                                MINOR_CHANGE = ui6.value;
                                generateData();
                            }
                        });
                        $("<div id='MINOR_CHANGE' class='slider_name'>MINOR CHANGE :</div>").insertBefore('#slider6');
                        $("<div id='text_slider6'><input type='number' class='textBox' id='input_slider6' name='slider_value' min='0' max='100' step='0.1' value='0.5'></input></div>").insertAfter('#MINOR_CHANGE');
                        $('#slider6,#MINOR_CHANGE,#text_slider6').wrapAll('<div class="Parent_container"></div>');
                        $("#input_slider6").change(function() {
                            var $this = $(this);
                            var sliderValue = $this.val();
                            var min = $('#slider6').slider("option", "min");
                            var max = $('#slider6').slider("option", "max");
                            if(parseInt(sliderValue)>=min && parseInt(sliderValue)<=max){
                                $('#slider6').slider( "option", "value",$this.val() );
                            }
                            else{
                                var preValue = $('#slider6').slider("option", "value");
                                 $('#input_slider6').val(preValue);
                            }
                        });
                    
                        var slider7 = $('#slider7').slider({    
                            min: 0,
                            max: 100,
                            range: "min",
                            step : 0.1,
                            value: 1.5,
                            change: function(event, ui7) {
                                $('#input_slider7').val(ui7.value);
                                MEDIUM_CHANGE = ui7.value;
                                generateData();
                            }
                        });
                        $("<div id='MEDIUM_CHANGE' class='slider_name'>MEDIUM CHANGE :</div>").insertBefore('#slider7');
                        $("<div id='text_slider7'><input type='number' class='textBox' id='input_slider7' name='slider_value' min='0' max='100' step='0.1' value='1.5'></input></div>").insertAfter('#MEDIUM_CHANGE');
                        $('#slider7,#MEDIUM_CHANGE,#text_slider7').wrapAll('<div class="Parent_container"></div>');
                       $("#input_slider7").change(function() {
                            var $this = $(this);
                            var sliderValue = $this.val();
                            var min = $('#slider7').slider("option", "min");
                            var max = $('#slider7').slider("option", "max");
                            if(parseInt(sliderValue)>=min && parseInt(sliderValue)<=max){
                                $('#slider7').slider( "option", "value",$this.val() );
                            }
                            else{
                                var preValue = $('#slider7').slider("option", "value");
                                 $('#input_slider7').val(preValue);
                            }
                        });
                    
                        var slider8 = $('#slider8').slider({    
                            min: 0,
                            max: 100,
                            range: "min",
                            step : 0.1,
                            value: 5,
                            change: function(event, ui8) { 
                                $('#input_slider8').val(ui8.value);
                                MAJOR_CHANGE = ui8.value;
                                generateData();
                            } 
                        });
                        $("<div id='MAJOR_CHANGE' class='slider_name'>MAJOR CHANGE :</div>").insertBefore('#slider8');
                        $("<div id='text_slider8'><input type='number' class='textBox' id='input_slider8' name='slider_value' min='0' max='100' step='0.1' value='5'></input></div>").insertAfter('#MAJOR_CHANGE');
                        $('#slider8,#MAJOR_CHANGE,#text_slider8').wrapAll('<div id = "lastSlider" class="Parent_container"></div>');
                        $("#input_slider8").change(function() {
                            var $this = $(this);
                            var sliderValue = $this.val();
                            var min = $('#slider8').slider("option", "min");
                            var max = $('#slider8').slider("option", "max");
                            if(parseInt(sliderValue)>=min && parseInt(sliderValue)<=max){
                                $('#slider8').slider( "option", "value", $this.val() );
                            }
                            else{
                                var preValue = $('#slider8').slider("option", "value");
                                 $('#input_slider8').val(preValue);
                            }
                        });
                        $("<button type='button' id='reset' class='buttons btn btn-default'>RESET</button>").insertAfter("#lastSlider");
                        $("<button type='button' id='save' class='buttons btn btn-default'>SAVE</button>").insertAfter("#reset");
                      //  $("<button type='button' id='export' class='buttons btn btn-default'>EXPORT</button>").insertAfter("#reset");
                      $('#reset,#save').wrapAll('<div class="Action_Buttons"></div>');
                        $("#reset").click(function(){
                            
                            ACCELERATED_GROWTH = 200;
                            NORMAL_GROWTH = 100;
                            FLAT = 10;
                            NORMAL_DECAY = 25;
                            ACCELERATED_DECAY = 500;
                            MINOR_CHANGE = 0.5;
                            MEDIUM_CHANGE = 1.5;
                            MAJOR_CHANGE = 5;
                            $('#slider1').slider( "option", "value",ACCELERATED_GROWTH );
                            $('#slider2').slider( "option", "value",NORMAL_GROWTH );
                            $('#slider3').slider( "option", "value",FLAT );
                            $('#slider4').slider( "option", "value",NORMAL_DECAY );
                            $('#slider5').slider( "option", "value",ACCELERATED_DECAY);
                            $('#slider6').slider( "option", "value",MINOR_CHANGE  );
                            $('#slider7').slider( "option", "value",MEDIUM_CHANGE  );
                            $('#slider8').slider( "option", "value",MAJOR_CHANGE );
                        });
                     
                     
                     $("#save").click(function(){
                            temp_mainItem = values;
                            console.log("SAVING------");
                            console.log(temp_mainItem);
                      });
                     
                     /*$("#export").click(function(){
                    	 var data1 = JSON.stringify(temp_mainItem);
                    	 $.ajax({
                 		    url: resourceURL2,
                 		    type: "POST",
                 		    data:{ data1 : data1},
                 		   // contentType: "application/json",
                 		    success: function(response){
                 		    	console.log("response of downloadable excel sheet:"+response);
                 		    	 alert('Export completed');
                             }
                 		});
                    });*/
                    
                        
                    });
                    
                      
                      
                    function generateData(){
                        id=0; id1=0;k=0;
                         formulaForMinor = MINOR_CHANGE/3;
                         formulaForMedium = (MEDIUM_CHANGE*2)/3;
                         formulaForMajor = (MAJOR_CHANGE*3)/3;
                        values = [];
                        item={};
                        item['id']=id;
                        item['name']="";
                        item['date']="01/01/2009";
                        item['user']=0;
                        item['scale']="";
                        item['impact']="";
                        item['image']="";
                        item['ShortDescription']="";
						item['FullDescription']="";
						item['PrimaryDisruption']="";
						item['SecondaryDisruption']="";
						item['ImpactonSecondary']="";
						item['ScaleonSecondary']="";
						item['Source']="";
						item['SourceReliability']="";
						item['Likelihood']="";
						item['DateofInitialUptake']="";
						item['TimetoMainstreamUptake']="";
						item['TroughTimeframe']="";
						item['Type']="";
						item['Scale']="";
						item['Sector']="";
						item['DownstreamCascades2']="";
                        values.push(item);
                        
                        data.forEach(function(d){
                            id++;
                            
                            if(id==1){
                                item={};
                                item['id']=id;
                                item['name']=d.Name;
                                item['date']=d.InventionDate;
                                item['user']=10000;
                                item['scale']=d.ScaleonPrimary;
                                item['impact']=d.ImpactonPrimary;
                                item['image']=d.ImageURL;
                                item['ShortDescription']=d.ShortDescription;
								item['FullDescription']=d.FullDescription;
								item['PrimaryDisruption']=d.PrimaryDisruption;
								item['SecondaryDisruption']=d.SecondaryDisruption;
								item['ImpactonSecondary']=d.ImpactonSecondary;
								item['ScaleonSecondary']=d.ScaleonSecondary;
								item['Source']=d.Source;
								if(d.SourceReliability == undefined){
									item['SourceReliability']="";
								}
								else{
									item['SourceReliability']=d.SourceReliability;
								}
								item['Likelihood']=d.Likelihood;
								item['DateofInitialUptake']=d.DateofInitialUptake;
								item['TimetoMainstreamUptake']=d.TimetoMainstreamUptake;
								item['Type']=d.Type;
								item['Scale']=d.Scale;
								item['Sector']=d.Sector;
								item['DownstreamCascades2']=d.DownstreamCascades2;
                                values.push(item);
                            }  
                            else{
                                //id1 = id-1;
                                value = values[id-1].user;
                                impact = values[id-1].impact;
                                scale = values[id-1].scale;
                                firstDate = date(values[id-1].date);
                                secondDate = date(d.InventionDate);
                                diffDays = Math.round(Math.abs((firstDate - secondDate)/(oneDay)));
                                calculateValue(value);
                                isYearChange(firstDate,secondDate);
                                item={};
                                item['id']=id;
                                item['name']=d.Name;
                                item['date']=d.InventionDate;
                                item['user']=Math.floor(value);
                                item['scale']=d.ScaleonPrimary;
                                item['impact']=d.ImpactonPrimary;
                                item['image']=d.ImageURL;
                                item['ShortDescription']=d.ShortDescription;
								item['FullDescription']=d.FullDescription;
								item['PrimaryDisruption']=d.PrimaryDisruption;
								item['SecondaryDisruption']=d.SecondaryDisruption;
								item['ImpactonSecondary']=d.ImpactonSecondary;
								item['ScaleonSecondary']=d.ScaleonSecondary;
								item['Source']=d.Source;
								if(d.SourceReliability == undefined){
									item['SourceReliability']="";
								}
								else{
									item['SourceReliability']=d.SourceReliability;
								}
								item['Likelihood']=d.Likelihood;
								item['DateofInitialUptake']=d.DateofInitialUptake;
								item['TimetoMainstreamUptake']=d.TimetoMainstreamUptake;
								item['Type']=d.Type;
								item['Scale']=d.Scale;
								item['Sector']=d.Sector;
								item['DownstreamCascades2']=d.DownstreamCascades2;
                                values.push(item);
                                
                        
                            }
                     
                    });
                            console.log("------------------>"+values);
                           drawGraph();
                        
                    }
                      function calculateValue(val){
                        
                        
                          value = val;
                          if(scale=="Minor"){
                                    if(impact=="Positive"){
                                        for(var i=0;i<diffDays;i++){   
                                            value = value+(((value*formulaForMinor)/100)/365);  
                                        }
                                    }
        			                 else if(scale=="Negative"){
                                         for(var i=0;i<diffDays;i++){    
                                            value = value-(((value*formulaForMinor)/100)/365);    
                                        }
                                     }
        			                 else{
                                         value = value;
                                     }
        		                  }
        		                  else if(scale == "Medium"){
        			                 if(impact=="Positive"){
                                    for(var i=0;i<diffDays;i++){
                                            value = value+(((value*formulaForMedium)/100)/365);    
                                        }
                                     }
        			                 else if(scale=="Negative"){
                                         for(var i=0;i<diffDays;i++){
                                            value = value-(((value*formulaForMedium)/100)/365);  
                                        }
                                     }
                                    else{
                                         value = value;
                                     }
                                }
        		                  else{
        			                 if(impact=="Positive"){
                                         for(var i=0;i<diffDays;i++){
                                            value = value+(((value*formulaForMajor)/100)/365);     
                                        }
                                     }
        			                 else if(scale=="Negative"){
                                         for(var i=0;i<diffDays;i++){
                                            value = value-(((value*formulaForMajor)/100)/365);      
                                        }    
                                    }
                                     else{
                                         value = value;
                                     }
        		                  }
                           }
                        function isYearChange(firstDate,secondDate){
                                if(firstDate.getFullYear() == secondDate.getFullYear()){
                                      k++;
                                      if(k==1){
                                          startIndex=id-1;
                                          state = 0;
                                      }
                                      if(id==data.length){
                                          endIndex = 0;
                                          updateValues();
                                      }
                                  }
                                  else{
                                      k=0;      
                                      endIndex=id-1;
                                      state++;
                                      updateValues();
                                  }
                        }
                        function updateValues(){
                                        if(state>1){
                                          if((id-1)!=1){
                                           
                                              var init = values[endIndex].user;
                                              var val = init - ((init * 10)/100);
                                            
                                              reCalculate(val);
                                            }
                                        }
                                        else if(endIndex==0){
                                            start = startIndex;
                                            end = id;
                                           isLast = 1;
                                        }
                                        else{
                                            if((id-1)!=1){
                                            var val;
                                            var init1 = values[startIndex].user;
                                            var end1 = values[endIndex].user;
                                            
                                            if(init1 <=end1){
                                                var a = (end1*100)/init1;
                                                a = a-100;
                                if(a>=10){
                                    for(var i=startIndex;i<=endIndex;i++){
                                        if(i!=endIndex){
                                           
                                        diffDays1 = Math.round(Math.abs((date(values[i].date) - date(values[i+1].date))/(oneDay)));
                                        for(var j=0;j<diffDays1;j++){
                                            values[i].user = values[i].user + (((values[i].user*ACCELERATED_GROWTH)/100)/365);
                                        }
                                        
                                        if(i==endIndex-1){
                                            for(var j=0;j<diffDays1;j++){
                                                values[i+1].user = values[i+1].user + (((values[i+1].user*ACCELERATED_GROWTH)/100)/365);
                                                val = values[i+1].user;
                                            }
                                        }
                                        }
                                    }
                                    reCalculate(val);
                                }
                                else if(a>=5 && a<10){
                                    
                                    for(var i=startIndex;i<=endIndex;i++){
                                        if(i!=endIndex){
                                        diffDays1 = Math.round(Math.abs((date(values[i].date) - date(values[i+1].date))/(oneDay)));
                                        for(var j=0;j<diffDays1;j++){
                                            values[i].user = values[i].user + (((values[i].user*NORMAL_GROWTH)/100)/365);
                                        }
                                        if(i==endIndex-1){
                                            for(var j=0;j<diffDays1;j++){
                                                values[i+1].user = values[i+1].user + (((values[i+1].user*NORMAL_GROWTH)/100)/365);
                                                val = values[i+1].user;
                                            }
                                        }
                                        }
                                    }
                                    reCalculate(val);
                                }
                                else{
                                    
                                    for(var i=startIndex;i<=endIndex;i++){
                                        if(i!=endIndex){
                                        diffDays1 = Math.round(Math.abs((date(values[i].date) - date(values[i+1].date))/(oneDay)));
                                        for(var j=0;j<diffDays1;j++){
                                            values[i].user = values[i].user - (((values[i].user*FLAT)/100)/365);
                                        }
                                        if(i==endIndex-1){
                                            for(var j=0;j<diffDays1;j++){
                                                values[i+1].user = values[i+1].user - (((values[i+1].user*FLAT)/100)/365);
                                                val = values[i+1].user;
                                            }
                                        }
                                        }
                                    }
                                    reCalculate(val);
                                }
                            }
                             else{
                                var a = (end1*100)/init1;
                                a = 100-a;
                                if(a>=10){
                                    
                                    for(var i=startIndex;i<=endIndex;i++){
                                        if(i!=endIndex){
                                        diffDays1 = Math.round(Math.abs((date(values[i].date) - date(values[i+1].date))/(oneDay)));
                                        for(var j=0;j<diffDays1;j++){
                                            values[i].user = values[i].user - (((values[i].user*ACCELERATED_DECAY)/100)/365);
                                            
                                        }
                                        if(i==endIndex-1){
                                            for(var j=0;j<diffDays1;j++){
                                                values[i+1].user = values[i+1].user - (((values[i+1].user*ACCELERATED_DECAY)/100)/365);
                                                val = values[i+1].user;
                                            }
                                        }
                                        }
                                    }
                                    reCalculate(val);
                                }
                                else if(a>=5 && a<10){
                                    
                                    for(var i=startIndex;i<=endIndex;i++){
                                        if(i!=endIndex){   
                                            diffDays1 = Math.round(Math.abs((date(values[i].date) - date(values[i+1].date))/(oneDay)));
                                            for(var j=0;j<diffDays1;j++){
                                                values[i].user = values[i].user - (((values[i].user*NORMAL_DECAY)/100)/365);
                                            }
                                        
                                        if(i==endIndex-1){
                                            for(var j=0;j<diffDays1;j++){
                                                values[i+1].user = values[i+1].user - (((values[i+1].user*NORMAL_DECAY)/100)/365);
                                                val = values[i+1].user;
                                            }
                                        }
                                    }
                                    }
                                    reCalculate(val);
                                }
                                else{
                                    
                                    for(var i=startIndex;i<=endIndex;i++){
                                        if(i!=endIndex){
                                            diffDays1 = Math.round(Math.abs((date(values[i].date) - date(values[i+1].date))/(oneDay)));
                                            for(var j=0;j<diffDays1;j++){
                                                values[i].user = values[i].user - (((values[i].user*FLAT)/100)/365);
                                            }
                                            if(i==endIndex-1){
                                                for(var j=0;j<diffDays1;j++){
                                                    values[i+1].user = values[i+1].user - (((values[i+1].user*FLAT)/100)/365);
                                                    val = values[i+1].user;
                                                }
                                            }
                                        }
                                    }
                                    reCalculate(val);
                                }
                            }
                                    
                                            } }
                                    }
                        function reCalculate(val){
                                        value = val;
                                        if(scale=="Minor"){
                                    if(impact=="Positive"){
                                        for(var i=0;i<diffDays;i++){   
                                            value = value+((value*formulaForMinor)/100);  
                                        }
        			                 }
        			                 else if(scale=="Negative"){
                                         var changes = (value*formulaForMinor)/100;
                                         for(var i=0;i<diffDays;i++){
                                            value = value-((value*formulaForMinor)/100);    
                                        }
                                      }
        			                 else{
                                         value = value;
        			                 }
        		                  }
        		                  else if(scale == "Medium"){
        			                 if(impact=="Positive"){
                                         for(var i=0;i<diffDays;i++){
                                            value = value+((value*formulaForMedium)/100);    
                                        }
                                        }
        			                 else if(scale=="Negative"){
        				                 for(var i=0;i<diffDays;i++){
                                            value = value-((value*formulaForMedium)/100);  
                                        }
                                    }
                                    else{
        				                 value = value;
        			                 }
        		                  }
        		                  else{
        			                 if(impact=="Positive"){
                                         for(var i=0;i<diffDays;i++){
                                            value = value+((value*formulaForMajor)/100);     
                                        }
                                    }
        			                 else if(scale=="Negative"){
                                         var changes = (value*formulaForMinor)/100;
                                         for(var i=0;i<diffDays;i++){
                                            value = value-((value*formulaForMajor)/100);      
                                        }
                                    }
                                     else{
        				                 value = value;
        			                 }
        		                  }
                                }
                        
                      
                        var width = 900,
                       
                      height = 600,
                       
                      margin = {
                        bottom: 50,
                        left: 50,
                        top: 50,
                        right: 50
                      };
                   
                    var scaleX = d3.time
                        .scale()
                      
                        .domain([new Date('01/01/2009'), new Date('01/01/2050')])
                        .range([70, width - 50]);
                    
                     var scaleY = d3.scale.linear()
                        .range([height-50,70])
                        .domain([0,100000000]);
                    
                    function date(x) {
                      return new Date(x);
                    }
                     
                  
                            
                    var svg = chartContainer
                      .append('svg')
                      .style('height', height)
                      .style('width', width);
                 chart = null;
                      
                   function drawGraph(){
                       
                    nv.addGraph(function() {
                        
                     
                        
                        if(isLast==1){
                            var init =  values[start].user;
                            var end1 = values[end].user;

                            if(init <=end1){
                                var a = (end1*100)/init;
                                a = a-100;
                                if(a>=10){
                                    for(var i=start;i<=end;i++){
                                        if(i!=end){
                                            diffDays1 = Math.round(Math.abs((date(values[i].date) - date(values[i+1].date))/(oneDay)));
                                            for(var j=0;j<diffDays1;j++){
                                                values[i].user = values[i].user + (((values[i].user*ACCELERATED_GROWTH)/100)/365);
                                            }
                                        
                                        if(i==end-1){
                                            for(var j=0;j<diffDays1;j++){
                                                values[i+1].user = values[i+1].user + (((values[i+1].user*ACCELERATED_GROWTH)/100)/365);
                                            }
                                        }
                                        }
                                    }
                                }
                                else if(a>=5 && a<10){
                                    for(var i=start;i<=end;i++){
                                        if(i!=end){
                                        diffDays1 = Math.round(Math.abs((date(values[i].date) - date(values[i+1].date))/(oneDay)));
                                        for(var j=0;j<diffDays1;j++){
                                            values[i].user = values[i].user + (((values[i].user*NORMAL_GROWTH)/100)/365);
                                        }
                                        if(i==end-1){
                                            for(var j=0;j<diffDays1;j++){
                                                values[i+1].user = values[i+1].user + (((values[i+1].user*NORMAL_GROWTH)/100)/365);
                                            }
                                        }
                                        }
                                    }
                                }
                                else{
                                    for(var i=start;i<=end;i++){
                                        if(i!=end){
                                        diffDays1 = Math.round(Math.abs((date(values[i].date) - date(values[i+1].date))/(oneDay)));
                                        for(var j=0;j<diffDays1;j++){
                                            values[i].user = values[i].user - (((values[i].user*FLAT)/100)/365);
                                        }
                                        if(i==end-1){
                                            for(var j=0;j<diffDays1;j++){
                                                values[i+1].user = values[i+1].user - (((values[i+1].user*FLAT)/100)/365);
                                            }
                                        }
                                        }
                                    }
                                }
                            }
                             else{
                                var a = (end1*100)/init;
                                a = 100-a;
                                if(a>=10){
                                    for(var i=start;i<=end;i++){
                                        if(i!=end){
                                        diffDays1 = Math.round(Math.abs((date(values[i].date) - date(values[i+1].date))/(oneDay)));
                                        for(var j=0;j<diffDays1;j++){
                                            values[i].user = values[i].user - (((values[i].user*ACCELERATED_DECAY)/100)/365);
                                        }
                                        if(i==end-1){
                                            for(var j=0;j<diffDays1;j++){
                                                values[i+1].user = values[i+1].user - (((values[i+1].user*ACCELERATED_DECAY)/100)/365);
                                            }
                                        }
                                        }
                                    }
                                }
                                else if(a>=5 && a<10){
                                    for(var i=start;i<=end;i++){
                                        if(i!=end){   
                                            diffDays1 = Math.round(Math.abs((date(values[i].date) - date(values[i+1].date))/(oneDay)));
                                            for(var j=0;j<diffDays1;j++){
                                                values[i].user = values[i].user - (((values[i].user*NORMAL_DECAY)/100)/365);
                                            }
                                        
                                        if(i==end-1){
                                            for(var j=0;j<diffDays1;j++){
                                                values[i+1].user = values[i+1].user - (((values[i+1].user*NORMAL_DECAY)/100)/365);
                                            }
                                        }
                                    }
                                    }
                                }
                                else{
                                    for(var i=start;i<=end;i++){
                                        if(i!=end){
                                            diffDays1 = Math.round(Math.abs((date(values[i].date) - date(values[i+1].date))/(oneDay)));
                                            for(var j=0;j<diffDays1;j++){
                                                values[i].user = values[i].user - (((values[i].user*FLAT)/100)/365);
                                            }
                                            if(i==end-1){
                                                for(var j=0;j<diffDays1;j++){
                                                    values[i+1].user = values[i+1].user - (((values[i+1].user*FLAT)/100)/365);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            
                        }
                       
                        finalData = [];  
                        mainItem={};
                        mainItem['id'] = "blockchain";
                        mainItem['key'] = "Bitcoin Blockchain";
                        mainItem['values'] = values;
                        finalData.push(mainItem);
                        console.log(finalData);
                        var disruptions = [];
                        var disruptionObj = {};
                        values.forEach(function(d){
                            if(d.image!=""){
                                disruptionObj = {};
                                disruptionObj['id'] = d.id;
                                disruptionObj['name'] = d.name;
                                disruptionObj['image'] = d.image;
                                disruptionObj['date'] = d.date;
                                disruptionObj['user'] = d.user;
                                disruptions.push(disruptionObj);
                            }
                        });
                        console.log(disruptions);
                        mainItem={};
                            mainItem['disruptions'] = disruptions;
                            mainItem['data'] = finalData;
                        console.log(mainItem);
                       chart = null;
                      chart = nv.models.stackedAreaChart()
                        .margin({right: 100})
                        .x(function(d) { return date(d.date) })   //We can modify the data accessor functions...
                        .y(function(d) { return Math.floor(d.user); })   //...in case your data is formatted differently.
                        .useInteractiveGuideline(false)    //Tooltips which show all data points. Very nice!
                        .rightAlignYAxis(true)      //Let's move the y-axis to the right side.
                        .showControls(false)       //Allow user to choose 'Stacked', 'Stream', 'Expanded' mode.
                        .clipEdge(false).color(['327FB3','719CBC','C07D22','D8C190']);
                        /*chart.xScale(scaleX);
                        chart.yScale(scaleY);*/
                       chart.xAxis
                        .axisLabel('Year')
                        .tickFormat(function(d) {
                          return d3.time.format('%Y')(new Date(d));
                        });
                       
                      
                         chart.yAxis.axisLabel('No of users');
                      var minDate = date('01/01/2009');
                      var maxDate = date('01/01/2017');
                      chart.interpolate("monotone");
                      //chart.forceY([0,10000000]);
          	             chart.forceX([minDate,maxDate]);
                      svg
                        .datum(mainItem.data)
                        .style('shape-rendering', 'crispEdges')
                        .call(chart);
                      nv.utils.windowResize(chart.update);
                    
                    drawDiruptions();
                        
                         
                      return chart;
                    });
                      
                       
                       
                       
                       
                   }
                      
                      function drawDiruptions() {
                          if(trendItem!=null)
                              trendItem.remove();
                           var id2;
                          var newScaleX;
                          var newScaleY;
                         var x1,y1;
                          
                           var dragTooltip = chartContainer.append("div")
                                .attr("id","dragtooltip")
                                .attr("class","dragtooltip");
                          
                          d3.selectAll('#dragtooltip').remove();
                          
                          /*var tip =chartContainer.append("div")	
                                .attr("id", "nametooltip")
                                .attr("class", "nametooltip");
                          
                          d3.selectAll('#nametooltip').remove();*/
                         
                          var isDragged;
                       var draggable = d3.behavior.drag()
                     /*  .on('dragstart', function() {
                          
                           
                            d3.event.stopPropagation();
                            
                            
                        })*/
                        .on('drag', function() {
                            isDragged = true;
                          if ((d3.event.y >= 50 && d3.event.y <= height - 50) && (d3.event.x >= 50 && d3.event.x <= width -50)) {
                             
                           
                            id2 =  d3.select(this).attr("id");
                             
                            newScaleX = chart.xAxis.scale().invert(d3.event.x-50);
                            newScaleY = chart.yAxis.scale().invert(d3.event.y);
                         
                           d3.selectAll('#dragtooltip').remove();
                            dragTooltip.selectAll('*').remove();
                            dragTooltip = chartContainer.append("div")
                                .attr("id","dragtooltip")
                                .attr("class","dragtooltip");
                           /* dragTooltip.transition()		
                                .duration(0)		
                                .style("opacity", .9);*/
                           dragTooltip.html(d3.time.format('%m/%d/%Y')(new Date(newScaleX))+"<br><br>"+Math.floor(newScaleY));
                                /*.style("left", (d3.event.pageX-200) + "px") 		
                                .style("top", (d3.event.pageY+30) + "px");*/
                              
                            d3.select(this)
                              .attr('transform', 'translate(' + chart.xAxis.scale()(newScaleX) + ','+chart.yAxis.scale()(newScaleY)+')');
                          }
                        })
                       .on('dragend',function(){
                           if(isDragged){
                               
                           console.log(date(newScaleX));
                            updateChart(id2,newScaleY,newScaleX);
                            d3.selectAll('#dragtooltip').remove();
                           }
                        });
                      
                        function clicked(){
                           isDragged = false;
                         var popUp = chartContainer.append("div")
                                .attr("id","popup")
                                .attr("class","popup");
                        
                               d3.selectAll('#popup').remove();
                             popUp.selectAll('*').remove();
                              popUp = chartContainer.append("div")
                                .attr("id","popup")
                                .attr("class","popup")
                                .style("left", (d3.event.pageX-50) + "px") 		
                                .style("top", (d3.event.pageY+30) + "px");  
                   
                             $("#popup").show();
                             if (d3.event.defaultPrevented) return; 
                            id2 =  d3.select(this).attr("id");
                            for(var i=0;i<(mainItem.data[0].values).length;i++){
                                if(mainItem.data[0].values[i].id == id2){
                                    var data1 = mainItem.data[0].values[i];
                                     var popupCloseButton = popUp.append('button') .attr('id', 'popClose')
                                        .attr('class', 'close mr-sm mt-sm')
                                        .append('span')
                                        .html('&times;');
                                    $('#popClose').wrapAll("<div style='min-height:30px;'><div>");
                                    $('#popClose').click(function(){
                                        
                                        popUp.selectAll('*').remove();
                                        popUp.transition()		
                                            .duration(10)		
                                            .style("opacity", 0);    
                                    });
                                     var table = popUp.append("table").attr('class','Disrup_DataTable');
                                    var thead = table.append('thead')
                                    var tbody = table.append('tbody');
                                   var data1 = tbody.append('tr');
                                                data1.append('td')        
                                                .text("Id");
                                                data1.append('td')
                                                .text(mainItem.data[0].values[i].id);
                                    var data2 = tbody.append('tr');
                                                data2.append('td')        
                                                .text("Name");
                                                data2.append('td')
                                                .text(mainItem.data[0].values[i].name);
                                    var data3 = tbody.append('tr');
                                                data3.append('td')        
                                                .text("Date");
                                                data3.append('td')
                                                .text(mainItem.data[0].values[i].date);
                                    var data4 = tbody.append('tr');
                                                data4.append('td')        
                                                .text("User");
                                                data4.append('td')
                                                .text(Math.floor(mainItem.data[0].values[i].user));
                                    var data5 = tbody.append('tr');
                                                data5.append('td')        
                                                .text("scale");
                                                data5.append('td')
                                                .text(mainItem.data[0].values[i].scale);
                                    var data6 = tbody.append('tr');
                                                data6.append('td')        
                                                .text("impact");
                                                data6.append('td')
                                                .text(mainItem.data[0].values[i].impact);
                                    console.log(mainItem.data[0].values[i]);
                                    popUp.transition()		
                                        .duration(0)		
                                        .style("opacity", .9)
                                        .style("left", (d3.event.pageX-200) + "px") 		
                                        .style("top", (d3.event.pageY+30) + "px");
                                    break;
                                }
                            }
                              
                          
                        }
                      function wrap(text, width) {
                        text.each(function() {
                          var text = d3.select(this),
                            words = text.text().split(/\s+/).reverse(),
                            word,
                            line = [],
                            lineNumber = 0,
                            lineHeight = 1.1,
                            y = text.attr('y'),
                            dy = parseFloat(text.attr('dy'));

                          var  tspan = text.text(null)
                            .append('tspan')
                            .classed('visible-lg', true)
                            .attr('x', 18)
                            .attr('text-anchor', 'middle')
                            .attr('y', y)
                            .attr('dy', dy + 'em');

                          while (word = words.pop()) {
                            line.push(word);
                            tspan.text(line.join(' d'));
                            if (tspan.node().getComputedTextLength() > width) {
                              line.pop();
                              tspan.text(line.join(' '));
                              line = [word];
                              tspan = text.append('tspan')
                                .attr('x', 18)
                                .attr('text-anchor', 'middle')
                                .attr('y', y)
                                .attr('dy', ++lineNumber * lineHeight + dy + 'em')
                                .text(word);
                            }
                          }
                        });
                      }
                    
                      
                        
                         trendItem = svg
                        .selectAll('svg')
                        .data(mainItem.disruptions)
                        .enter()
                        .append('g')
                        .attr('fill', 'white')//function(d) { return d.is_positive ? 'green': 'red'; })
                        .attr('id', function(d) {return d.id; })
                        .attr('transform', function(d) {
                        
                                for(var j in mainItem.data[0].values){
                                    if(d.id == mainItem.data[0].values[j].id){
                                       /* var x = scaleX(date(data.data[0].values[j].x))+50;
                                        var y = scaleY(data.data[0].values[j].y);*/
                                           x1 = chart.xAxis.scale()(date(mainItem.data[0].values[j].date))+50;
                                          y1 = chart.yAxis.scale()(mainItem.data[0].values[j].user);
                                    }
                                }

                            return 'translate(' + x1 + ','+y1+')';
                        }).style('cursor', 'pointer')
                        .call(draggable)
                        .on('mouseup',clicked); 
                        
                        
                      trendItem.append('image')
                        .attr('xlink:href', function(d) {return svgPath+d.image;})
                        .attr('width', 36)
                        .attr('height', 36)
                        .on('mouseover',function(d){
                            tip = chartContainer.append("div")
                                .attr("id","nametooltip")
                                .attr("class","nametooltip");
                            
                            tip.transition()		
                                .duration(0)		
                                .style("opacity", .9);
                           tip.html(d.name)
                                .style("left", (d3.event.pageX-200) + "px") 		
                                .style("top", (d3.event.pageY+25) + "px");
                        })
                        .on("mouseout", function(d) {
                            /*tip.selectAll('*').remove();
                           	tip.transition()		
                                .duration(10)		
                                .style("opacity", 0);*/
                          d3.selectAll('#nametooltip').remove();
                        });
                        
                      /*trendItem.append('text')
                        .text(function(d) {return d.name;})
                        .attr('x', 0)
                        .attr('y', 38)
                        .attr('dy', 1)
                        .call(wrap, 140);*/
                      }
                    function updateChart(id2,newScaleY,newScaleX) {
                       var flag = true;
                        for (var j=0;j< (mainItem.data[0].values).length;j++) {
                            if(flag){
                            var id3 = mainItem.data[0].values[j].id;
                                if(id3 == id2){
                                    mainItem.data[0].values[j].date = d3.time.format('%m/%d/%Y')(new Date(newScaleX)); 
                                   var new_date1 = date(mainItem.data[0].values[j].date);
                                    var next_date = date(mainItem.data[0].values[j+1].date);
                                    var pre_date1 = date(mainItem.data[0].values[j-1].date);
                                    console.log(new_date1);
                                    console.log(next_date);
                                    console.log(pre_date1);
                                    if(new_date1 < next_date){
                                        for(m=j;m>=0;m--){
                                            console.log(mainItem.data[0].values[m].date);
                                            var new_date = date(mainItem.data[0].values[m].date);
                                            var pre_date = date(mainItem.data[0].values[m-1].date);
                                            if(new_date < pre_date){
                                                var temp = mainItem.data[0].values[m-1];
                                                mainItem.data[0].values[m-1] = mainItem.data[0].values[m];
                                                mainItem.data[0].values[m] = temp;
                                                mainItem.data[0].values[m-1].id--; 
                                                mainItem.data[0].values[m].id++;
                                                id2 = mainItem.data[0].values[m].id;
                                            }
                                            else{
                                                flag= false;
                                                break;
                                            }
                                        }
                            
                                }
                                else{
                                    for(m=j;m<(mainItem.data[0].values).length-1;m++){
                                         console.log(mainItem.data[0].values[m].date);
                                         var new_date = date(mainItem.data[0].values[m].date);
                                            var pre_date = date(mainItem.data[0].values[m+1].date);
                                            if(new_date > pre_date){
                                                var temp = mainItem.data[0].values[m+1];
                                                mainItem.data[0].values[m+1] = mainItem.data[0].values[m];
                                                mainItem.data[0].values[m] = temp;
                                                mainItem.data[0].values[m+1].id++; 
                                                mainItem.data[0].values[m].id--;
                                                id2 = mainItem.data[0].values[m].id;
                                            }
                                            else{
                                                flag= false;
                                                break;
                                            }
                                    }
                                }
                                }
                        }
                        }
                        console.log(mainItem);
                        var pre = Math.floor(newScaleY);
                        var values1 = [];
                        values = [];
                        id=0;
                        startIndex=0;
                        k=0;
                        state=0;
                        for (var i=0;i<(mainItem.data).length;i++) {
                            
                            for (var j=0;j< (mainItem.data[i].values).length;j++) {
                                
                                var id3 = mainItem.data[i].values[j].id;
                               
                                if(id3<id2){
                                        if(id3==0){
                                            item={};
                                            item['id']=0;
                                            item['name']="";
                                            item['date']="01/01/2009";
                                            item['user']=0;
                                            item['scale']="";
                                            item['impact']="";
                                            item['image']="";
                                            item['ShortDescription']="";
                    						item['FullDescription']="";
                    						item['PrimaryDisruption']="";
                    						item['SecondaryDisruption']="";
                    						item['ImpactonSecondary']="";
                    						item['ScaleonSecondary']="";
                    						item['Source']="";
                    						item['SourceReliability']="";
                    						item['Likelihood']="";
                    						item['DateofInitialUptake']="";
                    						item['TimetoMainstreamUptake']="";
                    						item['TroughTimeframe']="";
                    						item['Type']="";
                    						item['Scale']="";
                    						item['Sector']="";
                    						item['DownstreamCascades2']="";
                                            values.push(item);
                                          }
                                        else{
                                            id++;
                                            item={};
                                            item['id']=id;
                                            item['name']=mainItem.data[i].values[j].name;
                                            item['date']=mainItem.data[i].values[j].date;
                                            item['user']=mainItem.data[i].values[j].user;
                                            item['scale']=mainItem.data[i].values[j].scale;
                                            item['impact']=mainItem.data[i].values[j].impact;
                                            item['image']=mainItem.data[i].values[j].image;
                                            item['ShortDescription']=mainItem.data[i].values[j].ShortDescription;
                    						item['FullDescription']=mainItem.data[i].values[j].FullDescription;
                    						item['PrimaryDisruption']=mainItem.data[i].values[j].PrimaryDisruption;
                    						item['SecondaryDisruption']=mainItem.data[i].values[j].SecondaryDisruption;
                    						item['ImpactonSecondary']=mainItem.data[i].values[j].ImpactonSecondary;
                    						item['ScaleonSecondary']=mainItem.data[i].values[j].ScaleonSecondary;
                    						item['Source']=mainItem.data[i].values[j].Source;
                    						item['SourceReliability']=mainItem.data[i].values[j].SourceReliability;
                    						item['Likelihood']=mainItem.data[i].values[j].Likelihood;
                    						item['DateofInitialUptake']=mainItem.data[i].values[j].DateofInitialUptake;
                    						item['TimetoMainstreamUptake']=mainItem.data[i].values[j].TimetoMainstreamUptake;
                    						item['TroughTimeframe']=mainItem.data[i].values[j].TroughTimeframe;
                    						item['Type']=mainItem.data[i].values[j].Type;
                    						item['Scale']=mainItem.data[i].values[j].Scale;
                    						item['Sector']=mainItem.data[i].values[j].Sector;
                    						item['DownstreamCascades2']=mainItem.data[i].values[j].DownstreamCascades2;
                                            values.push(item);
                                            
                                            }
                                    
                                }
                                 if(id3==id2){
                                    id++;
                                    state = 1;
                                    firstDate = date(mainItem.data[i].values[j+1].date);
                                    secondDate = date(mainItem.data[i].values[j].date);
                                    var z = j-1;
                                    if(firstDate.getFullYear() != secondDate.getFullYear()){
                                        k=1;
                                    }
                                    else{
                                        k=0;
                                    }
                                    if(k==1){
                                        firstDate = date(mainItem.data[i].values[j-1].date);
                                        secondDate = date(mainItem.data[i].values[j].date);
                                       
                                        if(firstDate.getFullYear() == secondDate.getFullYear()){
                                            state = 0;
                                            for(var m=z;m>0;m--){
                                               
                                                if(date(mainItem.data[i].values[m].date).getFullYear() != date(mainItem.data[i].values[m-1].date).getFullYear()){
                                                    startIndex=m;
                                                 
                                                 
                                                    break;
                                                    
                                                }
                                            }
                                            if(j== (mainItem.data[i].values).length-1){
                                                endIndex = 0;
                                                
                                                updateValues();       
                                            }
                                           
                                        }
                                        
                                        else{
                                            scale = values[j-1].scale;
                                            impact = values[j-1].impact;
                                            console.log(j-1);
                                            endIndex=j-1;
                                          
                                            state++;
                                            updateValues();
                                        }
                                    }   
                                    item={};
                                        item['id']=id;
                                        item['name']=mainItem.data[i].values[j].name;
                                        item['date']=mainItem.data[i].values[j].date;
                                        item['user']=pre;
                                        item['scale']=mainItem.data[i].values[j].scale;
                                        item['impact']=mainItem.data[i].values[j].impact;
                                        item['image']=mainItem.data[i].values[j].image;
                                        item['ShortDescription']=mainItem.data[i].values[j].ShortDescription;
                						item['FullDescription']=mainItem.data[i].values[j].FullDescription;
                						item['PrimaryDisruption']=mainItem.data[i].values[j].PrimaryDisruption;
                						item['SecondaryDisruption']=mainItem.data[i].values[j].SecondaryDisruption;
                						item['ImpactonSecondary']=mainItem.data[i].values[j].ImpactonSecondary;
                						item['ScaleonSecondary']=mainItem.data[i].values[j].ScaleonSecondary;
                						item['Source']=mainItem.data[i].values[j].Source;
                						item['SourceReliability']=mainItem.data[i].values[j].SourceReliability;
                						item['Likelihood']=mainItem.data[i].values[j].Likelihood;
                						item['DateofInitialUptake']=mainItem.data[i].values[j].DateofInitialUptake;
                						item['TimetoMainstreamUptake']=mainItem.data[i].values[j].TimetoMainstreamUptake;
                						item['TroughTimeframe']=mainItem.data[i].values[j].TroughTimeframe;
                						item['Type']=mainItem.data[i].values[j].Type;
                						item['Scale']=mainItem.data[i].values[j].Scale;
                						item['Sector']=mainItem.data[i].values[j].Sector;
                						item['DownstreamCascades2']=mainItem.data[i].values[j].DownstreamCascades2;
                                        values.push(item);
                                       
                                        
                                }
                                
                                if(id3>id2){
                                    id++;
                                   
                                    var pre_val = values[j-1].user;
                                    scale = values[j-1].scale;
                                    impact = values[j-1].impact;
                                 
                                    firstDate = date(mainItem.data[i].values[j-1].date);
                                    secondDate = date(mainItem.data[i].values[j].date);
                                    diffDays = Math.round(Math.abs((firstDate - secondDate)/(oneDay))); 
                                    calculateValue(pre_val);
                                    
                                    var z = j-1;
                                   
                                    if(firstDate.getFullYear() == secondDate.getFullYear()){
                                        state = 0;
                                        for(var m=z;m>0;m--){
                                           
                                            if(date(mainItem.data[i].values[m].date).getFullYear() != date(mainItem.data[i].values[m-1].date).getFullYear()){
                                                startIndex=m;
                                               
                                           
                                                break;
                                            }
                                        }
                                        if(j== (mainItem.data[i].values).length-1){
                                            endIndex = 0;
                                            updateValues();       
                                        }
                                       
                                       
                                    }
                                    else{
                                        endIndex=j-1;
                                        state++;
                                      
                                        updateValues();
                                    }
                                
                                    item={};
                                    item['id']=id;
                                    item['name']=mainItem.data[i].values[j].name;
                                    item['date']=mainItem.data[i].values[j].date;
                                    item['user']=Math.floor(value);
                                    item['scale']=mainItem.data[i].values[j].scale;
                                    item['impact']=mainItem.data[i].values[j].impact;
                                    item['image']=mainItem.data[i].values[j].image;
                                    item['ShortDescription']=mainItem.data[i].values[j].ShortDescription;
            						item['FullDescription']=mainItem.data[i].values[j].FullDescription;
            						item['PrimaryDisruption']=mainItem.data[i].values[j].PrimaryDisruption;
            						item['SecondaryDisruption']=mainItem.data[i].values[j].SecondaryDisruption;
            						item['ImpactonSecondary']=mainItem.data[i].values[j].ImpactonSecondary;
            						item['ScaleonSecondary']=mainItem.data[i].values[j].ScaleonSecondary;
            						item['Source']=mainItem.data[i].values[j].Source;
            						item['SourceReliability']=mainItem.data[i].values[j].SourceReliability;
            						item['Likelihood']=mainItem.data[i].values[j].Likelihood;
            						item['DateofInitialUptake']=mainItem.data[i].values[j].DateofInitialUptake;
            						item['TimetoMainstreamUptake']=mainItem.data[i].values[j].TimetoMainstreamUptake;
            						item['TroughTimeframe']=mainItem.data[i].values[j].TroughTimeframe;
            						item['Type']=mainItem.data[i].values[j].Type;
            						item['Scale']=mainItem.data[i].values[j].Scale;
            						item['Sector']=mainItem.data[i].values[j].Sector;
            						item['DownstreamCascades2']=mainItem.data[i].values[j].DownstreamCascades2;
                                    values.push(item); 
                                    }
                            }
                            
                    }
                        trendItem.remove();
                       drawGraph();
                        
                    
                 }
                  }  };
            
              return directive;
          }); 
        
        //timeLine
        App.directive('timeLine',function(){
         var directive ={};
         directive.restrict = 'EA';
         directive.scope = {};
         directive.link = function(scope, iElement, iAttrs){
             
          var xAxisDiv = d3.select(iElement[0]);

          // on window resize, re-render d3 canvas
          window.onresize = function() {
            return scope.$apply();
          };

          scope.$watch(function() {
              return angular.element(window)[0].innerWidth;
            }, function() {
              return scope.render();
            }
          );

          scope.render = function() {
            xAxisDiv.selectAll('*').remove();

            var width = angular.element('.axis-width').width();

            var scaleX = d3.time
              .scale()
              .domain([new Date('2016/01/01 00:00:00'), new Date('2020/01/01 00:00:00'), new Date('2050/01/01 00:00:00')])
              .range([70, width / 1.9, width]);

            var xAxis = d3.svg
              .axis()
              .scale(scaleX)
              .orient('bottom')
              .tickValues([new Date('2016/01/01 00:00:00'),
                          new Date('2017/01/01 00:00:00'),
                          new Date('2018/01/01 00:00:00'),
                          new Date('2019/01/01 00:00:00'),
                          new Date('2020/01/01 00:00:00'),
                          new Date('2025/01/01 00:00:00'),
                          new Date('2030/01/01 00:00:00'),
                          new Date('2035/01/01 00:00:00'),
                          new Date('2040/01/01 00:00:00'),
                          new Date('2045/01/01 00:00:00'),
                          new Date('2050/01/01 00:00:00')])
              .tickSize(0);

            xAxisDiv.append('div')
              .classed('x-axis col-md-10 col-md-offset-2 col-xs-10 col-xs-offset-2 col-sm-10 col-sm-offset-2 col-lg-10 col-lg-offset-2', true)
              .append('svg')
              .append('g')
              .call(xAxis)
              .attr('transform', 'translate(0,6)');
      };
             
         };
            return directive;  
     });