myAppModule.controller('LoginController',function($scope,$location,$auth,$mdToast, $localStorage) {
	
	$scope.$storage = $localStorage;
	
	if($auth.isAuthenticated()){
		$location.path('/add');
	};
	
	$scope.authenticate = function(provider) {
	      $auth.authenticate(provider)
	        .then(function(response) {
	        	$scope.$storage.user = response.data.user;
	        	console.log("You have successfully logged in");
	        	showToast("Successfully logged in with your "+provider +" account");
	        	$location.path('/add');
	        })
	        .catch(function(response) {
	        	showToast("Login Failure");
	          console.log(response);
	        });
	    };
	    
	    showToast = function(content) {
	        $mdToast.show(
	          $mdToast.simple()
	            .content(content)
	            .position($scope.getToastPosition())
	            .hideDelay(5000)
	        );
	      };
	      
      $scope.getToastPosition = function() {
    	    return Object.keys($scope.toastPosition)
    	      .filter(function(pos) { return $scope.toastPosition[pos]; })
    	      .join(' ');
    	  };
	  $scope.toastPosition = {
			    bottom: true,
			    top: false,
			    left: false,
			    right: true,
	  }
});
