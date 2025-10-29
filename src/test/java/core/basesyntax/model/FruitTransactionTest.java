package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    private static final String INVALID_CODE = "i";
    private FruitTransaction fruitTransaction;

    @BeforeEach
    public void setup() {
        fruitTransaction
                = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana",
                15);
    }

    @Test
    public void getOperation_Ok() {
        assertEquals(FruitTransaction.Operation.BALANCE,
                fruitTransaction.getOperation());
    }

    @Test
    public void getCode_Ok() {
        String expectedCode = "b";
        assertEquals(expectedCode, fruitTransaction.getOperation().getCode());
    }

    @Test
    public void getFruit_Ok() {
        String expected = "banana";
        assertEquals(expected, fruitTransaction.getFruit());
    }

    @Test
    public void getQuantity_Ok() {
        int expected = 15;
        assertEquals(expected, fruitTransaction.getQuantity());
    }

    @Test
    public void invalidCode_fromCode_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode(INVALID_CODE));
    }
}
