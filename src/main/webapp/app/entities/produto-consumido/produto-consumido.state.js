(function() {
    'use strict';

    angular
        .module('noon07App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('produto-consumido', {
            parent: 'entity',
            url: '/produto-consumido',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'noon07App.produtoConsumido.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/produto-consumido/produto-consumidos.html',
                    controller: 'ProdutoConsumidoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('produtoConsumido');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('produto-consumido-detail', {
            parent: 'produto-consumido',
            url: '/produto-consumido/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'noon07App.produtoConsumido.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/produto-consumido/produto-consumido-detail.html',
                    controller: 'ProdutoConsumidoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('produtoConsumido');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ProdutoConsumido', function($stateParams, ProdutoConsumido) {
                    return ProdutoConsumido.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'produto-consumido',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('produto-consumido-detail.edit', {
            parent: 'produto-consumido-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/produto-consumido/produto-consumido-dialog.html',
                    controller: 'ProdutoConsumidoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProdutoConsumido', function(ProdutoConsumido) {
                            return ProdutoConsumido.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('produto-consumido.new', {
            parent: 'produto-consumido',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/produto-consumido/produto-consumido-dialog.html',
                    controller: 'ProdutoConsumidoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idproduto: null,
                                nome: null,
                                valor: null,
                                quantidade: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('produto-consumido', null, { reload: 'produto-consumido' });
                }, function() {
                    $state.go('produto-consumido');
                });
            }]
        })
        .state('produto-consumido.edit', {
            parent: 'produto-consumido',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/produto-consumido/produto-consumido-dialog.html',
                    controller: 'ProdutoConsumidoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProdutoConsumido', function(ProdutoConsumido) {
                            return ProdutoConsumido.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('produto-consumido', null, { reload: 'produto-consumido' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('produto-consumido.delete', {
            parent: 'produto-consumido',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/produto-consumido/produto-consumido-delete-dialog.html',
                    controller: 'ProdutoConsumidoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ProdutoConsumido', function(ProdutoConsumido) {
                            return ProdutoConsumido.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('produto-consumido', null, { reload: 'produto-consumido' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
