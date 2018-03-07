(function() {
    'use strict';

    angular
        .module('noon07App')
        .controller('ProdutoController', ProdutoController);

    ProdutoController.$inject = ['$http','Produto', 'ProdutoSearch', 'ParseLinks', 'AlertService', 'paginationConstants'];

    function ProdutoController($http,Produto, ProdutoSearch, ParseLinks, AlertService, paginationConstants) {

        var vm = this;

        vm.produtos = [];
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

        loadAll();

        function loadAll () {
            if (vm.currentSearch) {
                ProdutoSearch.query({
                    query: vm.currentSearch,
                    page: vm.page,
                    size: vm.itemsPerPage,
                    sort: sort()
                }, onSuccess, onError);
            } else {
                Produto.query({
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
                    vm.produtos.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function reset () {
            vm.page = 0;
            vm.produtos = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }

        function clear () {
            vm.produtos = [];
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
            // if (!searchQuery){
            //     return vm.clear();
            // }
            // vm.produtos = [];
            // vm.links = {
            //     last: 0
            // };
            // vm.page = 0;
            // vm.predicate = '_score';
            // vm.reverse = false;
            // vm.currentSearch = searchQuery;
            // vm.loadAll();

            $http.get('http://'+currentLocation.host+'/api/produtos?nome.contains='+ searchQuery, 
                {headers: { Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTUyMTgxMzMyMX0.He3bRKEVAk5Lg2yqGK_80Kw_dUaPwYU26coDu_Ba0uIl99H8Ga0K6SVtn4TXGmjIeMWrgoBPikj0MtKxxpKYPA'}})
            .then(function(response) {
                        vm.produtos = response.data;
                    });


        }
    }
})();
