'use strict';

describe('Controller Tests', function() {

    describe('ProdutoConsumido Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProdutoConsumido, MockComanda;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProdutoConsumido = jasmine.createSpy('MockProdutoConsumido');
            MockComanda = jasmine.createSpy('MockComanda');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ProdutoConsumido': MockProdutoConsumido,
                'Comanda': MockComanda
            };
            createController = function() {
                $injector.get('$controller')("ProdutoConsumidoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'noon07App:produtoConsumidoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
