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

myAppModule.factory('RegisterUserService', function($resource){
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