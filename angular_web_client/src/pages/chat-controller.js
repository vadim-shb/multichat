'use strict';

angular.module('webClient').controller('ChatCtrl', ['$scope', '$http', 'serverUrl', function($scope, $http, defaultServerUrl) {
    $scope.serverUrl = defaultServerUrl;
    $scope.connected = false;

    $scope.message = {
        text: ''
    };

    $scope.sendMessage = function() {
        $http.post('http://' + $scope.serverUrl + '/api/client/send-text-message', $scope.message).
            success(function() {
                console.log('success');
            })
            .error(function() {
                console.log('error');
            });
    };

    $scope.connect = function() {
        $scope.connected = true;
    };

    $scope.disconnect = function() {
        $scope.connected = false;
    };
}]);
