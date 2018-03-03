(function() {
    'use strict';

    angular
        .module('noon07App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('comanda', {
            parent: 'entity',
            url: '/comanda',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'noon07App.comanda.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/comanda/comandas.html',
                    controller: 'ComandaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('comanda');
                    $translatePartialLoader.addPart('statusComanda');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('comanda-detail', {
            parent: 'comanda',
            url: '/comanda/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'noon07App.comanda.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/comanda/comanda-detail.html',
                    controller: 'ComandaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('comanda');
                    $translatePartialLoader.addPart('statusComanda');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Comanda', function($stateParams, Comanda) {
                    return Comanda.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'comanda',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('comanda-detail.edit', {
            parent: 'comanda-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comanda/comanda-dialog.html',
                    controller: 'ComandaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Comanda', function(Comanda) {
                            return Comanda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('comanda.new', {
            parent: 'comanda',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comanda/comanda-dialog.html',
                    controller: 'ComandaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                numero: null,
                                data: null,
                                total: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('comanda', null, { reload: 'comanda' });
                }, function() {
                    $state.go('comanda');
                });
            }]
        })
        .state('comanda.edit', {
            parent: 'comanda',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comanda/comanda-dialog.html',
                    controller: 'ComandaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Comanda', function(Comanda) {
                            return Comanda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('comanda', null, { reload: 'comanda' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('comanda.delete', {
            parent: 'comanda',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comanda/comanda-delete-dialog.html',
                    controller: 'ComandaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Comanda', function(Comanda) {
                            return Comanda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('comanda', null, { reload: 'comanda' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
