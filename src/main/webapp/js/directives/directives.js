myAppModule.directive('sidebar', function($rootScope) {
	return {
		restrict: 'E',
		scope: false,
		replace: true,
		templateUrl: 'partials/sidebar.html',
		controller: 'SidebarController',
        link: function (scope, element, attr) {

        }
	};
});