myAppModule.controller('SidebarController', function($scope, $rootScope, $localStorage, $mdSidenav, SidebarService){
	
	$scope.sidebarItemService = SidebarService;
	
	$scope.user = $localStorage.user;
    
    $rootScope.$on('$stateChangeStart', function(event, toState, fromState, fromParams) {
        $scope.sidebarItemService.clearSidebarItems();
    });

    $scope.isDisabled = function(bool) {
        return bool;
    }
    
    $scope.toggleSidebar = function(){
    	$mdSidenav('sidenav-left').toggle().then(function(){
			console.log("Sidebar toggled");
    	});
    };
    
	$scope.click = function(item){
		console.log('You clicked on ' + item.title);
	};
	
	$rootScope.$on('loggedIn', function(){
		$scope.user = $localStorage.user;
	});
    
});

myAppModule.controller('SidenavController', function($scope, $rootScope, $timeout, $mdSidenav, $mdUtil){
//		
//	$scope.toggleSideBar = buildToggler('sidenav-left');
//	
//    function buildToggler(navID) {
//        var debounceFn =  $mdUtil.debounce(function(){
//              $mdSidenav(navID)
//                .toggle()
//                .then(function () {
//                  $log.debug("toggle " + navID + " is done");
//                });
//            },300);
//        return debounceFn;
//      }
//	
//	$scope.close = function(){
//		$mdSidenav('sidenav-left').close().then(function(){
//			console.log("Sidebar closed");
//		});
//	}
});