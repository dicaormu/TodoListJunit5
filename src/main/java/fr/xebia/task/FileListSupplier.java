package fr.xebia.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileListSupplier implements Supplier<List<String>> {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private String filename;

    public FileListSupplier(String filename) {
        this.filename = filename;
    }

    @Override
    public List<String> get() {
        File file = new File(filename);
        try (Stream<String> lines = Files.lines(file.toPath())) {
            return lines
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Error parsing file", e);
            return Collections.emptyList();
        }
    }
}
