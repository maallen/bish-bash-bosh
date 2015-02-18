function JobsFeedMapController($scope, $http){
	$scope.map = {
		center: {
			latitude: 53.428665,
			longitude: -8.3320801
		},
		zoom: 8,
		bounds: {}
	};

	$scope.markers =[];

	var marker1 = {
		id: 0,
		latitude: 53.428665,
		longitude: -8.3320801,
		title: 1
	};

	var marker2 = {
		id: 1,
		latitude: 53.3965882,
		longitude: -7.9021542,
		title: 2
	};

	$scope.markers.push(marker1);
	$scope.markers.push(marker2);


	// $scope.marker = {
	// 	id: 0,
	// 	coords: {
	// 		latitude: 53.4254167,
	// 		longitude: -7.9243681
	// 	},
	// 	options: { draggable: false}
	// };
}