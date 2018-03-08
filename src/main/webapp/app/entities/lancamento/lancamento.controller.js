(function() {
    'use strict';

    angular
    .module('noon07App')
    .controller('LancamentoController', LancamentoController);

    LancamentoController.$inject = ['$http','$scope','Lancamento', 'LancamentoSearch','ProdutoConsumido', 'AlertService'];

    function LancamentoController($http, $scope, Lancamento, LancamentoSearch, ProdutoConsumido, AlertService) {

        var vm = this;

        vm.comanda;

        
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        vm.lancamentos = [];

        vm.cliente = null;
        vm.comandanaoencontrada = false;

        vm.produtos = [];
        vm.produtosadicionados = [];

        vm.isSaving = false;
        var i = 0;




        var currentLocation = window.location;

        // loadAll();

        function makeid() {
          var text = "";
          var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

          for (var i = 0; i < 5; i++)
            text += possible.charAt(Math.floor(Math.random() * possible.length));

        return text;
        }

    $scope.add = function(produto){

        var achou = false;

        for(var i = 0; i < vm.produtosadicionados.length; i++){

            if(vm.produtosadicionados[i].id == produto.id){
                achou = true;
                vm.produtosadicionados[i].quantidade++;
            }

        }

        if(!achou){
            produto.quantidade = 1;
            vm.produtosadicionados.push(produto);
        }


    }

    $scope.remove = function(produto){

        for(var i = 0; i < vm.produtosadicionados.length; i++){

            if(vm.produtosadicionados[i].id == produto.id){

                if(vm.produtosadicionados[i].quantidade > 1){
                    vm.produtosadicionados[i].quantidade--;
                }else{
                    if(vm.produtosadicionados[i].quantidade == 1){
                        vm.produtosadicionados.splice(i, 1);
                    }    
                }


            }

        }

    }

    $scope.exclui = function(produto){

        for(var i = 0; i < vm.produtosadicionados.length; i++){

            if(vm.produtosadicionados[i].id == produto.id){
                vm.produtosadicionados.splice(i, 1);
            }

        }

    }

    $scope.lancar = function(){

        vm.isSaving = true;

        i = 0;

        var consumido = new ProdutoConsumido();

        consumido.comanda = vm.comanda;
        consumido.valor = vm.produtosadicionados[i].valor;
        consumido.nome = vm.produtosadicionados[i].nome;
        consumido.idproduto = vm.produtosadicionados[i].id;
        consumido.quantidade = vm.produtosadicionados[i].quantidade;
        consumido.identrada = 0;

        ProdutoConsumido.save(consumido, onSaveSuccess, onSaveError);
                // console.log(consumido);

                // vm.isSaving = false
                // vm.clear();
                // AlertService.info("Produtos lançados!");

            }

            function onSaveSuccess (result) {

                if((i+1) < vm.produtosadicionados.length){
                    i++;
                    var consumido = new ProdutoConsumido();

                    consumido.comanda = vm.comanda;
                    consumido.valor = vm.produtosadicionados[i].valor;
                    consumido.nome = vm.produtosadicionados[i].nome;
                    consumido.idproduto = vm.produtosadicionados[i].id;
                    consumido.quantidade = vm.produtosadicionados[i].quantidade;

                    ProdutoConsumido.save(consumido, onSaveSuccess, onSaveError);
                }else{
                    vm.isSaving = false
                    vm.clear();
                    AlertService.info("Produtos lançados!");
                }

            }

            function onSaveError () {
                // vm.isSaving = false;
            }


            $scope.buscaproduto = function(){
                console.log(vm.nomeproduto);


                if(vm.nomeproduto==null || vm.nomeproduto==""){
                    // loadAll();
                    vm.produtos = [];
                }else{
                    $http.get('http://'+currentLocation.host+'/api/produtos?nome.contains='+ vm.nomeproduto, 
                        {headers: { Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTUyMTgxMzMyMX0.He3bRKEVAk5Lg2yqGK_80Kw_dUaPwYU26coDu_Ba0uIl99H8Ga0K6SVtn4TXGmjIeMWrgoBPikj0MtKxxpKYPA'}})
                    .then(function(response) {
                        console.log(response);

                        vm.produtos = response.data;
                        // if(response.data.length > 0){
                        //     vm.produto = response.data[0].nome;
                        //     vm.produtonaoencontrado = false;
                        // }else{
                        //     vm.produtonaoencontrado = true;
                        //     vm.produto = null;
                        // }
                    });
                }


                
            }

            $scope.buscacomanda = function(){

             $http.get('http://'+currentLocation.host+'/api/comandas?status.in=ABERTA&numero.equals='+ vm.numerocomanda, 
                {headers: { Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTUyMTgxMzMyMX0.He3bRKEVAk5Lg2yqGK_80Kw_dUaPwYU26coDu_Ba0uIl99H8Ga0K6SVtn4TXGmjIeMWrgoBPikj0MtKxxpKYPA'}})
             .then(function(response) {
                console.log(response);

                if(response.data.length > 0){
                    vm.comanda = response.data[0];
                    vm.cliente = response.data[0].cliente.nome;
                    vm.comandanaoencontrada = false;
                }else{
                    vm.comandanaoencontrada = true;
                    vm.cliente = null;
                }
            });
         }

         function loadAll() {

            vm.comanda = null;

            vm.lancamentos = [];

            vm.cliente = null;
            vm.comandanaoencontrada = false;

            vm.produtos = [];
            vm.produtosadicionados = [];

            vm.isSaving = false;        

            $http.get('http://'+currentLocation.host+'/api/produtos', 
                {headers: { Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTUyMTgxMzMyMX0.He3bRKEVAk5Lg2yqGK_80Kw_dUaPwYU26coDu_Ba0uIl99H8Ga0K6SVtn4TXGmjIeMWrgoBPikj0MtKxxpKYPA'}})
            .then(function(response) {
                console.log(response);

                vm.produtos = response.data;
                        // if(response.data.length > 0){
                        //     vm.produto = response.data[0].nome;
                        //     vm.produtonaoencontrado = false;
                        // }else{
                        //     vm.produtonaoencontrado = true;
                        //     vm.produto = null;
                        // }
                    });

        // Lancamento.query(function(result) {
        //     vm.lancamentos = result;
        //     vm.searchQuery = null;
        // });
    }

    function search() {
        if (!vm.searchQuery) {
            return vm.loadAll();
        }
        LancamentoSearch.query({query: vm.searchQuery}, function(result) {
            vm.lancamentos = result;
            vm.currentSearch = vm.searchQuery;
        });
    }

    function clear() {

        vm.comanda = null;

        vm.numerocomanda = null;

        vm.nomeproduto = null;

        vm.lancamentos = [];

        vm.cliente = null;
        vm.comandanaoencontrada = false;

        vm.produtos = [];
        vm.produtosadicionados = [];

        vm.isSaving = false;     

        i = 0;

    }


}
})();
