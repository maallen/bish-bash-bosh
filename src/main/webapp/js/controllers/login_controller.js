/*angular.module("app").controller("LoginCtrl",function($scope,$location, $window, $http, UserService, AuthenticationService) {

    $scope.clearMessages = function () {
        $scope.successMessages = '';
        $scope.errorMessages = '';
        $scope.errors = {};
    };

    $scope.reset = function() {

        if($scope.regForm) {
            $scope.regForm.$setPristine();
        }
        $scope.newMember = {email: "", password: ""};

        $scope.clearMessages();
    };

    $scope.login = function(email, password) {
    	
        console.log("email ==> "+email);
        console.log("Password ==> "+password);
        
        if (email !== undefined && password !== undefined) {
 
                UserService.logIn(email, password).success(function(data) {
                	
                    AuthenticationService.isLogged = true;
                    $window.sessionStorage.token = data.token;
                    $location.path("/add");
                    
                }).error(function(result, data) {
                	
                    console.log(result);
                    console.log(data);
                    if ((result.status == '409') || (result.status == '400')) {
                    	alert("Undefined User Name and Password in..");
                        $scope.errors = result.data;
                    } else {
                    	alert("Undefined User Name and Password in..");
                        $scope.errorMessages = [ 'Unknown  server error' ];
                    }
                    $scope.errorMessages = [ 'Unknown  server error' ];
                });
            }
        else{
            alert("Undefined User Name and Password in..");
        };
    };
    $scope.logout = function logout() {
            if (AuthenticationService.isLogged) {
                AuthenticationService.isLogged = false;
                delete $window.sessionStorage.token;
                $location.path("/");
            }
        };
    
    $scope.reset();
});*/

function LoginCtrl($scope,$location, $window, $http, UserService, AuthenticationService) {

    $scope.clearMessages = function () {
        $scope.successMessages = '';
        $scope.errorMessages = '';
        $scope.errors = {};
    };

    $scope.reset = function() {

        if($scope.regForm) {
            $scope.regForm.$setPristine();
        }
        $scope.newMember = {email: "", password: ""};

        $scope.clearMessages();
    };

    $scope.login = function(email, password) {
    	
        console.log("email ==> "+email);
        console.log("Password ==> "+password);
        
        if (email !== undefined && password !== undefined) {
 
                UserService.logIn(email, password).success(function(data) {
                	
                    AuthenticationService.isLogged = true;
                    $window.sessionStorage.token = data.token;
                    $location.path("/add");
                    
                }).error(function(result, data) {
                	
                    console.log(result);
                    console.log(data);
                    if ((result.status == '409') || (result.status == '400')) {
                    	alert("Undefined User Name and Password in..");
                        $scope.errors = result.data;
                    } else {
                    	alert("Undefined User Name and Password in..");
                        $scope.errorMessages = [ 'Unknown  server error' ];
                    }
                    $scope.errorMessages = [ 'Unknown  server error' ];
                });
            }
        else{
            alert("Undefined User Name and Password in..");
        };
    };
    $scope.logout = function logout() {
            if (AuthenticationService.isLogged) {
                AuthenticationService.isLogged = false;
                delete $window.sessionStorage.token;
                $location.path("/");
            }
        };
    
    $scope.reset();
}