(function() {
    'use strict';

    angular
        .module('noon07App')
        .controller('RelatorioDetailController', RelatorioDetailController);

    RelatorioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Relatorio'];

    function RelatorioDetailController($scope, $rootScope, $stateParams, previousState, entity, Relatorio) {
        var vm = this;

        vm.relatorio = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('noon07App:relatorioUpdate', function(event, result) {
            vm.relatorio = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
