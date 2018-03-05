(function() {
    'use strict';

    angular
        .module('noon07App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('relatorio', {
            parent: 'entity',
            url: '/relatorio',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'noon07App.relatorio.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/relatorio/relatorios.html',
                    controller: 'RelatorioController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('relatorio');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('relatorio-detail', {
            parent: 'relatorio',
            url: '/relatorio/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'noon07App.relatorio.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/relatorio/relatorio-detail.html',
                    controller: 'RelatorioDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('relatorio');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Relatorio', function($stateParams, Relatorio) {
                    return Relatorio.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'relatorio',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('relatorio-detail.edit', {
            parent: 'relatorio-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/relatorio/relatorio-dialog.html',
                    controller: 'RelatorioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Relatorio', function(Relatorio) {
                            return Relatorio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('relatorio.new', {
            parent: 'relatorio',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/relatorio/relatorio-dialog.html',
                    controller: 'RelatorioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('relatorio', null, { reload: 'relatorio' });
                }, function() {
                    $state.go('relatorio');
                });
            }]
        })
        .state('relatorio.edit', {
            parent: 'relatorio',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/relatorio/relatorio-dialog.html',
                    controller: 'RelatorioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Relatorio', function(Relatorio) {
                            return Relatorio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('relatorio', null, { reload: 'relatorio' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('relatorio.delete', {
            parent: 'relatorio',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/relatorio/relatorio-delete-dialog.html',
                    controller: 'RelatorioDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Relatorio', function(Relatorio) {
                            return Relatorio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('relatorio', null, { reload: 'relatorio' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
