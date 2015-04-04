myAppModule.controller('ViewJobsController',function($scope, $interval, $http, $localStorage, JobService) {
    
	$scope.jobs = [];	
	$scope.orderBy = 'price';
	$scope.map = { 
		center: { latitude: 53.2734, longitude: -7.7 },
		zoom: 8 };
     		
	load_jobs(); //load jobs
	
	$interval(function(){ //load every 30 sec there after
		load_jobs();
		},30000); //30 secs
	 
	
	function load_jobs(){
		JobService.getJobsList().then(function(dataResponse) {		
		$scope.jobs = dataResponse;
		});
	}
});
	
myAppModule.controller('CreateJobsController',function($scope, $http, JobService){
    
    $scope.createJob = function(){
    	JobService.createJob($scope.job);
    };
});
