(function() {
    'use strict';

    angular
        .module('noon07App')
        .controller('LancamentoDeleteController',LancamentoDeleteController);

    LancamentoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Lancamento'];

    function LancamentoDeleteController($uibModalInstance, entity, Lancamento) {
        var vm = this;

        vm.lancamento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Lancamento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
