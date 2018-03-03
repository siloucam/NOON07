(function() {
    'use strict';

    angular
        .module('noon07App')
        .controller('ProdutoConsumidoDetailController', ProdutoConsumidoDetailController);

    ProdutoConsumidoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProdutoConsumido', 'Comanda'];

    function ProdutoConsumidoDetailController($scope, $rootScope, $stateParams, previousState, entity, ProdutoConsumido, Comanda) {
        var vm = this;

        vm.produtoConsumido = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('noon07App:produtoConsumidoUpdate', function(event, result) {
            vm.produtoConsumido = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
