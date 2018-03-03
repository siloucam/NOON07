(function() {
    'use strict';

    angular
        .module('noon07App')
        .controller('ClienteDialogController', ClienteDialogController);

    ClienteDialogController.$inject = ['AlertService','$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cliente', 'Comanda'];

    function ClienteDialogController (AlertService, $timeout, $scope, $stateParams, $uibModalInstance, entity, Cliente, Comanda) {
        var vm = this;

        vm.cliente = entity;
        vm.clear = clear;
        vm.save = save;
        vm.comandas = Comanda.query();

        vm.numerocomanda = null;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cliente.id !== null) {
                Cliente.update(vm.cliente, onSaveSuccess, onSaveError);
            } else {
                Cliente.save(vm.cliente, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {

            if(vm.numerocomanda){
            var comanda = new Comanda();

            comanda.cliente = result;
            comanda.numero = vm.numerocomanda;
            comanda.data = new Date();
            comanda.total = 0;
            comanda.status = 'ABERTA';
            comanda.id = null;

            Comanda.save(comanda,function(){
                console.log("Salvou");
            AlertService.info("Cliente salvo com sucesso!");
            AlertService.info("Comanda aberta com sucesso!");
            
            // $scope.$emit('noon07App:clienteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
            });
        }else{
            AlertService.info("Cliente salvo com sucesso!");
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
