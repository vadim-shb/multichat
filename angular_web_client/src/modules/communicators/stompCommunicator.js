angular.module("communicators").factory("stompCommunicator", [function() {
        var stompClient;

        var connectFunc = function(address, receiveMessagesCallback, closeConnectionCallback) {
            var ws = new SockJS('http://' + address + '/stomp');
            //var ws = new SockJS('http://localhost:12000/stomp');
            stompClient = Stomp.over(ws);
            stompClient.connect({}, function(frame) {
                stompClient.subscribe('/topic/messages', function(messagesStompDto) {
                    receiveMessagesCallback(JSON.parse(messagesStompDto.body));
                }, function(errorMessage) {
                    closeConnectionCallback(errorMessage);
                });
            });
        };
        var disconnectFunc = function() {
            stompClient.disconnect();
        };
        var sendMessageFunc = function(message) {
            stompClient.send('/app/send-text-message', {}, JSON.stringify(message));
        };

        return {
            connect: connectFunc,
            disconnect: disconnectFunc,
            sendMessage: sendMessageFunc
        }

    }]
);
