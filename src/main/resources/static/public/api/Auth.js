const authController = function ($scope, ApiClient) {

    $scope.vm = {
        token: null
    }
    $scope.tokenToSave = null;

    $scope.setAuthorizationHeader = function () {
        $scope.vm.token = ApiClient.setAuthorization($scope.tokenToSave);
    }
}

angular.module('auth', ['api-client'])
    .controller('AuthController', authController);