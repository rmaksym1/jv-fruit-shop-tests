package core.basesyntax.service.impl;

import core.basesyntax.interfaces.OperationHandler;
import core.basesyntax.interfaces.OperationStrategy;
import core.basesyntax.interfaces.ShopService;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShopServiceTest {
    private final Map<FruitTransaction.Operation, OperationHandler> operationHandlers
            = new HashMap<>();
    private FruitTransaction fruitTransaction;
    private final OperationStrategy operationStrategy
            = new OperationStrategyImpl(operationHandlers);
    private final ShopService shopService = new ShopServiceImpl(operationStrategy);

    @Test
    void nullTransactionList_NotOk() {
        Assertions.assertThrows(NullPointerException.class, () -> shopService.process(null));
    }

    @Test
    void emptyTransactionList_Ok() {
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        Assertions.assertDoesNotThrow(() -> shopService.process(fruitTransactionList));
    }

    @Test
    void singleNoOperationTransactionList_Ok() {
        OperationStrategy fakeStrategy = new OperationStrategy() {
            @Override
            public OperationHandler get(FruitTransaction.Operation operation) {
                return tx -> {};
            }
        };

        ShopService shopService = new ShopServiceImpl(fakeStrategy);

        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 32),
                new FruitTransaction(null, "banana", 3)
        );

        Assertions.assertDoesNotThrow(() -> shopService.process(transactions));
    }

    @Test
    void singleNullTransactionList_Ok() {
        OperationStrategy fakeStrategy = new OperationStrategy() {
            @Override
            public OperationHandler get(FruitTransaction.Operation operation) {
                return tx -> {};
            }
        };

        final ShopService shopService = new ShopServiceImpl(fakeStrategy);
        final List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 10));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 32));
        transactions.add(null);

        Assertions.assertDoesNotThrow(() -> shopService.process(transactions));
    }

    @Test
    void validTransactionList_Ok() {
        OperationStrategy fakeStrategy = new OperationStrategy() {
            @Override
            public OperationHandler get(FruitTransaction.Operation operation) {
                return tx -> {};
            }
        };

        final ShopService shopService = new ShopServiceImpl(fakeStrategy);
        final List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 10));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 32));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 26));

        Assertions.assertDoesNotThrow(() -> shopService.process(transactions));
    }
}
