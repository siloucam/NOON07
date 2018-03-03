(function() {
    'use strict';

    angular
        .module('noon07App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('extend-user', {
            parent: 'entity',
            url: '/extend-user',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'noon07App.extendUser.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/extend-user/extend-users.html',
                    controller: 'ExtendUserController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('extendUser');
                    $translatePartialLoader.addPart('setor');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('extend-user-detail', {
            parent: 'extend-user',
            url: '/extend-user/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'noon07App.extendUser.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/extend-user/extend-user-detail.html',
                    controller: 'ExtendUserDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('extendUser');
                    $translatePartialLoader.addPart('setor');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ExtendUser', function($stateParams, ExtendUser) {
                    return ExtendUser.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'extend-user',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('extend-user-detail.edit', {
            parent: 'extend-user-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/extend-user/extend-user-dialog.html',
                    controller: 'ExtendUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ExtendUser', function(ExtendUser) {
                            return ExtendUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('extend-user.new', {
            parent: 'extend-user',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/extend-user/extend-user-dialog.html',
                    controller: 'ExtendUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                setor: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('extend-user', null, { reload: 'extend-user' });
                }, function() {
                    $state.go('extend-user');
                });
            }]
        })
        .state('extend-user.edit', {
            parent: 'extend-user',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/extend-user/extend-user-dialog.html',
                    controller: 'ExtendUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ExtendUser', function(ExtendUser) {
                            return ExtendUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('extend-user', null, { reload: 'extend-user' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('extend-user.delete', {
            parent: 'extend-user',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/extend-user/extend-user-delete-dialog.html',
                    controller: 'ExtendUserDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ExtendUser', function(ExtendUser) {
                            return ExtendUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('extend-user', null, { reload: 'extend-user' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
