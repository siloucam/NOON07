(function() {
    'use strict';

    angular
        .module('noon07App')
        .factory('ProdutoConsumidoSearch', ProdutoConsumidoSearch);

    ProdutoConsumidoSearch.$inject = ['$resource'];

    function ProdutoConsumidoSearch($resource) {
        var resourceUrl =  'api/_search/produto-consumidos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
