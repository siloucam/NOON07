(function() {
    'use strict';

    angular
    .module('noon07App')
    .controller('ClienteController', ClienteController);

    ClienteController.$inject = ['$http','$uibModal', '$scope','Cliente', 'ClienteSearch', 'ParseLinks', 'AlertService', 'paginationConstants'];

    function ClienteController($http, $uibModal, $scope, Cliente, ClienteSearch, ParseLinks, AlertService, paginationConstants) {

        var vm = this;

        vm.clientes = [];
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.page = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;
        vm.clear = clear;
        vm.loadAll = loadAll;
        vm.search = search;

        // loadAll();

        $scope.debug = function(){
            AlertService.info("Comanda aberta com sucesso!");
        }

        $scope.vesetemcomanda = function(cliente){
            $http.get('http://localhost:9000/api/comandas?clienteId.equals='+ cliente.id, 
                {headers: { Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTUyMTgxMzMyMX0.He3bRKEVAk5Lg2yqGK_80Kw_dUaPwYU26coDu_Ba0uIl99H8Ga0K6SVtn4TXGmjIeMWrgoBPikj0MtKxxpKYPA'}})
            .then(function(response) {
                        // console.log(response);
                        if(response.data.length > 0){
                            console.log("Tem comanda!");
                            return true;
                        }
                    });
        }        

        $scope.newcomanda = function(cliente){

            $http.get('http://localhost:9000/api/comandas?status.in=ABERTA&clienteId.equals='+ cliente.id, 
                {headers: { Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTUyMTgxMzMyMX0.He3bRKEVAk5Lg2yqGK_80Kw_dUaPwYU26coDu_Ba0uIl99H8Ga0K6SVtn4TXGmjIeMWrgoBPikj0MtKxxpKYPA'}})
            .then(function(response) {
                        // console.log(response);
                        if(response.data.length > 0){
                           AlertService.error(cliente.nome + " já possui uma comanda aberta! Número: " + response.data[0].numero);
                           console.log("Tem comanda!");
                           return true;
                       }else{

                        $uibModal.open({
                            templateUrl: 'app/entities/cliente/clientes-comanda-dialog.html',
                            controller: 'ClienteComandaDialogController',
                            controllerAs: 'vm',
                            backdrop: 'static',
                            size: 'sm',
                            resolve: {
                                entity: function () {
                                    return {
                                        cliente: cliente,
                                        numero: null,
                                        data: new Date(),
                                        total: 0,
                                        status: 'ABERTA',
                                        id: null
                                    };
                                }
                            }
                        }).result.then(function() {
                    // $state.go('comanda', null, { reload: 'comanda' });
                    AlertService.info("Comanda aberta com sucesso!");
                    vm.clientes = [];
                }, function() {
                    // $state.go('comanda');
                });

                    }
                });
        }

        function loadAll () {
            if (vm.currentSearch) {
                ClienteSearch.query({
                    query: vm.currentSearch,
                    page: vm.page,
                    size: vm.itemsPerPage,
                    sort: sort()
                }, onSuccess, onError);
            } else {
                Cliente.query({
                    page: vm.page,
                    size: vm.itemsPerPage,
                    sort: sort()
                }, onSuccess, onError);
            }
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');

                for (var i = 0; i < data.length; i++) {
                    vm.clientes.push(data[i]);
                }

            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function reset () {
            vm.page = 0;
            vm.clientes = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }

        function clear () {
            vm.clientes = [];
            vm.links = {
                last: 0
            };
            vm.page = 0;
            vm.predicate = 'id';
            vm.reverse = true;
            vm.searchQuery = null;
            vm.currentSearch = null;
            vm.loadAll();
        }

        function search (searchQuery) {
            if (!searchQuery){
                return vm.clear();
            }
            vm.clientes = [];
            vm.links = {
                last: 0
            };
            vm.page = 0;
            vm.predicate = '_score';
            vm.reverse = false;
            vm.currentSearch = searchQuery;
            vm.loadAll();
        }
    }
})();
