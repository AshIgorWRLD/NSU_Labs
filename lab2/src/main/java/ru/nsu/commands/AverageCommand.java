package ru.nsu.commands;

import lombok.extern.slf4j.Slf4j;
import ru.nsu.globalstrings.Messages;
import ru.nsu.globalstrings.Regexes;
import ru.nsu.stackcalculator.Calculator;

@Slf4j
public class AverageCommand extends Command {
    private static final int COMMAND_LENGTH = 2;
    private static final int ELEMENTS_AMOUNT_POSITION = 1;

    @Override
    public boolean isCommandStructureRight(String[] commandLine) {
        if (commandLine.length != COMMAND_LENGTH) {
            log.info(Messages.COMMAND_LENGTH_EXC);
        }
        if (!commandLine[ELEMENTS_AMOUNT_POSITION].matches(Regexes.NUMBERS_IN_STRING)) {
            log.info(Messages.COMMAND_STRUCTURE_EXC);
        }
        return true;
    }

    @Override
    public void doCommand(String[] commandLine, Calculator calculator) {
        calculator.getAvg(Integer.parseInt(commandLine[ELEMENTS_AMOUNT_POSITION]));
    }
}
