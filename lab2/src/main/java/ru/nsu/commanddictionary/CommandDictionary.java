package ru.nsu.commanddictionary;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class CommandDictionary {
    private static final String COMMANDS_PATH_FILE_PATH =
            "src\\main\\resources\\commandPaths.properties";
    private static final String PARSE_SYMBOL = "=";
    private static final int PROPERTIES_KEY_POSITION = 0;
    private static final int PROPERTIES_VALUE_POSITION = 1;

    @Getter
    private final Map<String, String> dictionary;

    public CommandDictionary() {
        dictionary = new HashMap<>();
    }

    public void parseCommands() {
        try (FileReader fileReader = new FileReader(COMMANDS_PATH_FILE_PATH)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            parseFile(bufferedReader);
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            log.error("NO SUCH FILE", e);
        } catch (IOException e) {
            log.error("CAN'T CREATE READER OF FILE", e);
        }
    }

    private void parseFile(BufferedReader bufferedReader) {
        String newLine;
        String[] subPropertiesLine;
        try {
            while (!Objects.equals(newLine = bufferedReader.readLine(), "")) {
                subPropertiesLine = newLine.split(PARSE_SYMBOL);
                dictionary.put(subPropertiesLine[PROPERTIES_KEY_POSITION],
                        subPropertiesLine[PROPERTIES_VALUE_POSITION]);
            }
        } catch (IOException e) {
            log.error("BUFFERED READER ERROR", e);
        }
    }
}
