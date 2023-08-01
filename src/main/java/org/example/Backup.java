package org.example;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Backup {

    public void backup() throws IOException {
        Files.createDirectories(Path.of("./backup"));
        DirectoryStream<Path> currentDir = Files.newDirectoryStream(Path.of("."));
        for (Path file: currentDir) {
            if(Files.isDirectory(file))
                continue;
            Files.copy(file, Path.of("./backup" + file.toString()));
        }
    }
}
