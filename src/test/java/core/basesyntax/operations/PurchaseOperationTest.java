package core.basesyntax.operations;

import static core.basesyntax.service.impl.StorageDao.storage;

import core.basesyntax.service.impl.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private FruitTransaction fruitTransaction;
    private final PurchaseOperation operation = new PurchaseOperation();

    @BeforeEach
    public void setup() {
        storage.put("apple", 50);
    }

    @AfterEach
    public void tearDown() {
        storage.clear();
    }

    @Test
    void apply_emptyTransaction_Ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 10);
        Assertions.assertDoesNotThrow(() -> operation.apply(fruitTransaction));
    }

    @Test
    void apply_nullTransaction_MaybeOk() {
        Assertions.assertDoesNotThrow(() -> operation.apply(null));
    }

    @Test
    void apply_negativeQuantityTransaction_NotOk() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", -100);
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                operation.apply(fruitTransaction));
    }

    @Test
    void apply_validTransaction_Ok() {
        storage.put("apple", 15);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 10);
        Assertions.assertDoesNotThrow(() ->
                operation.apply(fruitTransaction));

        Assertions.assertEquals(5, storage.get("apple"));
    }

    @Test
    void apply_notEnoughInStock_NotOk() {
        storage.put("apple", 15);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20);
        Assertions.assertThrows(RuntimeException.class, () ->
                operation.apply(fruitTransaction));
    }
}

