myAppModule.controller('JobsFeedMapController',function($scope, $http, JobService){
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

    function load_jobs(){
        JobService.getJobsList().then(function(dataResponse) {
            for (i = 0; i < dataResponse.length; i++) {
                $scope.markers.push({
                    id: dataResponse[i]._id,
                    latitude: dataResponse[i].latitude,
                    longitude: dataResponse[i].longitude,
                    title: dataResponse[i].title,
                    clickable: true
                });
            }
        });
    }
	// $scope.marker = {
	// 	id: 0,
	// 	coords: {
	// 		latitude: 53.4254167,
	// 		longitude: -7.9243681
	// 	},
	// 	options: { draggable: false}
	// };
});