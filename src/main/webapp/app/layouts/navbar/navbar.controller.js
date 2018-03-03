(function() {
    'use strict';

    angular
        .module('noon07App')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$http','$state', 'Auth', 'Principal', 'ProfileService', 'LoginService'];

    function NavbarController ($http, $state, Auth, Principal, ProfileService, LoginService) {
        var vm = this;

        vm.setor = null;

        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;

        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
            
                $http.get('http://localhost:9000/api/extend-users?userId.equals=' + account.id, 
                {headers: { Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTUyMTgxMzMyMX0.He3bRKEVAk5Lg2yqGK_80Kw_dUaPwYU26coDu_Ba0uIl99H8Ga0K6SVtn4TXGmjIeMWrgoBPikj0MtKxxpKYPA'}})
            .then(function(response) {
                console.log(response.data);
                vm.setor = response.data[0].setor;
                console.log(vm.setor);
                
            });

            });
        }

        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.$state = $state;

        function login() {
            collapseNavbar();
            LoginService.open();
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            $state.go('home');
            location.reload();
        }

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }
    }
})();
