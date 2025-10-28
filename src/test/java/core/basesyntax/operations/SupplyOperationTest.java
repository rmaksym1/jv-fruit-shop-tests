package core.basesyntax.operations;

import static core.basesyntax.service.impl.StorageDao.storage;

import core.basesyntax.service.impl.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private FruitTransaction fruitTransaction;
    private final SupplyOperation operation = new SupplyOperation();

    @BeforeEach
    void setUp() {
        storage.clear();
    }

    @Test
    void apply_nullTransaction_NotOk() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> operation.apply(null));
    }

    @Test
    void apply_emptyTransaction_Ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 44);
        Assertions.assertDoesNotThrow(()
                -> operation.apply(fruitTransaction));
    }

    @Test
    void apply_negativeQuantityTransaction_NotOk() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", -10);
        Assertions.assertThrows(IllegalArgumentException.class, ()
                -> operation.apply(fruitTransaction));
    }

    @Test
    void apply_validTransaction_Ok() {
        storage.put("apple", 34);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 34);
        Assertions.assertDoesNotThrow(() ->
                operation.apply(fruitTransaction));

        Assertions.assertEquals(68, storage.get("apple"));
    }
}
