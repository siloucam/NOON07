(function() {
    'use strict';

    angular
        .module('noon07App')
        .controller('ClienteComandaDialogController', ClienteComandaDialogController);

    ClienteComandaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Comanda', 'ProdutoConsumido', 'Cliente'];

    function ClienteComandaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Comanda, ProdutoConsumido, Cliente) {
        var vm = this;

        vm.comanda = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.produtoconsumidos = ProdutoConsumido.query();
        vm.clientes = Cliente.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.comanda.id !== null) {
                Comanda.update(vm.comanda, onSaveSuccess, onSaveError);
            } else {
                Comanda.save(vm.comanda, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            // $scope.$emit('noon07App:comandaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.data = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
