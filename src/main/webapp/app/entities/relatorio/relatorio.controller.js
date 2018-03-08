(function() {
    'use strict';

    angular
    .module('noon07App')
    .controller('RelatorioController', RelatorioController);

    RelatorioController.$inject = ['$http','$scope','Relatorio', 'RelatorioSearch'];

    function RelatorioController($http, $scope, Relatorio, RelatorioSearch) {

        var vm = this;

        vm.dia = "";
        vm.mes = "";
        vm.ano = "";

        vm.relatorios = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        vm.comandas = [];

        vm.numerodecomandas = 0;
        vm.consumo = 0;

        vm.consumoprodutos = 0;
        vm.consumoentradas = 0;

        vm.produtos = [];
        vm.entradas = [];

        var comandasquery = "";

        var currentLocation = window.location;

        vm.gerado = false;

        loadAll();

        $scope.datadehoje = function(){
            var data = new Date();
            vm.dia = data.getDate();
            vm.mes = data.getMonth() + 1;
            vm.ano = data.getFullYear();
        }

        function pad(num, size) {
            var s = num+"";
            while (s.length < size) s = "0" + s;
            return s;
        }

        $scope.gerarrelatiorio = function(){

            vm.gerado = true;

            vm.consumo = 0;

            vm.consumoprodutos = 0;
            vm.consumoentradas = 0;

            vm.produtos = [];

            comandasquery = "";

            var dataquery = "";

            if(vm.dia!=""&&vm.mes!=""&&vm.ano!=""){
                console.log("Pesquisando por dia");
            }

            // console.log('http://'+currentLocation.host+'/api/comandas?status.in=FECHADA&data.equals='+vm.ano+"-"+pad(vm.mes,2)+"-"+pad(vm.dia,2));

            $http.get('http://'+currentLocation.host+'/api/comandas?status.in=FECHADA&data.equals='+vm.ano+"-"+pad(vm.mes,2)+"-"+pad(vm.dia,2), 
                {headers: { Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTUyMTgxMzMyMX0.He3bRKEVAk5Lg2yqGK_80Kw_dUaPwYU26coDu_Ba0uIl99H8Ga0K6SVtn4TXGmjIeMWrgoBPikj0MtKxxpKYPA'}})
            .then(function(response) {

                if(response.data.length > 0){

                    vm.comandas = response.data;
                    vm.numerodecomandas = vm.comandas.length;
                    console.log(vm.comandas);

                    for(var i=0;i<vm.comandas.length;i++){
                    // console.log(i);
                    // console.log(vm.comandas[i]);
                    vm.consumo += vm.comandas[i].total;
                    console.log("Comanda " + vm.comandas[i].numero + " Total: " + vm.comandas[i].total);
                    // comandasquery.push(vm.comandas[i].id);
                    if(i==0){
                        comandasquery = comandasquery + "comandaId.in=" + vm.comandas[i].id;
                    }else{
                        comandasquery = comandasquery + "&comandaId.in=" + vm.comandas[i].id;
                    }
                    
                }


                consumoComanda();
            }


        });

        };

        function consumoComanda(){

            console.log(comandasquery);

            $http.get('http://'+currentLocation.host+'/api/produto-consumidos?' + comandasquery, 
                {headers: { Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTUyMTgxMzMyMX0.He3bRKEVAk5Lg2yqGK_80Kw_dUaPwYU26coDu_Ba0uIl99H8Ga0K6SVtn4TXGmjIeMWrgoBPikj0MtKxxpKYPA'}})
            .then(function(response) {
                console.log(response);

                for(var i = 0; i < response.data.length; i++){

                    var achou = false;

                    if(response.data[i].idproduto!=0){
                        for(var j = 0; j < vm.produtos.length; j++){

                        if(vm.produtos[j].idproduto == response.data[i].idproduto){
                            achou = true;
                            vm.produtos[j].quantidade += response.data[i].quantidade;
                            vm.consumoprodutos += response.data[i].quantidade*response.data[i].valor;
                            break;
                        }

                    }

                    if(!achou){
                        vm.produtos.push(response.data[i]);
                        vm.consumoprodutos += response.data[i].quantidade*response.data[i].valor;
                    }

                    }else{
                        if(response.data[i].identrada!=0){
                            for(var j = 0; j < vm.entradas.length; j++){

                        if(vm.entradas[j].identrada == response.data[i].identrada){
                            achou = true;
                            vm.entradas[j].quantidade += response.data[i].quantidade;
                            vm.consumoentradas += response.data[i].valor;
                            break;
                        }

                    }

                    if(!achou){
                        vm.entradas.push(response.data[i]);
                        vm.consumoentradas += response.data[i].valor;
                    }
                        }
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
