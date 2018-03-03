'use strict';

describe('Controller Tests', function() {

    describe('Comanda Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockComanda, MockProdutoConsumido, MockCliente;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockComanda = jasmine.createSpy('MockComanda');
            MockProdutoConsumido = jasmine.createSpy('MockProdutoConsumido');
            MockCliente = jasmine.createSpy('MockCliente');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Comanda': MockComanda,
                'ProdutoConsumido': MockProdutoConsumido,
                'Cliente': MockCliente
            };
            createController = function() {
                $injector.get('$controller')("ComandaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'noon07App:comandaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
