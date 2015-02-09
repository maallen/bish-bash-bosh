myAppModule.factory('Jobs', ['$resource',getJobsFunction]);

function getJobsFunction($resource,$http){
	
    return $resource('rest/getJobs', {}, {
        query: {method:'GET', isArray:true}
      });	
}

myAppModule.factory('LoginService', 
    function($resource){
    return $resource('rest/members/login', {});
});

// Define the REST resource service, allowing us to interact with it as a high level service
myAppModule.factory('Members', function($resource){
  return $resource('rest/members/:memberId', {});
});

myAppModule.factory('AuthenticationService', function(){
        var auth = {
        isLogged: false
    };
 
    return auth;
});

myAppModule.factory('CreateJobService', function($http){
        return  {
            createJob: function(job){
            return $http.post('rest/createJob', job);
        }
    }
});

myAppModule.factory('UserService', function($http){
        return {
            logIn: function(email, password) {    		
                return $http.post('rest/members/login', {email : email,password:password});
            },
 
        logOut: function() {
                return $http.post('/logout');
        }
    };
});

/*$http.get('rest/getJobs').
  success(function(data, status, headers, config) {
    // this callback will be called asynchronously
    // when the response is available
  }).
  error(function(data, status, headers, config) {
    // called asynchronously if an error occurs
    // or server returns response with an error status.
  });*/