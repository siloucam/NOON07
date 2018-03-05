(function() {
    'use strict';

    angular
        .module('noon07App')
        .controller('RelatorioDialogController', RelatorioDialogController);

    RelatorioDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Relatorio'];

    function RelatorioDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Relatorio) {
        var vm = this;

        vm.relatorio = entity;
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
            if (vm.relatorio.id !== null) {
                Relatorio.update(vm.relatorio, onSaveSuccess, onSaveError);
            } else {
                Relatorio.save(vm.relatorio, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('noon07App:relatorioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
