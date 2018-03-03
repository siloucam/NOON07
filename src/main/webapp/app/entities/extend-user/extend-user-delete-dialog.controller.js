(function() {
    'use strict';

    angular
        .module('noon07App')
        .controller('ExtendUserDeleteController',ExtendUserDeleteController);

    ExtendUserDeleteController.$inject = ['$uibModalInstance', 'entity', 'ExtendUser'];

    function ExtendUserDeleteController($uibModalInstance, entity, ExtendUser) {
        var vm = this;

        vm.extendUser = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ExtendUser.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
