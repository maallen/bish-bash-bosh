function JobsFeedMapController($scope, $http){
	$scope.marker = {
		id: 0,
		coords: {
			latitude: 53.4254167,
			longitude: -7.9243681
		},
		options: { draggable: false}
	};
}