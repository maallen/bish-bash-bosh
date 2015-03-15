myAppModule.controller('LoginControllerOauth',function($scope,$location,$rootScope) {
    
    $scope.$on('event:google-plus-signin-success', function (event, authResult) {
        console.log(authResult);
        console.log("Access Token is "+ authResult.access_token);
        $rootScope.$apply(function() {

            $location.path('/add');
            console.log($location.path());
          });
        
      });
    
    $scope.$on('event:google-plus-signin-failure', function (event, authResult) {
        console.log('Not signed into Google Plus.');
      });     
  });