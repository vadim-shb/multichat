'use strict';

angular.module('webClient').controller('ChatCtrl', ['$scope', '$http', 'serverUrl', 'restCommunicator', function($scope, $http, defaultServerUrl, restCommunicator) {
    var communicator;
    $scope.connection = {
        serverUrl: defaultServerUrl,
        type: 'REST'
    };

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

    var sendMessageSuccess = function(sentMessageWithTime) {
        $scope.message.text = '';
        showMessage(sentMessageWithTime);
    };
    var sendMessageError = function() {
        console.log("Error. Can't send message");
    };
    var receiveMessagesSuccess = function(messages) {
        if (messages.length > 0) {
            messages.forEach(showMessage);
        }
    };
    var receiveMessagesError = function() {
        console.log('error');
    };

    $scope.sendMessage = function() {
        communicator.sendMessage($scope.message, sendMessageSuccess, sendMessageError);
    };

    $scope.connect = function() {
        if ($scope.connection.type == "REST") {
            communicator = restCommunicator;
            communicator.connect($scope.connection.serverUrl);
            communicator.receiveMessages(receiveMessagesSuccess, receiveMessagesError);
            $scope.connected = true;
        }
        if ($scope.connection.type == "WebSocket") {

        }
        if ($scope.connection.type == "SocksJS") {
        }
        if ($scope.connection.type == "SocksJS + STOMP") {
        }
    };

    $scope.disconnect = function() {
        communicator.disconnect();
        $scope.connected = false;
    };
}]);
