package ru.nsu.commandlistener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.nsu.commanddictionary.CommandDictionary;
import ru.nsu.fabric.CommandFabric;
import ru.nsu.globalstrings.Messages;
import ru.nsu.globalstrings.SplitSymbols;
import ru.nsu.stackcalculator.Calculator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
@AllArgsConstructor
public class CommandListener {
    private static final String END_COMMAND = "END";
    private static final String COMMENT_SYMBOL = "#";

    private final CommandFabric commandFabric;

    public static CommandListener initCommandListener(Calculator calculator) {
        CommandDictionary commandDictionary = new CommandDictionary();
        commandDictionary.parseCommands();
        CommandFabric commandFabric = new CommandFabric(commandDictionary.getDictionary(), calculator);
        return new CommandListener(commandFabric);
    }

    public void fileScan(String fileName) {
        try (FileReader fileReader = new FileReader(fileName)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            parseLines(bufferedReader);
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            log.error(Messages.NO_FILE_EXC, e);
        } catch (IOException e) {
            log.error(Messages.READER_EXC, e);
        }
    }

    public void commandLineScan() {
        Scanner scanner = new Scanner(System.in);
        String newLine;
        while (true) {
            newLine = scanner.nextLine();
            if (Objects.equals(newLine, END_COMMAND)) {
                return;
            }
            if (isComment(newLine)) {
                continue;
            }
            commandFabric.parseCommand(newLine);
        }
    }

    private boolean isComment(String string) {
        return Objects.equals(string.split(SplitSymbols.SPACE_PARSE_SYMBOL)[0].
                split(SplitSymbols.EMPTY_PARSE_SYMBOL)[0], COMMENT_SYMBOL);
    }

    private void parseLines(BufferedReader bufferedReader) {
        String newLine;
        try {
            while (!Objects.equals(newLine = bufferedReader.readLine(), null)) {
                if (isComment(newLine)) {
                    continue;
                }
                commandFabric.parseCommand(newLine);
            }
        } catch (IOException e) {
            log.error(Messages.BUFFERED_READER_EXC, e);
        }
    }
}
