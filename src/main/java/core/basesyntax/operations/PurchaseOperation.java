package core.basesyntax.operations;

import static core.basesyntax.service.impl.StorageDao.storage;

import core.basesyntax.interfaces.OperationHandler;
import core.basesyntax.service.impl.FruitTransaction;

public class PurchaseOperation implements OperationHandler {

    @Override
    public void apply(FruitTransaction tx) {
        if (tx != null) {
            if (tx.getQuantity() < 0) {
                throw new IllegalArgumentException("Quantity cannot be negative: "
                        + tx.getQuantity());
            }
            if (storage.get(tx.getFruit()) >= tx.getQuantity()) {
                storage.put(tx.getFruit(), storage.getOrDefault(tx.getFruit(), 0)
                        - tx.getQuantity());
            } else {
                throw new RuntimeException("Not enough " + tx.getFruit());
            }
        }
    }
}
