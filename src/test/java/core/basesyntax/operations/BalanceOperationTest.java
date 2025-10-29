package core.basesyntax.operations;

import static core.basesyntax.service.impl.StorageDao.storage;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private FruitTransaction fruitTransaction;
    private BalanceOperation operation;

    @BeforeEach
    void setUp() {
        operation = new BalanceOperation();
    }

    @AfterEach
    void clearStorage() {
        storage.clear();
    }

    @Test
    void apply_nullTransaction_NotOk() {
        assertThrows(IllegalArgumentException.class, () -> operation.apply(null));
    }

    @Test
    void apply_emptyTransaction_Ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 44);
        assertDoesNotThrow(() ->
                operation.apply(fruitTransaction));
    }

    @Test
    void apply_negativeQuantityTransaction_NotOk() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", -100);
        assertThrows(IllegalArgumentException.class, () ->
                operation.apply(fruitTransaction));
    }

    @Test
    void apply_validTransaction_Ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 40);
        assertDoesNotThrow(() ->
                operation.apply(fruitTransaction));

        assertEquals(40, storage.get("apple"));
    }
}
