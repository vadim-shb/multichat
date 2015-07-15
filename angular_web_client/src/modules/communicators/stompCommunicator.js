angular.module("communicators").factory("stompCommunicator", ['tokenGenerator', function(tokenGenerator) {
        var stompClient;

        var connectFunc = function(address, receiveMessagesCallback, closeConnectionCallback) {
            var ws = new SockJS('http://' + address + '/stomp');
            stompClient = Stomp.over(ws);
            stompClient.connect({}, function() {
                stompClient.subscribe('/topic/messages', function(messagesStompDto) {
                    receiveMessagesCallback(JSON.parse(messagesStompDto.body));
                }, function(errorMessage) {
                    closeConnectionCallback(errorMessage);
                });
            });
            ws.onclose = closeConnectionCallback;
        };
        var disconnectFunc = function() {
            stompClient.disconnect();
        };
        var sendMessageFunc = function(message, successCallback, errorCallback) {
            var token = tokenGenerator.generate(32);
            var stompMessage = {
                messageId: token,
                message: message
            };
            var confirmSubscription = stompClient.subscribe('/topic/receive-confirm/' + token, function() {
                successCallback();
                confirmSubscription.unsubscribe();
                confirmSubscription = undefined;
            }, function() {
                errorCallback();
                confirmSubscription.unsubscribe();
                confirmSubscription = undefined;
            });
            stompClient.send('/app/send-text-message', {}, JSON.stringify(stompMessage));
            setTimeout(function() {
                if (confirmSubscription) {
                    errorCallback();
                    confirmSubscription.unsubscribe();
                }
            }, 5000);
        };

        return {
            connect: connectFunc,
            disconnect: disconnectFunc,
            sendMessage: sendMessageFunc
        }

    }]
);
