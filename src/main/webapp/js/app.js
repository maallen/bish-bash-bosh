angular.module('kitchensink', [ 'ngRoute', 'MembersService','LoginService','AuthenticationService','UserService','JobsService' ])
    .config( [ '$httpProvider','$routeProvider', function($httpProvider, $routeProvider) {

        $httpProvider.interceptors.push('ajaxNonceInterceptor');

        $routeProvider.

        when('/home', {
            templateUrl : 'partials/register.html',
            controller : MembersCtrl
        }).when('/login', {
            templateUrl : 'partials/login.html',
            controller : LoginCtrl
        })
        .when('/add', {
            templateUrl : 'partials/place_add.html',
            controller : LoginCtrl
        }).when('/jobsFeed', {
            templateUrl : 'partials/jobs_feed.html',
            controller : JobsController
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