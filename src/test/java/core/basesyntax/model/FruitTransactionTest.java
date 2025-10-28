package core.basesyntax.model;

import core.basesyntax.service.impl.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
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
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE,
                fruitTransaction.getOperation());
    }

    @Test
    public void getCode_Ok() {
        String expectedCode = "b";
        Assertions.assertEquals(expectedCode, fruitTransaction.getOperation().getCode());
    }

    @Test
    public void getFruit_Ok() {
        String expected = "banana";
        Assertions.assertEquals(expected, fruitTransaction.getFruit());
    }

    @Test
    public void getQuantity_Ok() {
        int expected = 15;
        Assertions.assertEquals(expected, fruitTransaction.getQuantity());
    }
}
