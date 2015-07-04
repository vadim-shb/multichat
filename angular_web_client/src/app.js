'use strict';

angular
    .module('webClient', [
        'ngRoute',
        'communicators'
    ])
    //.config(['$httpProvider', function($httpProvider) {
    //    $httpProvider.defaults.withCredentials = true;
    //}])
    .config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/', {templateUrl: 'pages/chat.html', controller: 'ChatCtrl'});
        $routeProvider.otherwise({redirectTo: '/'});
    }])
    .value('serverUrl', 'localhost:12000');
