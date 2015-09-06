myAppModule.controller('ViewJobsController',function($scope, $interval, $http, $localStorage, JobService, LocationService) {
    
	$scope.jobs = [];	
	$scope.orderBy = 'price';
    LocationService.getCoordinates(setMapCenter);
     		
	load_jobs();
	
	$interval(function(){
		load_jobs();
		},30000);
	 
	function setMapCenter(position){
        $scope.map = {
            center: { latitude: position.coords.latitude, longitude: position.coords.longitude},
            zoom: 10 };
    }

	function load_jobs(){
		JobService.getJobsList().then(function(dataResponse) {		
		$scope.jobs = dataResponse;
		});
	}
});
	
myAppModule.controller('CreateJobsController',function($scope, $http, $location, $mdToast, JobService, LocationService){

    $scope.job = {
        coordinates: {latitude: 0, longitude: 0}
    };

    $scope.locationIcon = "gps_not_fixed";

    $scope.location = {
        latitude: '',
        longitude: ''
    }

    initAutocomplete();

    function initAutocomplete() {
        // Create the autocomplete object, restricting the search to geographical
        // location types.
        $scope.autocomplete = new google.maps.places.Autocomplete(
            (document.getElementById('jobLocation')),
            {types: ['geocode']});

        // When the user selects an address from the dropdown, populate the address
        // fields in the form.
        $scope.autocomplete.addListener('place_changed', setCoordinates);

        LocationService.getCoordinates(setAutoCompleteBounds);
    }

    function setAutoCompleteBounds(position){
        var geolocation = {
            lat: position.coords.latitude,
            lng: position.coords.longitude
        };
        var circle = new google.maps.Circle({
            center: geolocation,
            radius: position.coords.accuracy
        });
        $scope.autocomplete.setBounds(circle.getBounds());
    }

    function setCoordinates(){
        var place = $scope.autocomplete.getPlace();
        $scope.job.location = place.formatted_address;
        $scope.job.coordinates.latitude = place.geometry.location.G;
        $scope.job.coordinates.longitude = place.geometry.location.K;
    }

    $scope.createJob = function(){
    	JobService.createJob($scope.job).then(function(response) {
        	showToast("Your Job has been created successfully");
        	$location.path('/jobsFeed');
        });
    };
    
    showToast = function(content) {
        $mdToast.show(
          $mdToast.simple()
            .content(content)
            .position($scope.getToastPosition())
            .hideDelay(5000)
        );
      };
      
      $scope.getToastPosition = function() {
  	    return Object.keys($scope.toastPosition)
  	      .filter(function(pos) { return $scope.toastPosition[pos]; })
  	      .join(' ');
  	  };
	  $scope.toastPosition = {
			    bottom: true,
			    top: false,
			    left: false,
			    right: true,
	  }

	$scope.getLocation = function(){
        LocationService.getCoordinates(showPosition);
    }

    showPosition = function(position){
        $scope.job.coordinates.latitude=position.coords.latitude;
        $scope.job.coordinates.longitude=position.coords.longitude;
        var geocoder = new google.maps.Geocoder();
        var latLng = new google.maps.LatLng($scope.job.coordinates.latitude, $scope.job.coordinates.longitude);

        if(geocoder){
            geocoder.geocode({'latLng': latLng}, function(results, status){
                if(status == google.maps.GeocoderStatus.OK){
                    $scope.job.location = results[0].formatted_address;
                    $scope.locationIcon = "gps_fixed";
                }
                else{
                    console.log('Geocoding failed');
                    $scope.locationIcon = "gps_not_fixed";
                }
            });
        }
    }
});
