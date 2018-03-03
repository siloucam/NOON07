(function() {
    'use strict';

    angular
        .module('noon07App')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$http','$scope', 'Principal', 'LoginService', '$state'];

    function HomeController ($http, $scope, Principal, LoginService, $state) {
        var vm = this;

        vm.account = null;

        vm.setor = null;

        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
            location.reload();
        });

        getAccount();
 
        $scope.printsetor = function(setor){
            if(setor=='RECEPCAO') return "Recepção";
            if(setor=='GERENCIA') return "Gerência";
            if(setor=='CAIXA') return "Caixa";
            if(setor=='BAR') return "Bar";
                    }

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
                console.log(vm.account);

                $http.get('http://localhost:9000/api/extend-users?userId.equals=' + vm.account.id, 
                {headers: { Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTUyMTgxMzMyMX0.He3bRKEVAk5Lg2yqGK_80Kw_dUaPwYU26coDu_Ba0uIl99H8Ga0K6SVtn4TXGmjIeMWrgoBPikj0MtKxxpKYPA'}})
            .then(function(response) {
                console.log(response.data);
                vm.setor = response.data[0].setor;
                console.log(vm.setor);
                
            });

            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
