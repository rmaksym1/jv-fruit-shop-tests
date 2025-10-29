package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.interfaces.OperationHandler;
import core.basesyntax.operations.SupplyOperation;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {
    private final OperationHandler supplyHandler = new SupplyOperation();
    private OperationStrategyImpl operationStrategy;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    public void setup() {
        operationStrategy = new OperationStrategyImpl(
                Map.of(FruitTransaction.Operation.SUPPLY, supplyHandler));
    }

    @Test
    void get_NullOperation_NotOk() {
        assertThrows(IllegalArgumentException.class, () ->
                operationStrategy.get(null));
    }

    @Test
    void get_NonExistingOperation_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> operationStrategy.get(FruitTransaction.Operation.BALANCE));
    }

    @Test
    void get_Operation_Ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "orange", 44);
        OperationHandler handler = operationStrategy.get(fruitTransaction.getOperation());

        assertDoesNotThrow(() -> operationStrategy.get(fruitTransaction.getOperation()));
        assertEquals(supplyHandler, handler);
    }
}
