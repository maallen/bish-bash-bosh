myAppModule.controller('LoginController',function($scope, $rootScope, $state, $location,$auth,$mdToast, $localStorage,$mdDialog, $mdSidenav) {
	
	$scope.$storage = $localStorage;
	
	$mdSidenav("sidenav-left").close();
	
	if($auth.isAuthenticated()){
		$state.go('add');
	};
	
	  $scope.login = function() {
	      $auth.login({ email: this.loginUser.email, password: this.loginUser.password })
	        .then(function(response) {
	        	$scope.$storage.user = response.data.user;
	        	showToast("Successfully logged in with your account");
	        	$location.path('/add');
	        })
	        .catch(function(response) {
	        	if(response.status == 401){
	        	$mdDialog.show(
			  	    	$mdDialog.alert()
			  	          .title('Invalid email and/or password')
			  	          .content("Please try again")
			  	          .ariaLabel('Login Failure')
			  	          .ok('OK')
			  		    );
	        	}
	        });
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
	        	$rootScope.$broadcast('loggedIn');
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
