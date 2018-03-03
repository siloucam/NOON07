(function() {
    'use strict';

    angular
        .module('noon07App')
        .controller('ProdutoConsumidoDialogController', ProdutoConsumidoDialogController);

    ProdutoConsumidoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProdutoConsumido', 'Comanda'];

    function ProdutoConsumidoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProdutoConsumido, Comanda) {
        var vm = this;

        vm.produtoConsumido = entity;
        vm.clear = clear;
        vm.save = save;
        vm.comandas = Comanda.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.produtoConsumido.id !== null) {
                ProdutoConsumido.update(vm.produtoConsumido, onSaveSuccess, onSaveError);
            } else {
                ProdutoConsumido.save(vm.produtoConsumido, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('noon07App:produtoConsumidoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
