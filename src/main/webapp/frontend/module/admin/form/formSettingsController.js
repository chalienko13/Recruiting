/**
 * Created by dima on 30.04.16.
 */

function formSettingsController($scope, ngToast, $sce, formAppService) {

    $scope.getMandatory = function () {
        var role = "ROLE_ADMIN";
        formAppService.getAllQuestion(role).then(function success(data) {
            console.log(data);
            $scope.questions = data;
            // angular.forEach(data, function (item, i) {
            //     console.log(item.variants);
            //     });
        });
    };

    $scope.getSoft = function () {
        var role = "ROLE_SOFT";
        formAppService.getAllQuestion(role).then(function success(data) {
            console.log(data);
            $scope.questions = data;
            $scope.variants = [];
            angular.forEach(data, function (item, i) {
                console.log(item.variants);
                $scope.variants.push(item.variants);
            });
        });
    };

    $scope.getTech = function () {
        var role = "ROLE_TECH";
        formAppService.getAllQuestion(role).then(function success(data) {
            console.log(data);
            $scope.questions = data;
            $scope.variants = [];
            angular.forEach(data, function (item, i) {
                console.log(item.variants);
                $scope.variants.push(item.variants);
            });
        });
    };


    $scope.types = ["input", "checkbox", "radio", "select"];
    $scope.roles = ["MANDATORY", "ROLE_TECH", "ROLE_STUDENT"];
    $scope.sce = $sce;


    var selectedValue;
    $scope.showSelectValue = function (mySelect) {
        console.log(mySelect);
        selectedValue = mySelect;
        if (selectedValue == "input") {
            $scope.canMoveForward = true;
        } else {
            var myToastMsg = ngToast.info({
                content: 'a bla bla bla',//TODO
                timeout: 5000,
                horizontalPosition: 'center',
                verticalPosition: 'bottom',
                dismissOnClick: true,
                combineDuplications: true,
                maxNumber: 2
            });
            $scope.canMoveForward = false;
        }
    };
    var selectActiveValue;
    $scope.showSelectActiveValue = function (myActiveSelect) {
        console.log(myActiveSelect);
        selectActiveValue = myActiveSelect;
    };
    var selectRoleValue;
    $scope.showSelectRoleValue = function (myRoleSelect) {
        console.log(myRoleSelect);
        selectRoleValue = myRoleSelect;
    };


    function splitString(stringToSplit, separator) {
        return stringToSplit.split(separator);
    }

    $scope.saveForm = function () {

        var comma = ',';

        var variantArray = splitString($scope.addVariant, comma);

        var role;
        console.log(variantArray);
        if(selectRoleValue == "MANDATORY"){
            role = "ROLE_ADMIN";
        }else{
            role = variantArray;
        }

        formAppService.addQuestion($scope.addText, selectedValue, selectActiveValue, variantArray, role);
        
        console.log($scope.addText);
        console.log(selectedValue);
        console.log(selectActiveValue);
        console.log(selectRoleValue);
    };


}

angular.module('appAdminForm')
    .controller('formSettingsController', ['$scope', 'ngToast', '$sce', 'formSettingsService', formSettingsController]);