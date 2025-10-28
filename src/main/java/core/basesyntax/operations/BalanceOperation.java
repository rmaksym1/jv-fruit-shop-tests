package core.basesyntax.operations;

import static core.basesyntax.service.impl.StorageDao.storage;

import core.basesyntax.interfaces.OperationHandler;
import core.basesyntax.service.impl.FruitTransaction;

public class BalanceOperation implements OperationHandler {

    @Override
    public void apply(FruitTransaction tx) {
        if (tx == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        if (tx.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative: " + tx.getQuantity());
        }
        storage.put(tx.getFruit(), tx.getQuantity());
    }
}
