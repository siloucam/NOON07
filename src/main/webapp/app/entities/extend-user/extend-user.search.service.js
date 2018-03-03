(function() {
    'use strict';

    angular
        .module('noon07App')
        .factory('ExtendUserSearch', ExtendUserSearch);

    ExtendUserSearch.$inject = ['$resource'];

    function ExtendUserSearch($resource) {
        var resourceUrl =  'api/_search/extend-users/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
