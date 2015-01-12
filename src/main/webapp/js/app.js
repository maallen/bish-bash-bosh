angular.module('kitchensink', [ 'ngRoute', 'membersService','loginService','AuthenticationService','UserService' ])
    .config( [ '$httpProvider','$routeProvider', function($httpProvider, $routeProvider) {

        $httpProvider.interceptors.push('ajaxNonceInterceptor');

        $routeProvider.

        when('/home', {
            templateUrl : 'partials/register.html',
            controller : MembersCtrl

        }).when('/login', {
            templateUrl : 'partials/login.html',
            controller : LoginCtrl
        // Add a default route
        })
        .when('/add', {
            templateUrl : 'partials/place_add.html',
            controller : LoginCtrl
        // Add a default route
        }).otherwise({
            redirectTo : '/login'
        });
    } ])
    .factory('ajaxNonceInterceptor', function() {
        // This interceptor is equivalent to the behavior induced by $.ajaxSetup({cache:false});

        var param_start = /\?/;

        return {
            request : function(config) {
                if (config.method == 'GET') {
                    // Add a query parameter named '_' to the URL, with a value equal to the current timestamp
                    config.url += (param_start.test(config.url) ? "&" : "?") + '_=' + new Date().getTime();
                }
                return config;
            }
        };
    });