(function() {
    'use strict';

    angular
        .module('noon07App')
        .factory('ComandaSearch', ComandaSearch);

    ComandaSearch.$inject = ['$resource'];

    function ComandaSearch($resource) {
        var resourceUrl =  'api/_search/comandas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
