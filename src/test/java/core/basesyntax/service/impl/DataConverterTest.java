package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.interfaces.DataConverter;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DataConverterTest {
    private static final Path path = Path.of("src/main/resources/testReport.csv");
    private final DataConverter converter = new DataConverterImpl();

    @Test
    public void convert_nullData_NotOk() {
        assertThrows(IllegalArgumentException.class, () ->
                converter.convertToTransaction(null));
    }

    @Test
    public void convert_emptyList_NotOk() {
        assertThrows(IllegalArgumentException.class, () ->
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
        assertThrows(IllegalArgumentException.class, () ->
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
        assertThrows(IllegalArgumentException.class, () ->
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
        assertDoesNotThrow(() ->
                converter.convertToTransaction(list));
    }
}
