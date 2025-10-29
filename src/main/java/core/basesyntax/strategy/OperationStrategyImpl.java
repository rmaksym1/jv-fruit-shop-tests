package core.basesyntax.strategy;

import core.basesyntax.interfaces.OperationHandler;
import core.basesyntax.interfaces.OperationStrategy;
import core.basesyntax.service.impl.FruitTransaction;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<FruitTransaction.Operation,
            OperationHandler> handlers;

    public OperationStrategyImpl(Map<FruitTransaction.Operation,
            OperationHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public OperationHandler get(FruitTransaction.Operation op) {
        if (op == null || handlers.get(op) == null) {
            throw new IllegalArgumentException(String.format("Operation %s not found", op));
        }
        return handlers.get(op);
    }
}
