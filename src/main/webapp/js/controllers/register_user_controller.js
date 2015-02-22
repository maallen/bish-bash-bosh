myAppModule.controller('RegisterUserController',function($scope, $http, RegisterUserService) {

    $scope.refresh = function() {
        $scope.users = RegisterUserService.query();
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

        $scope.newUser = {email: "", password: ""};

        $scope.clearMessages();
    };

    $scope.register = function() {
        $scope.clearMessages();

        RegisterUserService.save($scope.newUser, function(data) {

            $scope.refresh();

            $scope.reset();

            $scope.successMessages = [ 'User Registered' ];
        }, function(result) {
            if ((result.status == 409) || (result.status == 400)) {
                $scope.errors = result.data;
            } else {
                $scope.errorMessages = [ 'Unknown  server error' ];
            }
        });

    };

    $scope.refresh();
    $scope.reset();
    $scope.orderBy = 'email';
});