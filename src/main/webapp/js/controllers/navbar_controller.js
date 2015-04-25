myAppModule.controller('NavbarController', function($scope, $rootScope, $auth,$mdToast, $localStorage, $mdMedia, $mdSidenav) {
	$rootScope.$mdMedia = $mdMedia;
	
    $scope.isAuthenticated = function() {
      return $auth.isAuthenticated();
    };
    
    $scope.toggleSidebar = function(){
    	$mdSidenav('sidenav-left').toggle().then(function(){
			console.log("Sidebar toggled");
    		});
    };
    
    $scope.signOut = function(){
    	$auth.logout("/");
    	$localStorage.$reset();
    	showToast("Successfully logged out of your account");
    }
    
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