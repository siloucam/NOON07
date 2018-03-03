(function() {
    'use strict';

    angular
        .module('noon07App')
        .controller('ProdutoDetailController', ProdutoDetailController);

    ProdutoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Produto'];

    function ProdutoDetailController($scope, $rootScope, $stateParams, previousState, entity, Produto) {
        var vm = this;

        vm.produto = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('noon07App:produtoUpdate', function(event, result) {
            vm.produto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
