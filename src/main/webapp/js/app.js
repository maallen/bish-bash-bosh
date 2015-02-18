var myAppModule = angular.module('myAppModule', ['ngRoute','ngResource','uiGmapgoogle-maps'])
    .config( [ '$httpProvider','$routeProvider', function($httpProvider, $routeProvider) {

        $httpProvider.interceptors.push('ajaxNonceInterceptor');

        $routeProvider.

        when('/home', {
            templateUrl : 'partials/register.html',
            controller : MembersCtrl
        }).when('/login', {
            templateUrl : 'partials/login.html',
            controller : myAppModule.LoginCtrl
        })
        .when('/add', {
            templateUrl : 'partials/place_add.html',
            controller : PlaceJobController
        }).when('/jobsFeed', {
            templateUrl : 'partials/jobs_feed.html',
            controller : JobsController
        }).otherwise({
            redirectTo : '/login'
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