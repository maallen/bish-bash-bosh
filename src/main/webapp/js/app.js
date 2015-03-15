var myAppModule = angular.module('myAppModule', ['ngRoute','ngResource','uiGmapgoogle-maps', 'ngMaterial','directive.g+signin'])
    .config( [ '$httpProvider','$routeProvider', function($httpProvider, $routeProvider) {

        $routeProvider
        .when('/add', {
            templateUrl : 'partials/place_job.html',
            controller : myAppModule.CreateJobsController
        }).when('/jobsFeed', {
            templateUrl : 'partials/jobs_feed.html',
            controller : myAppModule.ViewJobsController
        }).when('/landingpage', {
            templateUrl : 'partials/landing_page.html',
            controller : myAppModule.LoginControllerOauth
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
    	    }]);

$(document).ready(function(){
	$(".button-collapse").sideNav();
});