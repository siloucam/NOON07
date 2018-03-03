(function() {
    'use strict';

    angular
        .module('noon07App')
        .controller('ComandaDetailController', ComandaDetailController);

    ComandaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Comanda', 'ProdutoConsumido', 'Cliente'];

    function ComandaDetailController($scope, $rootScope, $stateParams, previousState, entity, Comanda, ProdutoConsumido, Cliente) {
        var vm = this;

        vm.comanda = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('noon07App:comandaUpdate', function(event, result) {
            vm.comanda = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
