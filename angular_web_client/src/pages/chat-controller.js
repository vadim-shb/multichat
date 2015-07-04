'use strict';

angular.module('webClient').controller('ChatCtrl', ['$scope', 'serverUrl', 'restCommunicator', 'webSocketCommunicator', function($scope, defaultServerUrl, restCommunicator, webSocketCommunicator) {
    var communicator;
    $scope.connection = {
        serverUrl: defaultServerUrl,
        type: 'WebSocket'
    };

    $scope.connected = false;

    $scope.message = {
        author: '',
        text: ''
    };

    $scope.visibleMessageLine = [];

    $scope.showMessage = function(msg) {
        var visualMessage = {
            text: '[' + msg.time.substring(0, 16) + '] <' + msg.author + '> ' + msg.text,
            color: msg.author == $scope.message.author ? 'red' : 'blue'
        };
        $scope.visibleMessageLine.push(visualMessage)
    };

    $scope.clearMessagesArea = function() {
        $scope.message.text = '';
    };

    var onSendMessageSuccess = function() {
        $scope.clearMessagesArea();
    };
    var onSendMessageError = function() {
        console.log("Error. Can't send message");
    };
    var onReceiveMessages = function(messages) {
        if (messages.length > 0) {
            messages.forEach($scope.showMessage);
        }
        $scope.$apply();
    };
    var onReceiveMessagesError = function() {
        console.log("Error. Can't receive messages");
    };

    var onDisconnect = function() {
        $scope.visibleMessageLine = [];
        $scope.connected = false;
        $scope.$apply();
    };

    $scope.sendMessage = function() {
        communicator.sendMessage($scope.message, onSendMessageSuccess, onSendMessageError);
    };

    $scope.connect = function() {
        if ($scope.connection.type == "REST") {
            communicator = restCommunicator;
            communicator.connect($scope.connection.serverUrl, onReceiveMessages, onDisconnect);
            $scope.connected = true;
        }
        if ($scope.connection.type == "WebSocket") {
            communicator = webSocketCommunicator;
            communicator.connect($scope.connection.serverUrl, onReceiveMessages, onDisconnect);
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
