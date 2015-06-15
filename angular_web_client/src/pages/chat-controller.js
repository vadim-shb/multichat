'use strict';

angular.module('webClient').controller('ChatCtrl', ['$scope', '$http', 'serverUrl', function($scope, $http, serverUrl) {
    $scope.sendMessage = function() {
        $http.post(serverUrl + '/send-text-message', $scope.message).
            success(function() {
                console.log('success');
            })
            .error(function() {
                console.log('error');
            });
    }
}]);
