myAppModule.controller('ViewJobsController',function($scope, $interval, $http, $localStorage, JobService) {
    
	$scope.jobs = [];	
	$scope.orderBy = 'price';
	$scope.map = { 
		center: { latitude: 53.2734, longitude: -7.7 },
		zoom: 8 };
     		
	load_jobs();
	
	$interval(function(){
		load_jobs();
		},30000);
	 
	
	function load_jobs(){
		JobService.getJobsList().then(function(dataResponse) {		
		$scope.jobs = dataResponse;
		});
	}
});
	
myAppModule.controller('CreateJobsController',function($scope, $http, $location, $mdToast, JobService){
    
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
});
