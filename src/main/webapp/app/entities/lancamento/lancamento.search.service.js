(function() {
    'use strict';

    angular
        .module('noon07App')
        .factory('LancamentoSearch', LancamentoSearch);

    LancamentoSearch.$inject = ['$resource'];

    function LancamentoSearch($resource) {
        var resourceUrl =  'api/_search/lancamentos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
