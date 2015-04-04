myAppModule.controller('ProfilePageController', function($scope, $http, $localStorage){
	$scope.user = $localStorage.user;

});