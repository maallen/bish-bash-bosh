myAppModule.controller('ProfilePageController', function($scope, $http, $localStorage, SidebarService){

	$scope.user = $localStorage.user;
	$scope.sidebarItemService = SidebarService;
	$scope.sidebarItemService.addMenuItem({
 	   title: 'Open Jobs',
	   icon: 'work',
	   order: 1
	});
	$scope.sidebarItemService.addMenuItem({
	 	   title: 'Messages',
		   icon: 'message',
		   order: 2
	});
	$scope.sidebarItemService.addMenuItem({
	 	   title: 'Settings',
		   icon: 'settings',
		   order: 3
	});
	
	$scope.content = "Page Content";
	
	$scope.click = function(item){
		if(item.title === "Jobs"){
			$scope.jobsClicked();
		}else if(item.title === "Messages"){
			$scope.messagesClicked();
		}else if(item.title === "Settings"){
			$scope.settingsClicked();
		}
	};
	
	$scope.jobsClicked = function(){
		$scope.content = "You clicked on Jobs";
	};
	
	$scope.messagesClicked = function(){
		$scope.content = "You clicked on Messages";
	};
	
	$scope.settingsClicked = function(){
		$scope.content = "You clicked on Settings";
	};
});