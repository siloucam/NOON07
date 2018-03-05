(function() {
    'use strict';
    angular
        .module('noon07App')
        .factory('Entrada', Entrada);

    Entrada.$inject = ['$resource'];

    function Entrada ($resource) {
        var resourceUrl =  'api/entradas/:id';

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
