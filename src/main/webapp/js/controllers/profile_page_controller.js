myAppModule.controller('ProfilePageController', function($scope, $http, $localStorage, $mdSidenav){
	$scope.user = $localStorage.user;
	$scope.sideMenu = [
	                   {
	                	   title: 'Jobs',
	                	   icon: 'work'
	                   },
	                   {
	                	   title: 'Messages',
	                	   icon: 'message'
	                   },
	                   {
	                	   title: 'Settings',
	                	   icon: 'settings'
	                   }
	                   ];
	
	$scope.content = "startup";
	
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