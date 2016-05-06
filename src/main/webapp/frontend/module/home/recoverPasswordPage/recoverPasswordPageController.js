/**
 * Created by IO on 06.05.2016.
 */

'use strict';

function recoverPasswordPageController($scope, $http, $routeParams){
    $scope.passwordChange = false;

    $scope.init = function () {
        $scope.email = $routeParams.email;
        console.log($scope.passwordSecond);
        

    };

    $scope.changePassword = function () {
        console.log($scope.passwordSecond);
        
        $http({
            method: 'POST',
            url: '/changePassword',
            contentType: 'application/json',
            data: {password : $scope.passwordSecond}
        }).success(function (data, status) {
            console.log(data);
            console.log(status);
            if(status != 409){
                $scope.passwordChange = true;
                $scope.username = data.username;
            }
        }).error(function (data, status, headers) {
            console.log(data);
        });
    }
}

angular.module('appRecoverPassword')
    .controller('recoverPasswordPageController', ['$scope', '$http', '$routeParams', recoverPasswordPageController]);