(function() {
    'use strict';

    angular
    .module('noon07App')
    .controller('RelatorioController', RelatorioController);

    RelatorioController.$inject = ['$http','$scope','Relatorio', 'RelatorioSearch'];

    function RelatorioController($http, $scope, Relatorio, RelatorioSearch) {

        var vm = this;

        vm.relatorios = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        vm.comandas = [];

        vm.numerodecomandas = 0;
        vm.consumo = 0;

        vm.produtos = [];

        var comandasquery = "";

        vm.gerado = false;

        loadAll();

        $scope.datadehoje = function(){
            var data = new Date();
            vm.dia = data.getDate();
            vm.mes = data.getMonth() + 1;
            vm.ano = data.getFullYear();
        }

        $scope.gerarrelatiorio = function(){

            vm.gerado = true;

            vm.consumo = 0;
            vm.produtos = [];

            comandasquery = "";

            $http.get('http://localhost:9000/api/comandas', 
                {headers: { Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTUyMTgxMzMyMX0.He3bRKEVAk5Lg2yqGK_80Kw_dUaPwYU26coDu_Ba0uIl99H8Ga0K6SVtn4TXGmjIeMWrgoBPikj0MtKxxpKYPA'}})
            .then(function(response) {

                vm.comandas = response.data;
                vm.numerodecomandas = vm.comandas.length;
                console.log(vm.comandas);

                for(var i=0;i<vm.comandas.length;i++){
                    // console.log(i);
                    // console.log(vm.comandas[i]);
                    vm.consumo += vm.comandas[i].total;
                    // comandasquery.push(vm.comandas[i].id);
                    if(i==0){
                        comandasquery = comandasquery + "comandaId.in=" + vm.comandas[i].id;
                    }else{
                        comandasquery = comandasquery + "&comandaId.in=" + vm.comandas[i].id;
                    }
                    
                }

                consumoComanda();


            });

        };

        function consumoComanda(){

            console.log(comandasquery);

            $http.get('http://localhost:9000/api/produto-consumidos?' + comandasquery, 
                {headers: { Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTUyMTgxMzMyMX0.He3bRKEVAk5Lg2yqGK_80Kw_dUaPwYU26coDu_Ba0uIl99H8Ga0K6SVtn4TXGmjIeMWrgoBPikj0MtKxxpKYPA'}})
            .then(function(response) {
                console.log(response);

                for(var i = 0; i < response.data.length; i++){

                    var achou = false;

                    for(var j = 0; j < vm.produtos.length; j++){

                        if(vm.produtos[j].idproduto==0){
                            vm.produtos[j].nome = "Entradas";
                        }


                        if(vm.produtos[j].idproduto == response.data[i].idproduto){
                            achou = true;
                            vm.produtos[j].quantidade += response.data[i].quantidade;
                            break;
                        }

                    }

                    if(!achou){
                        vm.produtos.push(response.data[i]);
                    }

                }

                // vm.produtos = response.data;

                        // //Calcula Total
                        // for(var i = 0; i < vm.produtos.length; i++){

                        //     vm.comanda.total += (vm.produtos[i].quantidade*vm.produtos[i].valor);

                        // }

                    });
        }

        function loadAll() {
            // Relatorio.query(function(result) {
            //     vm.relatorios = result;
            //     vm.searchQuery = null;
            // });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            RelatorioSearch.query({query: vm.searchQuery}, function(result) {
                vm.relatorios = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
    })();
