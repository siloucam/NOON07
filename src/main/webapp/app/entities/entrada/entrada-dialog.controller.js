(function() {
    'use strict';

    angular
        .module('noon07App')
        .controller('EntradaDialogController', EntradaDialogController);

    EntradaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Entrada'];

    function EntradaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Entrada) {
        var vm = this;

        vm.entrada = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.entrada.id !== null) {
                Entrada.update(vm.entrada, onSaveSuccess, onSaveError);
            } else {
                Entrada.save(vm.entrada, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('noon07App:entradaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
