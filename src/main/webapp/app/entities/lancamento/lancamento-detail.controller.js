(function() {
    'use strict';

    angular
        .module('noon07App')
        .controller('LancamentoDetailController', LancamentoDetailController);

    LancamentoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Lancamento'];

    function LancamentoDetailController($scope, $rootScope, $stateParams, previousState, entity, Lancamento) {
        var vm = this;

        vm.lancamento = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('noon07App:lancamentoUpdate', function(event, result) {
            vm.lancamento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
