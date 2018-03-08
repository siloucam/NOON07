(function() {
    'use strict';

    angular
        .module('noon07App')
        .controller('ClienteComandaDialogController', ClienteComandaDialogController);

    ClienteComandaDialogController.$inject = ['$http','$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Comanda', 'ProdutoConsumido', 'Cliente'];

    function ClienteComandaDialogController ($http ,$timeout, $scope, $stateParams, $uibModalInstance, entity, Comanda, ProdutoConsumido, Cliente) {
        var vm = this;

        vm.comanda = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.produtoconsumidos = ProdutoConsumido.query();
        vm.clientes = Cliente.query();

        vm.numeroindisponivel = false;

        var currentLocation = window.location;

        loadEntradas();

        // console.log("Vai tentar abrir");

        $scope.verificanumero = function(){
            // console.log("verificando " + vm.comanda.numero);
            $http.get('http://'+currentLocation.host+'/api/comandas?status.in=ABERTA&numero.equals='+ vm.comanda.numero, 
                {headers: { Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTUyMTgxMzMyMX0.He3bRKEVAk5Lg2yqGK_80Kw_dUaPwYU26coDu_Ba0uIl99H8Ga0K6SVtn4TXGmjIeMWrgoBPikj0MtKxxpKYPA'}})
            .then(function(response) {
                        // console.log("Deu que já tem comanda nesse numero!");                
                        // console.log(response);
                        if(response.data.length > 0){
                            // console.log(vm.comanda.numero + "já ta em uso!");
                           vm.numeroindisponivel = true;
                       }else{
                        // console.log(vm.comanda.numero + "ta livre!");
                            vm.numeroindisponivel = false;
                        }
                });
        }

        function loadEntradas(){
            $http.get('http://'+currentLocation.host+'/api/entradas', 
                {headers: { Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTUyMTgxMzMyMX0.He3bRKEVAk5Lg2yqGK_80Kw_dUaPwYU26coDu_Ba0uIl99H8Ga0K6SVtn4TXGmjIeMWrgoBPikj0MtKxxpKYPA'}})
            .then(function(response) {
                console.log(response);
                   vm.entradas = response.data;
                });
        }


        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;

            Comanda.save(vm.comanda, onSaveSuccess, onSaveError);

        }

        function onSaveSuccess (result) {
            // $scope.$emit('noon07App:comandaUpdate', result);

            console.log(result);

            var consumido = new ProdutoConsumido();

            consumido.comanda = result;
            consumido.valor = vm.entrada.valor;
            consumido.nome = vm.entrada.nome;
            consumido.idproduto = 0;
            consumido.quantidade = 1;
            consumido.identrada = vm.entrada.id;

            ProdutoConsumido.save(consumido, function(){

            $uibModalInstance.close(result);
            vm.isSaving = false;

            }, function(){});
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
