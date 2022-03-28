package fileoperations;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

@Slf4j
public class FileOpener {

    public static Optional<FileInputStream> openFile(String fileName) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            log.error("CAN'T GET INPUT STREAM OF YOUR FILE", e);
        }
        return Optional.ofNullable(fileInputStream);
    }
}
