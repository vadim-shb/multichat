'use strict';

angular.module('webClient').controller('ChatCtrl', ['$scope', 'serverUrl', 'restCommunicator', 'webSocketCommunicator', function($scope, defaultServerUrl, restCommunicator, webSocketCommunicator) {
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
        console.log("Error. Can't receive messages");
    };

    $scope.sendMessage = function() {
        communicator.sendMessage($scope.message, sendMessageSuccess, sendMessageError);
    };

    $scope.connect = function() {
        if ($scope.connection.type == "REST") {
            communicator = restCommunicator;
            //communicator.receiveMessages(receiveMessagesSuccess, receiveMessagesError);
            communicator.connect($scope.connection.serverUrl, receiveMessagesSuccess, receiveMessagesError);
            $scope.connected = true;
        }
        if ($scope.connection.type == "WebSocket") {
            communicator = webSocketCommunicator;
            communicator.receiveMessages(receiveMessagesSuccess, receiveMessagesError);
            communicator.connect($scope.connection.serverUrl);
            $scope.connected = true;
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
