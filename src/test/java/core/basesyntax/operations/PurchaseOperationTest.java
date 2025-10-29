package core.basesyntax.operations;

import static core.basesyntax.service.impl.StorageDao.storage;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private FruitTransaction fruitTransaction;
    private PurchaseOperation operation;

    @BeforeEach
    public void setup() {
        storage.put("apple", 50);
        operation = new PurchaseOperation();
    }

    @AfterEach
    public void tearDown() {
        storage.clear();
    }

    @Test
    void apply_emptyTransaction_Ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 10);
        assertDoesNotThrow(() -> operation.apply(fruitTransaction));
    }

    @Test
    void apply_nullTransaction_MaybeOk() {
        assertDoesNotThrow(() -> operation.apply(null));
    }

    @Test
    void apply_negativeQuantityTransaction_NotOk() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", -100);
        assertThrows(IllegalArgumentException.class, () ->
                operation.apply(fruitTransaction));
    }

    @Test
    void apply_validTransaction_Ok() {
        storage.put("apple", 15);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 10);
        assertDoesNotThrow(() ->
                operation.apply(fruitTransaction));

        assertEquals(5, storage.get("apple"));
    }

    @Test
    void apply_notEnoughInStock_NotOk() {
        storage.put("apple", 15);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20);
        assertThrows(RuntimeException.class, () ->
                operation.apply(fruitTransaction));
    }
}

