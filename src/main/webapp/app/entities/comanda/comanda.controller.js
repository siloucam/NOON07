(function() {
    'use strict';

    angular
    .module('noon07App')
    .controller('ComandaController', ComandaController);

    ComandaController.$inject = ['$scope','$uibModal','$http','Comanda', 'ComandaSearch', 'ParseLinks', 'AlertService', 'paginationConstants'];

    function ComandaController($scope, $uibModal, $http, Comanda, ComandaSearch, ParseLinks, AlertService, paginationConstants) {

        var vm = this;

        vm.comandas = [];
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

        var currentLocation = window.location;

        clear();

        $scope.abrircomanda = function(comanda){
            $uibModal.open({
                templateUrl: 'app/entities/comanda/comanda-dialog.html',
                controller: 'ComandaDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    entity: comanda
                }
            }).result.then(function() {
                    // $state.go('^', {}, { reload: false });
                    clear();
                }, function() {
                    // $state.go('^');
                    clear();
                });
        }

        function loadAll () {
            if (vm.currentSearch) {
                ComandaSearch.query({
                    query: vm.currentSearch,
                    page: vm.page,
                    size: vm.itemsPerPage,
                    sort: sort()
                }, onSuccess, onError);
            } else {


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
                    vm.comandas.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function reset () {
            vm.page = 0;
            vm.comandas = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }

        function clear () {
            vm.comandas = [];
            vm.links = {
                last: 0
            };
            vm.page = 0;
            vm.predicate = 'id';
            vm.reverse = true;
            vm.searchQuery = null;
            vm.currentSearch = null;
            // vm.loadAll();


            $http.get('http://'+currentLocation.host+'/api/comandas?status.in=ABERTA', 
                {headers: { Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTUyMTgxMzMyMX0.He3bRKEVAk5Lg2yqGK_80Kw_dUaPwYU26coDu_Ba0uIl99H8Ga0K6SVtn4TXGmjIeMWrgoBPikj0MtKxxpKYPA'}})
            .then(function(response) {
                console.log(response);
                vm.comandas = response.data;
            });
        }

        function search (searchQuery) {
            
            $http.get('http://'+currentLocation.host+'/api/comandas?status.in=ABERTA&numero.equals='+ searchQuery, 
                {headers: { Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTUyMTgxMzMyMX0.He3bRKEVAk5Lg2yqGK_80Kw_dUaPwYU26coDu_Ba0uIl99H8Ga0K6SVtn4TXGmjIeMWrgoBPikj0MtKxxpKYPA'}})
            .then(function(response) {
                // console.log(response);
                // vm.comandas = response.data;
                if(response.data.length == 1){
                    $scope.abrircomanda(response.data[0]);
                }
            });


        }
    }
})();
