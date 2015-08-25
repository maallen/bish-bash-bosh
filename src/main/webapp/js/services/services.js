myAppModule.factory('JobService', function($http){
		
	return {
		getJobsList: getJobsList,
		createJob: createJob
	}

    function getJobsList() {
        var request = $http({
        	method:'GET',
            url: 'rest/getJobs',
            params: {
                action: "get"
            }
        });
        return( request.then( handleSuccess ) );

    }

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

myAppModule.factory('SidebarService', function(){
	
	return {
		
		sidebarItems: [],
		
		clearSidebarItems: function(){
			this.sidebarItems = [];
		},
		
		addMenuItem: function(item){
			this.sidebarItems.push(item);
			
			this.sidebarItems.sort(function(a, b){
				return (a.order > b.order) ? 1 : ((b.order > a.order) ? -1:0);
			});
		}
	};

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