(function() {
    'use strict';
    angular
        .module('noon07App')
        .factory('Lancamento', Lancamento);

    Lancamento.$inject = ['$resource'];

    function Lancamento ($resource) {
        var resourceUrl =  'api/lancamentos/:id';

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
