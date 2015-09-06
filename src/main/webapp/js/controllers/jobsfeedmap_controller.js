myAppModule.controller('JobsFeedMapController',function($scope, $interval, $http, JobService, LocationService){
	$scope.map = {
		center: {
			latitude: 53.428665,
			longitude: -8.3320801
		},
		zoom: 8,
		bounds: {}
	};

	$scope.markers =[];
    load_jobs();

    $interval(function(){
        load_jobs();
    }, 30000);

    function load_jobs(){
        JobService.getJobsList().then(function(dataResponse) {
            $scope.markers = [];
            for (i = 0; i < dataResponse.length; i++) {
                $scope.markers.push({
                    id: dataResponse[i]._id,
                    coords: {latitude: dataResponse[i].latitude, longitude:  dataResponse[i].longitude},
                    title: dataResponse[i].title,
                    clickable: true
                });
            }
        });
    }
});