package core.basesyntax.interfaces;

import core.basesyntax.service.impl.FruitTransaction;

public interface OperationHandler {
    public void apply(FruitTransaction tx);
}
