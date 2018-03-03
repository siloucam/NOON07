(function() {
    'use strict';

    angular
        .module('noon07App')
        .controller('ExtendUserDetailController', ExtendUserDetailController);

    ExtendUserDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ExtendUser', 'User'];

    function ExtendUserDetailController($scope, $rootScope, $stateParams, previousState, entity, ExtendUser, User) {
        var vm = this;

        vm.extendUser = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('noon07App:extendUserUpdate', function(event, result) {
            vm.extendUser = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
