(function() {
    'use strict';
    angular
        .module('noon07App')
        .factory('Relatorio', Relatorio);

    Relatorio.$inject = ['$resource'];

    function Relatorio ($resource) {
        var resourceUrl =  'api/relatorios/:id';

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
