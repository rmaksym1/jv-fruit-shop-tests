package core.basesyntax.interfaces;

import core.basesyntax.service.impl.FruitTransaction;
import java.util.List;

public interface ShopService {
    public void process(List<FruitTransaction> transactions);
}
