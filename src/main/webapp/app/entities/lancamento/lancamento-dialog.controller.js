(function() {
    'use strict';

    angular
        .module('noon07App')
        .controller('LancamentoDialogController', LancamentoDialogController);

    LancamentoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Lancamento'];

    function LancamentoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Lancamento) {
        var vm = this;

        vm.lancamento = entity;
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
            if (vm.lancamento.id !== null) {
                Lancamento.update(vm.lancamento, onSaveSuccess, onSaveError);
            } else {
                Lancamento.save(vm.lancamento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('noon07App:lancamentoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
