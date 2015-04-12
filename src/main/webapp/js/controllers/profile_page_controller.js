myAppModule.controller('ProfilePageController', function($scope, $http, $localStorage, $mdSidenav){
	$scope.user = $localStorage.user;
	$scope.sideMenu = [
	                   {
	                	   link: '',
	                	   title: 'Open Jobs',
	                	   icon: 'work'
	                   },
	                   {
	                	   link: '',
	                	   title: 'Messages',
	                	   icon: 'message'
	                   },
	                   {
	                	   link: '',
	                	   title: 'Settings',
	                	   icon: 'settings'
	                   }
	                   ];
	
	$scope.content = "startup";
	
	$scope.click = function(item){
		$scope.content = "You clicked on " + item.title;
	};
});