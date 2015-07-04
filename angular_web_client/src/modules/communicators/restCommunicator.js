angular.module("communicators").factory("restCommunicator", ['$http', function($http) {
        var url = "";
        var connected = false;
        var CHECK_MESSAGES_INTERVAL = 3000;
        var innerCloseConnectionCallback;

        var connectFunc = function(address, receiveMessagesCallback, closeConnectionCallback) {
            url = 'http://' + address + '/api/client';
            $http.get(url + '/connect').
                success(function() {
                    connected = true;
                    innerCloseConnectionCallback = closeConnectionCallback;
                    receiveMessagesFunc(receiveMessagesCallback);
                })
                .error(function() {
                    closeConnectionCallback();
                });
        };
        var disconnectFunc = function() {
            $http.get(url + '/disconnect').
                success(function() {
                    // ignore ...
                })
                .error(function() {
                    // ignore ...
                });
            innerCloseConnectionCallback();
            connected = false;
        };
        var sendMessageFunc = function(message, successCallback, errorCallback) {
            $http.post(url + '/send-text-message', message).
                success(successCallback)
                .error(errorCallback);
        };
        var receiveMessagesFunc = function(successCallback) {
            if (connected) {
                $http.get(url + '/receive-text-messages').
                    success(function(messages) {
                        successCallback(messages);
                        setTimeout(receiveMessagesFunc(successCallback), CHECK_MESSAGES_INTERVAL);
                    })
                    .error(function() {
                        innerCloseConnectionCallback();
                    });
            }
        };

        return {
            connect: connectFunc,
            disconnect: disconnectFunc,
            sendMessage: sendMessageFunc
        }
    }]
);
