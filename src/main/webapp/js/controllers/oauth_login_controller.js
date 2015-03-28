myAppModule.controller('LoginController',function($scope,$location,$rootScope,$http,$auth) {
	
	$scope.authenticate = function(provider) {
	      $auth.authenticate(provider)
	        .then(function() {
	        	console.log("You have successfully logged in");
	        	$location.path('/add');
	        })
	        .catch(function(response) {
	          console.log(response);
	        });
	    };

});
