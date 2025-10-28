package core.basesyntax.service.impl;

import core.basesyntax.interfaces.DataConverter;
import core.basesyntax.interfaces.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DataConverterTest {
    private static final Path path = Path.of("src/main/resources/testReport.csv");
    private DataConverter converter = new DataConverterImpl();
    private FileReader fileReader = new FileReaderImpl();

    @AfterEach
    public void teardown() throws IOException {
        Files.deleteIfExists(path);
    }

    @Test
    public void nullData_NotOk() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                converter.convertToTransaction(null));
    }

    @Test
    public void emptyData_NotOk() {
        Assertions.assertThrows(UncheckedIOException.class, () ->
                converter.convertToTransaction(fileReader.read("")));
    }

    @Test
    public void corruptedLine_NotOk() throws IOException {
        Files.createFile(path);
        Files.writeString(path, "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,120\n"
                + "s,banana,30\n"
                + "s,apple\n");
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                converter.convertToTransaction(fileReader.read(path.toString())));
    }

    @Test
    public void negativeQuantity_NotOk() throws IOException {
        Files.createFile(path);
        Files.writeString(path, "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,120\n"
                + "s,banana,-67\n");
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                converter.convertToTransaction(fileReader.read(path.toString())));
    }

    @Test
    public void validData_Ok() throws IOException {
        Files.createFile(path);
        Files.writeString(path, "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,120\n"
                + "s,banana,30\n");
        Assertions.assertDoesNotThrow(() ->
                converter.convertToTransaction(fileReader.read(path.toString())));
    }
}
