(function() {
    'use strict';
    angular
        .module('noon07App')
        .factory('ProdutoConsumido', ProdutoConsumido);

    ProdutoConsumido.$inject = ['$resource'];

    function ProdutoConsumido ($resource) {
        var resourceUrl =  'api/produto-consumidos/:id';

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
