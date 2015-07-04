angular.module("communicators").factory("restCommunicator", ['$http', function($http) {
        var url = "";
        var connected = false;
        var CHECK_MESSAGES_INTERVAL = 300;
        var innerCloseConnectionCallback;
        var charSessionId;

        var connectFunc = function(address, receiveMessagesCallback, closeConnectionCallback) {
            url = 'http://' + address + '/api/client';
            $http.get(url + '/connect').
                success(function(data) {
                    charSessionId = data.charSessionId;
                    connected = true;
                    innerCloseConnectionCallback = closeConnectionCallback;
                    receiveMessagesFunc(receiveMessagesCallback);
                })
                .error(function() {
                    closeConnectionCallback();
                });
        };
        var disconnectFunc = function() {
            $http.get(url + '/disconnect', {params: {chatSessionId: charSessionId}});
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
                $http.get(url + '/receive-text-messages', {params: {chatSessionId: charSessionId}}).
                    success(function(messages) {
                        successCallback(messages);
                        setTimeout(receiveMessagesFunc, CHECK_MESSAGES_INTERVAL, successCallback);
                    })
                    .error(function() {
                        //setTimeout(receiveMessagesFunc, CHECK_MESSAGES_INTERVAL, successCallback);
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
