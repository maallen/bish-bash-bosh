var myAppModule = angular.module('myAppModule', ['ngRoute','ngResource','uiGmapgoogle-maps', 'ngMaterial'])
    .config( [ '$httpProvider','$routeProvider', function($httpProvider, $routeProvider) {

        $httpProvider.interceptors.push('ajaxNonceInterceptor');

        $routeProvider.

        when('/register', {
            templateUrl : 'partials/register.html',
            controller : myAppModule.RegisterUserController
        }).when('/login', {
            templateUrl : 'partials/login.html',
            controller : myAppModule.LoginController
        })
        .when('/add', {
            templateUrl : 'partials/place_job.html',
            controller : myAppModule.CreateJobsController
        }).when('/jobsFeed', {
            templateUrl : 'partials/jobs_feed.html',
            controller : myAppModule.ViewJobsController
        }).when('/landingpage', {
            templateUrl : 'partials/landing_page.html',
            controller : myAppModule.LoginControllerOauth
        }).when('/access_token=:accessToken', {
            template: '',
            controller: function ($location,$rootScope) {
              var hash = $location.path().substr(1);
              
              var splitted = hash.split('&');
              var params = {};

              for (var i = 0; i < splitted.length; i++) {
                var param  = splitted[i].split('=');
                var key    = param[0];
                var value  = param[1];
                params[key] = value;
                $rootScope.accesstoken=params;
              }
              $location.path("/add");
            }
          }).otherwise({
            redirectTo : '/landingpage'
        });
    } ])
    .config(['uiGmapGoogleMapApiProvider', function(GoogleMapApiProviders) {
    	        GoogleMapApiProviders.configure({
    	        	// This key needs to be updated to our own developer key..
    	        	key: 'AIzaSyCXxlIQE_baTY18opP79TFol5Ck40xQyP8',
    	            v: '3.17',
    	            libraries: 'weather,geometry,visualization'
    	        });
    	    }])
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