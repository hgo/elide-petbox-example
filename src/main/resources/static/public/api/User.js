const userService = function ($q, ApiClient) {

    var _getUsers = function () {
        var q = $q.defer();
        ApiClient.client().find("user", function (err, resources) {
            if (err) {
                q.reject(err);
            } else {
                var result = resources.map(function (resource) {
                    return resource.toJSON();
                });
                q.resolve(result);
            }
        });
        return q.promise;
    };
    var _newUser = function (model) {
        var user = ApiClient.client().create("user")
        for (k in model) {
            user.set(k, model[k]);
        }
        return user.sync();
    };

    return {
        newUser: _newUser,
        getUsers: _getUsers
    }

};
const userController = function ($scope, UserService) {

    $scope.schema = {
        type: "object",
        properties: {
            email: {type: "string", minLength: 2, title: "Email"},
            name: {type: "string", minLength: 2, title: "Name"},
            password: {type: "string", minLength: 4}
        }
    };

    $scope.code = {
        getUsers: null,
        scheme: JSON.stringify({
            '$scope.schema': $scope.schema
        }, null, 4)
    }

    $scope.getUsers = function () {
        UserService.getUsers().then(function (result) {
            $scope.code.getUsers = JSON.stringify(result, null, 4);

        }, function (err) {
            console.err(err);
        })
    }


    $scope.form = [
        "email", "name", {"key": "password", "type": "password"},
        {
            type: "submit",
            title: "Save"
        }
    ];

    $scope.model = {};

    $scope.onSubmit = function (userForm) {
        if (userForm.$valid) {
            UserService.newUser($scope.model).then(function (a) {
                alert('user posted' + a.toJSON());
            }, function (err) {
                console.err(err);
            });
        } else {
            alert('not valid see console');
            console.info(userForm);
        }
    }
};
angular.module('user', ['api-client'])
    .service('UserService', userService)
    .controller('UserController', userController);