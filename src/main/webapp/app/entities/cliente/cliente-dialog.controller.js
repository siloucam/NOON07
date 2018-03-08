(function() {
    'use strict';

    angular
    .module('noon07App')
    .controller('ClienteDialogController', ClienteDialogController);

    ClienteDialogController.$inject = ['ProdutoConsumido', '$http','AlertService','$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cliente', 'Comanda'];

    function ClienteDialogController (ProdutoConsumido, $http, AlertService, $timeout, $scope, $stateParams, $uibModalInstance, entity, Cliente, Comanda) {
        var vm = this;

        vm.cliente = entity;
        vm.clear = clear;
        vm.save = save;
        vm.comandas = Comanda.query();

        vm.entradas = [];

        vm.numerocomanda = null;

        vm.numeroindisponivel = false;

        var currentLocation = window.location;

        loadEntradas();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        $scope.verificanumero = function(){

            if(vm.numerocomanda!=null){
                $http.get('http://'+currentLocation.host+'/api/comandas?status.in=ABERTA&numero.equals='+ vm.numerocomanda, 
                    {headers: { Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTUyMTgxMzMyMX0.He3bRKEVAk5Lg2yqGK_80Kw_dUaPwYU26coDu_Ba0uIl99H8Ga0K6SVtn4TXGmjIeMWrgoBPikj0MtKxxpKYPA'}})
                .then(function(response) {                
                    if(response.data.length > 0){
                       vm.numeroindisponivel = true;
                   }else{
                    vm.numeroindisponivel = false;
                }
            });
            }else{
                vm.numeroindisponivel = false;
            }

        }

        function loadEntradas(){
            $http.get('http://'+currentLocation.host+'/api/entradas', 
                {headers: { Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTUyMTgxMzMyMX0.He3bRKEVAk5Lg2yqGK_80Kw_dUaPwYU26coDu_Ba0uIl99H8Ga0K6SVtn4TXGmjIeMWrgoBPikj0MtKxxpKYPA'}})
            .then(function(response) {
                console.log(response);
                   vm.entradas = response.data;
                });
        }

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

                Comanda.save(comanda,function(result){

                    var consumido = new ProdutoConsumido();

                    consumido.comanda = result;
                    consumido.valor = vm.entrada.valor;
                    consumido.nome = vm.entrada.nome;
                    consumido.idproduto = 0;
                    consumido.quantidade = 1;
                    consumido.identrada = vm.entrada.id;

                    ProdutoConsumido.save(consumido, function(){
                        console.log("Salvou");
                        AlertService.info("Cliente salvo com sucesso!");
                        AlertService.info("Comanda aberta com sucesso!");
                        $uibModalInstance.close(result);
                        vm.isSaving = false;
                         }, function(){})
            // $scope.$emit('noon07App:clienteUpdate', result);
            
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
