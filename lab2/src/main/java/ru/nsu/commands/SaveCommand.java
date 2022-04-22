package ru.nsu.commands;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.nsu.globalstrings.Messages;
import ru.nsu.stackcalculator.Calculator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

@Slf4j
public class SaveCommand extends Command {
    private static final int COMMAND_LENGTH = 2;
    private static final int FILE_NAME_POSITION = 1;

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
        File saveFile = new File(commandLine[FILE_NAME_POSITION]);
        saveFile.createNewFile();
        try (FileOutputStream fileOutputStream = new FileOutputStream(commandLine[FILE_NAME_POSITION])) {
            PrintStream printStream = new PrintStream(fileOutputStream);
            printStream.println(takeInfoFromStack(calculator));
        }
    }

    private String takeInfoFromStack(Calculator calculator) {
        StringBuilder stringBuilder = new StringBuilder();
        while (!(calculator.getStackSize() == 0)) {
            stringBuilder.append(calculator.pop()).append("\n");
        }
        return stringBuilder.toString();
    }
}
