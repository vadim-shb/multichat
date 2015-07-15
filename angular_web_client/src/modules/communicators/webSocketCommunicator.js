angular.module('communicators').factory('webSocketCommunicator', ['tokenGenerator', function(tokenGenerator) {
        var TIMEOUT = 3000;
        var ws;

        var waitForReceive = {};

        var connectFunc = function(address, receiveMessagesCallback, closeConnectionCallback) {
            ws = new WebSocket('ws://' + address + '/wsHandler');
            ws.onmessage = function(evt) {
                var message = JSON.parse(evt.data);
                if (message.command == "SEND_TEXT_MESSAGE_RECEIVE") {
                    if (message.code == 200) {
                        waitForReceive[message.messageId].successCallback();
                    }
                    else {
                        waitForReceive[message.messageId].errorCallback();
                    }
                    delete waitForReceive[message.messageId];
                }
                if (message.command == "SEND_TEXT_MESSAGES") {
                    receiveMessagesCallback(message.messages);
                }
            };
            ws.onclose = closeConnectionCallback;
        };
        var disconnectFunc = function() {
            ws.close();
        };
        var sendMessageFunc = function(message, successCallback, errorCallback) {
            var receiveToken = tokenGenerator.generate(32);
            ws.send(JSON.stringify({
                command: 'SEND_TEXT_MESSAGE',
                messageId: receiveToken,
                message: message
            }));
            waitForReceive[receiveToken] = {
                successCallback: successCallback,
                errorCallback: errorCallback
            };
            setTimeout(function() {
                if (waitForReceive[receiveToken]) {
                    waitForReceive[receiveToken].errorCallback();
                    delete waitForReceive[receiveToken];
                }
            }, TIMEOUT);
        };

        return {
            connect: connectFunc,
            disconnect: disconnectFunc,
            sendMessage: sendMessageFunc
        }

    }]
);

