const apiClientService = function () {

    var _client;

    function _createApiClient(options) {
        _client = new JsonapiClient("http://localhost:8181/api/v1/", options);
    }

    var _getClient = function () {
        return _client;
    }

    _createApiClient({});

    return {
        setAuthorization: function (header) {
            header = 'Basic ' + header;
            _createApiClient(
                {header: {"Authorization": header}}
            );
            return header;
        },
        client: _getClient
    }
};

angular.module('api-client', [])
    .service('ApiClient', apiClientService);