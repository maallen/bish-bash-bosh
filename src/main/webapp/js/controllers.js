function MembersCtrl($scope, $http, Members) {

    $scope.refresh = function() {
        $scope.members = Members.query();
    };

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

    $scope.register = function() {
        $scope.clearMessages();

        Members.save($scope.newMember, function(data) {

            $scope.refresh();

            $scope.reset();

            $scope.successMessages = [ 'Member Registered' ];
        }, function(result) {
            if ((result.status == 409) || (result.status == 400)) {
                $scope.errors = result.data;
            } else {
                $scope.errorMessages = [ 'Unknown  server error' ];
            }
        });

    };

    // Call the refresh() function, to populate the list of members
    $scope.refresh();

    // Initialize newMember here to prevent Angular from sending a request
    // without a proper Content-Type.
    $scope.reset();

    // Set the default orderBy to the name property
    $scope.orderBy = 'email';
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function JobsController($scope,$routeParams,$timeout,$http,$resource, Jobs) {
    
	$scope.jobs = [];
	
	$scope.orderBy = 'price';
	
	(function getJobs() {
		Jobs.query({
	        modelId : $routeParams.modelId
	    }, function(model) {
	        $scope.jobs = model;    
	        $timeout(getJobs, 5000);
	    });
	})();
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function PlaceJobController($scope, $http, CreateJobService){
    
    $scope.createJob = function(){
        CreateJobService.createJob($scope.job);
    };
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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