(function() {
    'use strict';

    angular
    .module('noon07App')
    .controller('ComandaDialogController', ComandaDialogController);

    ComandaDialogController.$inject = ['Principal','$http','AlertService', '$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Comanda', 'ProdutoConsumido', 'Cliente'];

    function ComandaDialogController (Principal, $http, AlertService, $timeout, $scope, $stateParams, $uibModalInstance, entity, Comanda, ProdutoConsumido, Cliente) {
        var vm = this;

        vm.comanda = entity;

        vm.corfirmafechar = false;
        vm.corfirmacancelar = false;                                                   

        console.log(vm.comanda);

        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.produtoconsumidos = ProdutoConsumido.query();
        vm.clientes = Cliente.query();

        vm.produtos = [];

        vm.account = null;

        vm.setor = null;

        var currentLocation = window.location;
        
        getAccount();

        loadConsumo();

        $scope.removeproduto = function(produto){
            ProdutoConsumido.delete(produto, function(){
                loadConsumo();
            });
        }

         function getAccount() {
            Principal.identity().then(function(account) {

                if(account){
                    vm.account = account;
                    vm.isAuthenticated = Principal.isAuthenticated;
                    console.log(vm.account);

                    $http.get('http://'+currentLocation.host+'/api/extend-users?userId.equals=' + vm.account.id, 
                        {headers: { Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTUyMTgxMzMyMX0.He3bRKEVAk5Lg2yqGK_80Kw_dUaPwYU26coDu_Ba0uIl99H8Ga0K6SVtn4TXGmjIeMWrgoBPikj0MtKxxpKYPA'}})
                    .then(function(response) {
                        console.log(response.data);
                        vm.setor = response.data[0].setor;
                        console.log(vm.setor);
                    });}

                });
        }

        $scope.fecharcomanda = function(){

            console.log('fechar');

            vm.isSaving = true;
            vm.comanda.status = 'FECHADA';

            Comanda.update(vm.comanda, function(){
                vm.isSaving = false;
                console.log("Atualizou");
                $uibModalInstance.dismiss('cancel');
            }, function(){});

        }

        function loadConsumo(){

            vm.produtos = [];

            $http.get('http://'+currentLocation.host+'/api/produto-consumidos?comandaId.equals=' + vm.comanda.id, 
                {headers: { Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTUyMTgxMzMyMX0.He3bRKEVAk5Lg2yqGK_80Kw_dUaPwYU26coDu_Ba0uIl99H8Ga0K6SVtn4TXGmjIeMWrgoBPikj0MtKxxpKYPA'}})
            .then(function(response) {
                console.log(response);

                for(var i = 0; i < response.data.length; i++){

                    var achou = false;

                    for(var j = 0; j < vm.produtos.length; j++){

                        if(vm.produtos[j].idproduto == response.data[i].idproduto){
                            achou = true;
                            vm.produtos[j].quantidade++;
                            break;
                        }

                    }

                    if(!achou){
                        vm.produtos.push(response.data[i]);
                    }

                }

                // vm.produtos = response.data;
                        vm.comanda.total = 0;
                        //Calcula Total
                        for(var i = 0; i < vm.produtos.length; i++){

                            vm.comanda.total += (vm.produtos[i].quantidade*vm.produtos[i].valor);

                        }

                    });

        }

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
            $uibModalInstance.close();
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
