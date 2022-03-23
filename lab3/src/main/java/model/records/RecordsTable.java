package model.records;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import model.GameType;

import java.io.*;

@Slf4j
public class RecordsTable {

    private static final String SPLIT_SIGN = "-";
    private static final int RECORDS_TYPE_NUMBER = 3;
    private static final int NOVICE_INFO_PLACE = 0;
    private static final int MEDIUM_INFO_PLACE = 1;
    private static final int EXPERT_INFO_PLACE = 2;
    private static final int GAME_TYPE_PLACE = 0;
    private static final int VALUE_PLACE = 1;
    private static final int NAME_PLACE = 2;

    private final File file = new File("src\\main\\resources\\records.txt");

    @Getter
    private Record[] records;

    public RecordsTable() {
        records = getRecordsArray();
    }

    public void refreshRecordTable(String name, int val, GameType gameType) {
        records = getRecordsArray();
        parseNewScore(new Record(name, val, gameType), records);
        pushNewTable(records);
    }

    public boolean isItRecord(int value, GameType gameType) {
        int pos = getRecordsArrayPosition(gameType);
        return value < records[pos].getValue();
    }

    private Record[] getRecordsArray() {
        try (FileReader fileReader = new FileReader(file)) {
            Record[] newRecords = new Record[RECORDS_TYPE_NUMBER];
            try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                for (int i = 0; i < RECORDS_TYPE_NUMBER; i++) {
                    String newStr = bufferedReader.readLine();
                    String[] subStr = newStr.split(SPLIT_SIGN);
                    GameType tmpRecordGameType = stringToGameType(subStr[GAME_TYPE_PLACE]);
                    if (tmpRecordGameType != null) {
                        newRecords[i] = new Record(subStr[NAME_PLACE],
                                Integer.parseInt(subStr[VALUE_PLACE]), tmpRecordGameType);
                    }
                }
            }
            return newRecords;
        } catch (IOException e) {
            log.error("Can't make file reader");
        }
        return null;
    }

    private void parseNewScore(Record record, Record[] records) {
        int oldRecordPosition = getRecordsArrayPosition(record.getGameType());
        records[oldRecordPosition] = record;
    }

    private int getRecordsArrayPosition(GameType gameType) {
        switch (gameType) {
            case NOVICE -> {
                return NOVICE_INFO_PLACE;
            }
            case MEDIUM -> {
                return MEDIUM_INFO_PLACE;
            }
            case EXPERT -> {
                return EXPERT_INFO_PLACE;
            }
        }
        return NOVICE_INFO_PLACE;
    }

    private void pushNewTable(Record[] records) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            PrintStream printStream = new PrintStream(fileOutputStream);
            for (int i = 0; i < RECORDS_TYPE_NUMBER; i++) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(records[i].getGameType()).append(SPLIT_SIGN).
                        append(records[i].getValue()).append(SPLIT_SIGN).append(records[i].getName());
                printStream.println(stringBuilder.toString());
            }
            printStream.close();
        } catch (IOException e) {
            log.error("Can't create file output stream");
        }
    }

    private GameType stringToGameType(String str) {
        switch (str) {
            case "NOVICE" -> {
                return GameType.NOVICE;
            }
            case "MEDIUM" -> {
                return GameType.MEDIUM;
            }
            case "EXPERT" -> {
                return GameType.EXPERT;
            }
        }
        return null;
    }
}
