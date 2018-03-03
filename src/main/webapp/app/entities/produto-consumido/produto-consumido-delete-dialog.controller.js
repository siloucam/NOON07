(function() {
    'use strict';

    angular
        .module('noon07App')
        .controller('ProdutoConsumidoDeleteController',ProdutoConsumidoDeleteController);

    ProdutoConsumidoDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProdutoConsumido'];

    function ProdutoConsumidoDeleteController($uibModalInstance, entity, ProdutoConsumido) {
        var vm = this;

        vm.produtoConsumido = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProdutoConsumido.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
