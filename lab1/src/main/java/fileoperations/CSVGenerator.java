package fileoperations;

import character.WordStatistic;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Set;

@Slf4j
public class CSVGenerator {

    private static final String PATH_TO_CSV_FILE_DIRECTORY = "result.csv";
    private static final String CSV_SEPARATION_SYMBOL = ",";
    private static final int PERCENTAGE_COEFFICIENT = 100;

    public static void generateAnswerFile(Set<WordStatistic> statisticSet, int averageAmount) {
        File csvFile = new File(PATH_TO_CSV_FILE_DIRECTORY);
        try {
            csvFile.createNewFile();
        } catch (IOException e) {
            log.error("CAN'T CREATE CSV FILE", e);
            return;
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(PATH_TO_CSV_FILE_DIRECTORY)) {
            processCharacterCharacteristic(statisticSet, averageAmount,
                    new PrintStream(fileOutputStream));
        } catch (IOException e) {
            log.error("CAN'T CREATE OUTPUT STREAM OF CSV FILE");
        }
    }

    private static void processCharacterCharacteristic(Set<WordStatistic> statisticSet,
                                                       int averageAmount, PrintStream printStream) {
        printStream.println("VALUE,AMOUNT,PERCENTAGE");
        statisticSet.forEach(x -> {
            StringBuilder stringBuilder = new StringBuilder(x.getValue());
            stringBuilder.append(CSV_SEPARATION_SYMBOL)
                .append(x.getAmount())
                .append(CSV_SEPARATION_SYMBOL)
                .append((double) x.getAmount() / averageAmount * PERCENTAGE_COEFFICIENT);
            printStream.println(stringBuilder);
        });
    }
}
