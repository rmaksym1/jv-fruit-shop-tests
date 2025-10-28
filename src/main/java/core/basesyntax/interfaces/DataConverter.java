package core.basesyntax.interfaces;

import core.basesyntax.service.impl.FruitTransaction;
import java.util.List;

public interface DataConverter {
    public List<FruitTransaction> convertToTransaction(List<String> data);
}
