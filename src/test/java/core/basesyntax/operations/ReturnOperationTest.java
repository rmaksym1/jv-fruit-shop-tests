package core.basesyntax.operations;

import static core.basesyntax.service.impl.StorageDao.storage;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private FruitTransaction fruitTransaction;
    private ReturnOperation operation;

    @BeforeEach
    void setUp() {
        storage.clear();
        operation = new ReturnOperation();
    }

    @Test
    void apply_nullTransaction_NotOk() {
        assertThrows(IllegalArgumentException.class, () -> operation.apply(null));
    }

    @Test
    void apply_emptyTransaction_Ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 44);
        assertDoesNotThrow(() ->
                operation.apply(fruitTransaction));
    }

    @Test
    void apply_negativeQuantityTransaction_NotOk() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", -10);
        assertThrows(IllegalArgumentException.class, () ->
                operation.apply(fruitTransaction));
    }

    @Test
    void apply_validTransaction_Ok() {
        storage.put("apple", 67);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 20);
        assertDoesNotThrow(() ->
                operation.apply(fruitTransaction));

        assertEquals(87, storage.get("apple"));
    }
}
