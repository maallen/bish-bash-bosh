myAppModule.factory('Jobs', ['$resource',getJobsFunction]);

function getJobsFunction($resource,$http){
	
    return $resource('rest/getJobs', {}, {
        query: {method:'GET', isArray:true}
      });	
}

myAppModule.factory('CreateJobService', function($http){
        return  {
            createJob: function(job){
            return $http.post('rest/createJob', job);
        }
    };
});
