(function() {
    'use strict';

    angular
        .module('noon07App')
        .controller('RelatorioDeleteController',RelatorioDeleteController);

    RelatorioDeleteController.$inject = ['$uibModalInstance', 'entity', 'Relatorio'];

    function RelatorioDeleteController($uibModalInstance, entity, Relatorio) {
        var vm = this;

        vm.relatorio = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Relatorio.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
