angular.module("communicators").factory("restCommunicator", ['$http', function($http) {
        var scope = {};
        var CHECK_MESSAGES_INTERVAL = 300;

        var connectFunc = function(address) {
            scope.url = 'http://' + address + '/api/client';
            scope.connected = true;
        };
        var disconnectFunc = function() {
            scope.connected = false;
        };
        var sendMessageFunc = function(message, successCallback, errorCallback) {
            $http.post(scope.url + '/send-text-message', message).
                success(successCallback)
                .error(errorCallback);
        };
        var receiveMessagesFunc = function(successCallback, errorCallback) {
            if (scope.connected) {
                $http.get(scope.url + '/receive-text-message').
                    success(function(messages) {
                        successCallback(messages);
                        setTimeout(receiveMessagesFunc(successCallback, errorCallback), CHECK_MESSAGES_INTERVAL);
                    })
                    .error(function() {
                        errorCallback();
                        setTimeout(receiveMessagesFunc(successCallback, errorCallback), CHECK_MESSAGES_INTERVAL);
                    });
            }
        };

        return {
            connect: connectFunc,
            disconnect: disconnectFunc,
            sendMessage: sendMessageFunc,
            receiveMessages: receiveMessagesFunc
        }
    }]
);
