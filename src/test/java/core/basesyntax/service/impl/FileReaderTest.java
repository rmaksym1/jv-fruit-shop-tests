package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.interfaces.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderTest {
    private static final String NON_EXISTENT_PATH = "src/main/resources/nothing.csv";
    private static final Path EMPTY_PATH = Path.of("src/main/resources/emptyReport.csv");
    private FileReader fileReader;

    @BeforeEach
    public void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_nonExistentFile_notOk() throws IOException {
        assertThrows(UncheckedIOException.class,
                () -> fileReader.read(NON_EXISTENT_PATH));
    }

    @Test
    void read_nullPath_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fileReader.read(null));
    }

    @Test
    void read_EmptyFile_ReturnsEmptyList_Ok() throws IOException {
        Path file = Files.createTempFile("testreport", ".csv");
        assertEquals(Collections.emptyList(),
                fileReader.read(String.valueOf(file)));
    }

    @Test
    void read_ReportFile_ReturnsList_Ok() throws IOException {
        String str = "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,100\n"
                + "s,banana,100\n";

        Path file = Files.createTempFile("testreport", ".csv");
        Files.writeString(file, str);

        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100"
        );

        List<String> actual = assertDoesNotThrow(
                () -> fileReader.read(String.valueOf(file))
        );

        assertEquals(expected, fileReader.read(file.toString()));
    }
}
