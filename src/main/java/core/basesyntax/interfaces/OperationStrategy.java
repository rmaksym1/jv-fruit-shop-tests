package core.basesyntax.interfaces;

import core.basesyntax.service.impl.FruitTransaction;

public interface OperationStrategy {
    public OperationHandler get(FruitTransaction.Operation op);
}
