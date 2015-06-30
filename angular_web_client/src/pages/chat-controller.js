'use strict';

angular.module('webClient').controller('ChatCtrl', ['$scope', '$http', 'serverUrl', function($scope, $http, defaultServerUrl) {
    $scope.serverUrl = defaultServerUrl;
    $scope.connected = false;

    $scope.message = {
        text: ''
    };

    $scope.visibleMessageLine = [];

    $scope.sendMessage = function() {
        $http.post('http://' + $scope.serverUrl + '/api/client/send-text-message', $scope.message).
            success(function() {
                $scope.message.text = '';
            })
            .error(function() {
                console.log('error');
            });
    };


    $scope.receiveMessage = function() {
        if ($scope.connected) {
            $http.get('http://' + $scope.serverUrl + '/api/client/receive-text-message').
                success(function(messages) {
                    if (messages.length > 0) {
                        messages.forEach(function(msg) {$scope.visibleMessageLine.push(msg)});
                    }
                    setTimeout($scope.receiveMessage, 300);
                })
                .error(function() {
                    console.log('error');
                    setTimeout($scope.receiveMessage, 300);
                });
        }
    };


    $scope.connect = function() {
        $scope.connected = true;
        $scope.receiveMessage();
    };

    $scope.disconnect = function() {
        $scope.connected = false;
    };
}]);
