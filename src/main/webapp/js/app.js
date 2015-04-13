var myAppModule = angular.module('myAppModule', ['ngRoute','ngResource','uiGmapgoogle-maps', 'ngMaterial','satellizer', 'ngStorage','ngMessages','ngPassword', 'ngMdIcons'])
    .config( ['$httpProvider','$routeProvider','$authProvider', function($httpProvider, $routeProvider,$authProvider) {

    	$authProvider.loginUrl = 'rest/auth/login';
    	$authProvider.signupUrl = 'rest/auth/signup';
    	
    	$authProvider.google({
    		url: 'rest/auth/google',
    		clientId : "946568828798-ob0dnlcdhe30kbgajfdp26it3mt4pb86.apps.googleusercontent.com",
        	authorizationEndpoint: 'https://accounts.google.com/o/oauth2/auth',
        	scope: ['profile', 'email'],
        	redirectUri: window.location.origin + '/' || window.location.protocol + '//' + window.location.host + '/',
        	scopePrefix: 'openid',
        	scopeDelimiter: ' ',
        	requiredUrlParams: ['scope'],
        	optionalUrlParams: ['display'],
        	display: 'popup',
        	type: '2.0',
        	popupOptions: { width: 580, height: 500 }		  
    	});
    	
    	$authProvider.facebook({
    		  url: 'rest/auth/facebook',
    		  clientId: '592319570903607',
    		  authorizationEndpoint: 'https://www.facebook.com/dialog/oauth',
    		  redirectUri: window.location.origin + '/' || window.location.protocol + '//' + window.location.host + '//',
    		  scope: 'email',
    		  scopeDelimiter: ',',
    		  requiredUrlParams: ['display', 'scope'],
    		  display: 'popup',
    		  type: '2.0',
    		  popupOptions: { width: 580, height: 500 }	
    		});

        $routeProvider
        .when('/add', {
            templateUrl : 'partials/place_job.html',
            controller : myAppModule.CreateJobsController
        }).when('/jobsFeed', {
            templateUrl : 'partials/jobs_feed.html',
            controller : myAppModule.ViewJobsController
        }).when('/landingpage', {
            templateUrl : 'partials/landing_page.html',
            controller : myAppModule.LoginController
        }).when('/profile', {
        	templateUrl : 'partials/profile.html',
        	controller : myAppModule.ProfilePageController
        }).otherwise({
            redirectTo : '/landingpage'
        });
    } ])
    .config(function($mdThemingProvider) {
    	$mdThemingProvider.theme('default')
        .primaryPalette('blue', {
          'default': '400', // by default use shade 400 from the pink palette for primary intentions
          'hue-1': '100', // use shade 100 for the <code>md-hue-1</code> class
          'hue-2': '600', // use shade 600 for the <code>md-hue-2</code> class
          'hue-3': 'A100' // use shade A100 for the <code>md-hue-3</code> class
        })
        // If you specify less than all of the keys, it will inherit from the
        // default shades
        .accentPalette('grey', {
          'default': '200' // use shade 200 for default, and keep all other shades the same
        });
    })
    .config(['uiGmapGoogleMapApiProvider', function(GoogleMapApiProviders) {
    	        GoogleMapApiProviders.configure({
    	        	// This key needs to be updated to our own developer key..
    	        	key: 'AIzaSyCXxlIQE_baTY18opP79TFol5Ck40xQyP8',
    	            v: '3.17',
    	            libraries: 'weather,geometry,visualization'
    	        });
    	    }]);
