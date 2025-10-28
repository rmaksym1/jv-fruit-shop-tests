package core.basesyntax.service.impl;

import core.basesyntax.interfaces.OperationStrategy;
import core.basesyntax.interfaces.ShopService;
import java.util.List;

public class ShopServiceImpl implements ShopService {
    private final OperationStrategy operationStrategy;

    public ShopServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void process(List<FruitTransaction> transactions) {
        for (FruitTransaction transaction : transactions) {
            if (transaction == null || transaction.getOperation() == null) {
                continue;
            }
            operationStrategy.get(transaction.getOperation())
                    .apply(transaction);
        }
    }
}
