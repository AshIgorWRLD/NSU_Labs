package ru.nsu.commands;

import lombok.extern.slf4j.Slf4j;
import ru.nsu.globalstrings.Messages;
import ru.nsu.stackcalculator.Calculator;

@Slf4j
public class LoadCommand extends Command {
    private static final int COMMAND_LENGTH = 2;
    private static final int FILE_NAME_POSITION = 1;

    @Override
    public boolean isCommandStructureRight(String[] commandLine) {
        if (commandLine.length != COMMAND_LENGTH) {
            log.info(Messages.COMMAND_LENGTH_EXC);
            return false;
        }
        return true;
    }

    @Override
    public void doCommand(String[] commandLine, Calculator calculator) {
        System.out.println(commandLine[FILE_NAME_POSITION]);
        calculator.load(commandLine[FILE_NAME_POSITION]);
    }
}
