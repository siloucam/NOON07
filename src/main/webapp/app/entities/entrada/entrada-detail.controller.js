(function() {
    'use strict';

    angular
        .module('noon07App')
        .controller('EntradaDetailController', EntradaDetailController);

    EntradaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Entrada'];

    function EntradaDetailController($scope, $rootScope, $stateParams, previousState, entity, Entrada) {
        var vm = this;

        vm.entrada = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('noon07App:entradaUpdate', function(event, result) {
            vm.entrada = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
