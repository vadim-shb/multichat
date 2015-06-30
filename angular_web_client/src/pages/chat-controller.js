'use strict';

angular.module('webClient').controller('ChatCtrl', ['$scope', '$http', 'serverUrl', function($scope, $http, defaultServerUrl) {
    $scope.serverUrl = defaultServerUrl;
    $scope.connected = false;

    $scope.message = {
        author: '',
        text: ''
    };

    $scope.visibleMessageLine = [];

    function showMessage(msg) {
        var visualMessage = {
            text: '[' + msg.time.substring(0, 16) + '] <' + msg.author + '> ' + msg.text,
            color: msg.author == $scope.message.author ? 'red' : 'blue'
        };
        $scope.visibleMessageLine.push(visualMessage)
    }

    $scope.sendMessage = function() {
        $http.post('http://' + $scope.serverUrl + '/api/client/send-text-message', $scope.message).
            success(function(msg) {
                $scope.message.text = '';
                showMessage(msg);
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
                        messages.forEach(showMessage);
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
