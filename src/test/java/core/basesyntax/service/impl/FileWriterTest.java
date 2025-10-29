package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.interfaces.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

public class FileWriterTest {
    private static final String FILE_PATH = "src/main/resources/TestReport.csv";
    private final FileWriter fileWriter = new FileWriterImpl();

    @Test
    public void write_nullReport_notOk() {
        assertThrows(NullPointerException.class,
                () -> fileWriter.write(null, FILE_PATH));
    }

    @Test
    public void write_emptyReport_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write("", FILE_PATH));
    }

    @Test
    public void write_nullReportPath_notOk() {
        assertThrows(NullPointerException.class,
                () -> fileWriter.write("data", null));
    }

    @Test
    void write_NonExistentDirection_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write("data", "UnknownNonExistentDirection/TestReport.csv"));
    }

    @Test
    void write_ExistentDirection_Ok() throws IOException {
        Path file = Files.createTempFile("testreport", ".csv");
        assertDoesNotThrow(() -> fileWriter.write("data", file.toString()));
    }

    @Test
    public void write_emptyReportPath_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write("data", ""));
    }

    @Test
    public void write_reportFileDoesNotExist_Ok() {
        assertDoesNotThrow(() -> fileWriter.write("data", FILE_PATH));
    }
}
