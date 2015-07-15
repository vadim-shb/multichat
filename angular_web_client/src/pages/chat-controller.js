'use strict';

angular.module('webClient').controller('ChatCtrl', ['$scope', 'serverUrl', 'restCommunicator', 'webSocketCommunicator', 'stompCommunicator',
    function($scope, defaultServerUrl, restCommunicator, webSocketCommunicator, stompCommunicator) {
        var communicator;
        $scope.connection = {
            serverUrl: defaultServerUrl,
            type: 'SocksJS + STOMP'
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
            if (!$scope.$$phase) {
                $scope.$apply();
            }
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
                if (!$scope.$$phase) {
                    $scope.$apply();
                }
            }
        };

        var onReceiveMessage = function(message) {
            $scope.showMessage(message);
            if (!$scope.$$phase) {
                $scope.$apply();
            }
        };

        var onReceiveMessagesError = function() {
            console.log("Error. Can't receive messages");
        };

        var onDisconnect = function() {
            $scope.visibleMessageLine = [];
            $scope.connected = false;
            if (!$scope.$$phase) {
                $scope.$apply();
            }
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
                communicator = webSocketCommunicator;
                communicator.connect($scope.connection.serverUrl, onReceiveMessages, onDisconnect);
                $scope.connected = true;
            }
            if ($scope.connection.type == "SocksJS + STOMP") {
                communicator = stompCommunicator;
                communicator.connect($scope.connection.serverUrl, onReceiveMessage, onDisconnect);
                $scope.connected = true;
            }
        };

        $scope.disconnect = function() {
            communicator.disconnect();
            $scope.connected = false;
        };
    }])
;
