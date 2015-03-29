myAppModule.factory('JobService', function($http){
		
	return {
		getJobsList: getJobsList,
		createJob: createJob
	}

	//***********getJobsList************************
    function getJobsList() {
    //  get all of the jobs in the remote collection.
        var request = $http({
        	method:'GET',
            url: 'rest/getJobs',
            params: {
                action: "get"
            }
        });
        //we can add the error scenario in here also when ready.
        // eg. return( request.then(handleSuccess, handleError ));
        return( request.then( handleSuccess ) );

    }
    
    //***********createJob*************************
    function createJob(job){
    	console.log('createJob');
            return $http.post('rest/createJob', job);
    }
	
    // Transform the successful response, unwrapping the application data
    // from the API response pay-load.
    function handleSuccess( response ) {
        return ( response.data );
    }		    
});

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

myAppModule.factory('UserService', function($http){
        return {
            logIn: function(email, password) {    		
                return $http.post('rest/members/login', {email : email,password:password});
            },
 
        logOut: function() {
                return $http.post('/logout');
        }
        }
});