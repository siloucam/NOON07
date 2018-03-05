(function() {
    'use strict';

    angular
        .module('noon07App')
        .factory('EntradaSearch', EntradaSearch);

    EntradaSearch.$inject = ['$resource'];

    function EntradaSearch($resource) {
        var resourceUrl =  'api/_search/entradas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
