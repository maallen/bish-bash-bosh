myAppModule.controller('SignupController', function($scope, $auth, $mdToast,$location,$localStorage,$mdDialog) {
	
	$scope.$storage = $localStorage;
	
    $scope.signup = function() {
      $auth.signup({displayName: $scope.user.displayName,email: $scope.user.email,password: $scope.user.password})
      .then(function(response) {
	        	console.log("You have successfully logged in");
	        	showToast("Welcome! Account successfully registered for " + $scope.user.displayName );
	        	$scope.$storage.user = response.data.user;
	        	$location.path('/add');
	        }).catch(function(response) {
  	    $mdDialog.show(
  	    	$mdDialog.alert(response)
  	          .title('Sign up failure')
  	          .content("This email address "+ response.data.email + " is already registered")
  	          .ariaLabel('Sign up failure')
  	          .ok('OK')
  		    );
      });
    };
   
    
	 showToast = function(content) {
	        $mdToast.show(
	          $mdToast.simple()
	            .content(content)
	            .position($scope.getToastPosition())
	            .hideDelay(7000)
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
	  };
  });