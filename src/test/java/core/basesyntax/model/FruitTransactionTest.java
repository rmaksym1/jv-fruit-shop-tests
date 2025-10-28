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
    public void getOperation_ReturnCorrectValues() {
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE,
                fruitTransaction.getOperation());
    }

    @Test
    public void getCode_ReturnCorrectValues() {
        String expectedCode = "b";
        Assertions.assertEquals(expectedCode, fruitTransaction.getOperation().getCode());
    }

    @Test
    public void getFruit_ReturnCorrectValues() {
        String expected = "banana";
        Assertions.assertEquals(expected, fruitTransaction.getFruit());
    }

    @Test
    public void getQuantity_ReturnCorrectValues() {
        int expected = 15;
        Assertions.assertEquals(expected, fruitTransaction.getQuantity());
    }
}
