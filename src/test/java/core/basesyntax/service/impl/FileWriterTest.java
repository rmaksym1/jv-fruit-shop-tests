package core.basesyntax.service.impl;

import core.basesyntax.interfaces.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileWriterTest {
    private static final String FILE_PATH = "src/main/resources/TestReport.csv";
    private final FileWriter fileWriter = new FileWriterImpl();

    @AfterEach
    void setUp() throws IOException {
        Files.deleteIfExists(Paths.get(FILE_PATH));
    }

    @Test
    public void nullReport_notOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> fileWriter.write(null, FILE_PATH));
    }

    @Test
    public void emptyReport_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write("", FILE_PATH));
    }

    @Test
    public void nullReportPath_notOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> fileWriter.write("data", null));
    }

    @Test
    void writeToNonExistentDirection_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileWriter.write("data", "UnknownNonExistentDirection/TestReport.csv"));
    }

    @Test
    void writeToExistentDirection_Ok() throws IOException {
        Files.createFile(Path.of(FILE_PATH));
        Assertions.assertDoesNotThrow(() -> fileWriter.write("data", FILE_PATH));
    }

    @Test
    public void emptyReportPath_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write("data", ""));
    }

    @Test
    public void reportFileDoesNotExist_Ok() {
        Assertions.assertDoesNotThrow(() -> fileWriter.write("data", FILE_PATH));
    }
}
