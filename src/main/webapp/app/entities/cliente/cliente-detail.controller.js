(function() {
    'use strict';

    angular
        .module('noon07App')
        .controller('ClienteDetailController', ClienteDetailController);

    ClienteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Cliente', 'Comanda'];

    function ClienteDetailController($scope, $rootScope, $stateParams, previousState, entity, Cliente, Comanda) {
        var vm = this;

        vm.cliente = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('noon07App:clienteUpdate', function(event, result) {
            vm.cliente = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
