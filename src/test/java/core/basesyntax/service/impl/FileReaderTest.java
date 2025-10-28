package core.basesyntax.service.impl;

import core.basesyntax.interfaces.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileReaderTest {
    private static final String NON_EXISTENT_PATH = "src/main/resources/nothing.csv";
    private static final Path EMPTY_PATH = Path.of("src/main/resources/emptyReport.csv");
    private static final Path FILE_PATH = Path.of("src/main/resources/fruitsReport.csv");
    private final FileReader fileReader = new FileReaderImpl();

    @Test
    void nonExistentFile_notOk() throws IOException {
        Assertions.assertThrows(UncheckedIOException.class,
                () -> fileReader.read(NON_EXISTENT_PATH));
    }

    @Test
    void nullPath_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> fileReader.read(null));
    }

    @Test
    void read_EmptyFile_ReturnsEmptyList_Ok() throws IOException {
        Files.createFile(EMPTY_PATH);
        Assertions.assertEquals(Collections.emptyList(),
                fileReader.read(String.valueOf(EMPTY_PATH)));
        Files.deleteIfExists(EMPTY_PATH);
    }

    @Test
    void read_ReportFile_ReturnsList_Ok() throws IOException {
        String str = "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,100\n"
                + "s,banana,100\n";

        Files.createFile(FILE_PATH);
        Files.writeString(FILE_PATH, str);

        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100"
        );

        List<String> actual = Assertions.assertDoesNotThrow(
                () -> fileReader.read(String.valueOf(FILE_PATH))
        );

        Assertions.assertEquals(expected, fileReader.read(String.valueOf(FILE_PATH)));
        Files.deleteIfExists(FILE_PATH);
    }
}
