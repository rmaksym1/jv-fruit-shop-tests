package core.basesyntax.service.impl;

import static core.basesyntax.service.impl.StorageDao.storage;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.interfaces.ReportGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
    private final ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @BeforeEach
    void setUp() {
        storage.clear();
    }

    @Test
    void getReport_EmptyStorage_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator();
        assertEquals(expected, reportGenerator.getReport());
    }

    @Test
    void getReport_ValidStorage_Ok() {
        storage.put("banana", 45);
        storage.put("apple", 20);
        storage.put("orange", 36);

        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,20" + System.lineSeparator()
                + "banana,45" + System.lineSeparator()
                + "orange,36" + System.lineSeparator();

        assertDoesNotThrow(reportGenerator::getReport);

        assertEquals(expected, reportGenerator.getReport());
    }
}
