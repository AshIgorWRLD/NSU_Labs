package ru.nsu.commands;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.nsu.globalstrings.Messages;
import ru.nsu.globalstrings.Regexes;
import ru.nsu.globalstrings.SplitSymbols;
import ru.nsu.stackcalculator.Calculator;

import java.io.*;
import java.util.Objects;

@Slf4j
public class LoadCommand extends Command {
    private static final int COMMAND_LENGTH = 2;
    private static final int FILE_NAME_POSITION = 1;
    private static final int LOAD_ERROR = -1;

    @Override
    public boolean isCommandStructureRight(String[] commandLine) {
        if (commandLine.length != COMMAND_LENGTH) {
            log.error(Messages.COMMAND_LENGTH_EXC);
            return false;
        }
        return true;
    }

    @Override
    public void doCommand(String[] commandLine, Calculator calculator) throws IOException {
        try (Reader reader = new FileReader(commandLine[FILE_NAME_POSITION]);) {
            putFileInfoInStack(reader, calculator);
        }
    }

    private void putFileInfoInStack(Reader reader, Calculator calculator) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        String newLine;
        int amount = 0;

        newLine = bufferedReader.readLine();
        while (!Objects.equals(newLine, null)
                && !Objects.equals(newLine, "")) {
            int result = addFromLineToStack(newLine, calculator);
            if (result == -1) {
                calculator.clearStack(amount);
                return;
            }
            amount += result;
            newLine = bufferedReader.readLine();
        }
    }

    private int addFromLineToStack(String newLine, Calculator calculator) {
        String[] values = newLine.split(SplitSymbols.SPACE_PARSE_SYMBOL);
        int amount = 0;
        for (String symbol : values) {
            if (!symbol.matches(Regexes.NUMBERS_IN_STRING)) {
                calculator.clearStack(amount);
                return LOAD_ERROR;
            }
            calculator.push(Double.parseDouble(symbol));
            amount++;
        }
        return amount;
    }
}
