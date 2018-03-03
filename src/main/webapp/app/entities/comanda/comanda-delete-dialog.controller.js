(function() {
    'use strict';

    angular
        .module('noon07App')
        .controller('ComandaDeleteController',ComandaDeleteController);

    ComandaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Comanda'];

    function ComandaDeleteController($uibModalInstance, entity, Comanda) {
        var vm = this;

        vm.comanda = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Comanda.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
