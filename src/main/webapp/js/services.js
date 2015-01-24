// Define the REST resource service, allowing us to interact with it as a high level service
angular.module('MembersService', ['ngResource']).
    factory('Members', function($resource){
  return $resource('rest/members/:memberId', {});
});

angular.module('JobsService', ['ngResource']).
	factory('Jobs', function($resource){
return $resource('rest/getJobs', {});
});

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