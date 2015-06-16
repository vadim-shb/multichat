'use strict';

angular
    .module('webClient', [
        'ngRoute'
    ])
    .config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/', {templateUrl: 'pages/chat.html', controller: 'ChatCtrl'});
        $routeProvider.otherwise({redirectTo: '/'});
    }])
    .value('serverUrl', 'http://localhost:12000/api');
