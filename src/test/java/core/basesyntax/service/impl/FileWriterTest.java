package core.basesyntax.service.impl;

import core.basesyntax.interfaces.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileWriterTest {
    private static final String FILE_PATH = "src/main/resources/TestReport.csv";
    private final FileWriter fileWriter = new FileWriterImpl();

    @Test
    public void write_nullReport_notOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> fileWriter.write(null, FILE_PATH));
    }

    @Test
    public void write_emptyReport_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write("", FILE_PATH));
    }

    @Test
    public void write_nullReportPath_notOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> fileWriter.write("data", null));
    }

    @Test
    void write_NonExistentDirection_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileWriter.write("data", "UnknownNonExistentDirection/TestReport.csv"));
    }

    @Test
    void write_ExistentDirection_Ok() throws IOException {
        Path file = Files.createTempFile("testreport", ".csv");
        Assertions.assertDoesNotThrow(() -> fileWriter.write("data", file.toString()));
    }

    @Test
    public void write_emptyReportPath_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write("data", ""));
    }

    @Test
    public void write_reportFileDoesNotExist_Ok() {
        Assertions.assertDoesNotThrow(() -> fileWriter.write("data", FILE_PATH));
    }
}
