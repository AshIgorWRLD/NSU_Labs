package ru.nsu.commandlistener;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.nsu.commands.Command;
import ru.nsu.exceptions.*;
import ru.nsu.fabric.CommandFabric;
import ru.nsu.globalstrings.Messages;
import ru.nsu.globalstrings.Regexes;
import ru.nsu.globalstrings.SplitSymbols;
import ru.nsu.stackcalculator.Calculator;

import java.io.*;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class CommandListener {
    private static final String COMMENT_SYMBOL = "#";

    private final CommandFabric commandFabric;
    private final Calculator calculator;

    public void scan(InputStream inputStream) throws IOException, NoCommandException, EmptyStackException,
            WrongElementsException, DivisionByZeroException, OperationException, NoSuchElementException {
        try (Reader reader = new InputStreamReader(inputStream)) {
            BufferedReader bufferedReader = new BufferedReader(reader);
            parseLines(bufferedReader);
            bufferedReader.close();
        }
    }

    private boolean isComment(String string) {
        return Objects.equals(string.split(SplitSymbols.SPACE_PARSE_SYMBOL)[0].
                split(SplitSymbols.EMPTY_PARSE_SYMBOL)[0], COMMENT_SYMBOL);
    }


    private void parseLines(BufferedReader bufferedReader) throws IOException, NoCommandException,
            EmptyStackException, WrongElementsException, DivisionByZeroException, OperationException,
            NoSuchElementException {
        String newLine;
        while (!Objects.equals(newLine = bufferedReader.readLine(), null)) {
            if (isComment(newLine)) {
                continue;
            }
            String[] commandLineElements = newLine.split(SplitSymbols.SPACE_PARSE_SYMBOL);
            Command command = commandFabric.getCommand(commandLineElements[0]);
            if (Objects.equals(command, null)) {
                throw new NoCommandException();
            }
            if (!command.isCommandStructureRight(commandLineElements)) {
                return;
            }
            command.doCommand(commandLineElements, calculator);
        }
    }
}
