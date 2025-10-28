package core.basesyntax.service.impl;

import core.basesyntax.interfaces.DataConverter;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DataConverterTest {
    private static final Path path = Path.of("src/main/resources/testReport.csv");
    private final DataConverter converter = new DataConverterImpl();

    @Test
    public void convert_nullData_NotOk() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                converter.convertToTransaction(null));
    }

    @Test
    public void convert_emptyList_NotOk() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                converter.convertToTransaction(List.of()));
    }

    @Test
    public void convert_corruptedLine_NotOk() {
        List<String> list = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,120",
                "s,banana."
        );
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                converter.convertToTransaction(list));
    }

    @Test
    public void convert_negativeQuantity_NotOk() {
        List<String> list = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,-120",
                "s,banana,30"
        );
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                converter.convertToTransaction(list));
    }

    @Test
    public void convert_validData_Ok() {
        List<String> list = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,120",
                "s,banana,30"
        );
        Assertions.assertDoesNotThrow(() ->
                converter.convertToTransaction(list));
    }
}
