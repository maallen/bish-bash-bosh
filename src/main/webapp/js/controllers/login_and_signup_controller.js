myAppModule.controller('LoginController',function($scope,$location,$auth,$mdToast, $localStorage,$mdDialog) {
	
	$scope.$storage = $localStorage;
	
	if($auth.isAuthenticated()){
		$location.path('/add');
	};
	
	  $scope.login = function() {
	      /*$auth.login({ email: $scope.email, password: $scope.password })
	        .then(function() {
	        	console.log("You have successfully logged in");
	        	showToast("Successfully loged in with your account");
	        	$location.path('/add');
	        })
	        .catch(function(response) {
	        	showToast("Login Failure");
	        });*/
		  
		  $mdDialog.show(
		  	    	$mdDialog.alert()
		  	          .title('Login not implemented yet')
		  	          .content("Please try again soon :)")
		  	          .ariaLabel('Login Failure')
		  	          .ok('Try something else')
		  		    );
	    };
		
	    $scope.signup = function() {

	      $auth.signup({displayName: this.signUpUser.displayName,email: this.signUpUser.email,password: this.signUpUser.password})
	      .then(function(response) {
		    	  if(response.status == 201){
			        	showToast("Welcome! Account successfully registered for " + response.data.user.displayName );
			        	$scope.$storage.user = response.data.user;
			        	$location.path('/add');
		    	  }
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
