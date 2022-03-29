package fileoperations;

import character.CharacterStatisticCounter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class FileParser {

    private static final int READER_BUFFER_LENGTH = 128;

    private final CharacterStatisticCounter characterStatisticCounter;

    public FileParser() {
        characterStatisticCounter = new CharacterStatisticCounter();
    }

    private void charProcessing(char character) {
        if (Character.isLetterOrDigit(character)) {
            characterStatisticCounter.addCharacter(character);
        }
    }

    private void parseText(Reader reader) {
        char[] buffer = new char[READER_BUFFER_LENGTH];
        try {
            int res;
            do {
                res = reader.read(buffer);
                for (int i = 0; i < res; i++) {
                    charProcessing(Character.toUpperCase(buffer[i]));
                }
            } while (res == READER_BUFFER_LENGTH);
        } catch (IOException e) {
            log.error("CAN'T READ FROM READER", e);
        }
    }

    public void parse(FileInputStream fileInputStream) {
        try (Reader reader = new InputStreamReader(fileInputStream)) {
            parseText(reader);
            CSVGenerator.generateAnswerFile(characterStatisticCounter.mapFile(),
                    characterStatisticCounter.getAllCharactersAmount());
        } catch (IOException e) {
            log.error("CAN'T CREATE READER STREAM OF FILE INPUT STREAM", e);
        }
    }
}
