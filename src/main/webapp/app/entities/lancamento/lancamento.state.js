(function() {
    'use strict';

    angular
        .module('noon07App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lancamento', {
            parent: 'entity',
            url: '/lancamento',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'noon07App.lancamento.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lancamento/lancamentos.html',
                    controller: 'LancamentoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lancamento');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('lancamento-detail', {
            parent: 'lancamento',
            url: '/lancamento/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'noon07App.lancamento.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lancamento/lancamento-detail.html',
                    controller: 'LancamentoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lancamento');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Lancamento', function($stateParams, Lancamento) {
                    return Lancamento.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'lancamento',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('lancamento-detail.edit', {
            parent: 'lancamento-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lancamento/lancamento-dialog.html',
                    controller: 'LancamentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Lancamento', function(Lancamento) {
                            return Lancamento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lancamento.new', {
            parent: 'lancamento',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lancamento/lancamento-dialog.html',
                    controller: 'LancamentoDialogController',
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
                    $state.go('lancamento', null, { reload: 'lancamento' });
                }, function() {
                    $state.go('lancamento');
                });
            }]
        })
        .state('lancamento.edit', {
            parent: 'lancamento',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lancamento/lancamento-dialog.html',
                    controller: 'LancamentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Lancamento', function(Lancamento) {
                            return Lancamento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lancamento', null, { reload: 'lancamento' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lancamento.delete', {
            parent: 'lancamento',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lancamento/lancamento-delete-dialog.html',
                    controller: 'LancamentoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Lancamento', function(Lancamento) {
                            return Lancamento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lancamento', null, { reload: 'lancamento' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
