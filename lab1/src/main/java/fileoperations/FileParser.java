package fileoperations;

import character.WordStatisticCounter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class FileParser {

    private static final int READER_BUFFER_LENGTH = 128;

    private final WordStatisticCounter wordStatisticCounter;

    public FileParser() {
        wordStatisticCounter = new WordStatisticCounter();
    }

    private void wordProcessing(String word) {

        wordStatisticCounter.addWord(word);

    }

    private boolean charProcessing(List<Character> list, Character character) {
        if (Character.isLetterOrDigit(character)) {
            list.add(character);
            return true;
        }
        return false;
    }

    private String makeStringFromCharList(List<Character> list){
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(stringBuilder::append);
        return stringBuilder.toString();
    }

    private void parseText(Reader reader) {

        char[] buffer = new char[READER_BUFFER_LENGTH];
        try {
            int res;
            List<Character> wordList = new LinkedList<>();
            do {
                res = reader.read(buffer);
                for (int i = 0; i < res; i++) {
                    if (!charProcessing(wordList, Character.toUpperCase(buffer[i]))) {
                        if (wordList.size() > 0) {
                            wordProcessing(makeStringFromCharList(wordList));
                            wordList = new LinkedList<>();
                        }
                    }
                }
            } while (res == READER_BUFFER_LENGTH);
        } catch (IOException e) {
            log.error("CAN'T READ FROM READER", e);
        }
    }

    public void parse(FileInputStream fileInputStream) {
        try (Reader reader = new InputStreamReader(fileInputStream)) {
            parseText(reader);
            CSVGenerator.generateAnswerFile(wordStatisticCounter.mapFile(),
                    wordStatisticCounter.getAllWordsAmount());
        } catch (IOException e) {
            log.error("CAN'T CREATE READER STREAM OF FILE INPUT STREAM", e);
        }
    }
}
