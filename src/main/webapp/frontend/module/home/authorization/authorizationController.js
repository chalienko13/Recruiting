/**
 * Created by dima on 30.04.16.
 */

'use strict';

function authorizationController($scope, authorizationService) {
    
    $scope.login = function () {

        authorizationService.loginIn($scope.email, $scope.password);
        
    }
    
}

angular.module('appAuthorization')
    .controller('authorizationController', ['$scope', 'authorizationService', authorizationController]);