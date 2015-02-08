// Define the REST resource service, allowing us to interact with it as a high level service
angular.module('MembersService', ['ngResource']).
    factory('Members', function($resource){
  return $resource('rest/members/:memberId', {});
});

function getJobsFunction($resource,$http){
	
    return $resource('rest/getJobs', {}, {
        query: {method:'GET', isArray:true}
      });	
}

angular.module('JobsService', ['ngResource']).factory('Jobs', ['$resource',getJobsFunction]);

angular.module('LoginService', ['ngResource']).
    factory('loginService', function($resource){
    return $resource('rest/members/login', {});
});

angular.module('AuthenticationService', ['ngResource']).
    factory('AuthenticationService', function(){
        var auth = {
        isLogged: false
    };
 
    return auth;
});

angular.module('CreateJobService', ['ngResource']).
    factory('CreateJobService', function($http){
        return  {
            createJob: function(job){
            return $http.post('rest/createJob', job);
        }
    }
});

angular.module('UserService', ['ngResource']).
    factory('UserService', function($http){
        return {
            logIn: function(email, password) {    		
                return $http.post('rest/members/login', {email : email,password:password});
            },
 
        logOut: function() {
 
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