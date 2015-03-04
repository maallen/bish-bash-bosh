myAppModule.controller('LoginControllerOauth',function($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
    $scope.login=function() {
    	console.log("Clicked Login");
    	var client_id="946568828798-ob0dnlcdhe30kbgajfdp26it3mt4pb86.apps.googleusercontent.com";
    	var scope="email";
    	// Comment out the below when working locally
//    	var redirect_uri="http://localhost:8080/caash/";
    	var redirect_uri="http://caash-caash.rhcloud.com/";
    	var response_type="token";
    	var url="https://accounts.google.com/o/oauth2/auth?scope="+scope+"&client_id="+client_id+"&redirect_uri="+redirect_uri+
    	"&response_type="+response_type;
    	window.location.replace(url);
    };
  });


myAppModule.controller('AboutCtrl', function ($scope,$rootScope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
    $scope.root=$rootScope;
  });