(function() {
    'use strict';

    angular
        .module('noon07App')
        .controller('ExtendUserDialogController', ExtendUserDialogController);

    ExtendUserDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'ExtendUser', 'User'];

    function ExtendUserDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, ExtendUser, User) {
        var vm = this;

        vm.extendUser = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.extendUser.id !== null) {
                ExtendUser.update(vm.extendUser, onSaveSuccess, onSaveError);
            } else {
                ExtendUser.save(vm.extendUser, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('noon07App:extendUserUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
