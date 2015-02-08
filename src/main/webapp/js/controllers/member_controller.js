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