(function() {
    'use strict';

    angular
        .module('noon07App')
        .factory('RelatorioSearch', RelatorioSearch);

    RelatorioSearch.$inject = ['$resource'];

    function RelatorioSearch($resource) {
        var resourceUrl =  'api/_search/relatorios/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
