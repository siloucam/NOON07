(function() {
    'use strict';
    angular
        .module('noon07App')
        .factory('ExtendUser', ExtendUser);

    ExtendUser.$inject = ['$resource'];

    function ExtendUser ($resource) {
        var resourceUrl =  'api/extend-users/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
