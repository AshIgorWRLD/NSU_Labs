package ru.nsu.commands;

import lombok.extern.slf4j.Slf4j;
import ru.nsu.globalstrings.Messages;
import ru.nsu.globalstrings.Regexes;
import ru.nsu.stackcalculator.Calculator;

@Slf4j
public class DefineCommand extends Command {
    private static final int COMMAND_LENGTH = 3;
    private static final int NAME_ELEMENT_POSITION = 1;
    private static final int VALUE_ELEMENT_POSITION = 2;

    @Override
    public boolean isCommandStructureRight(String[] commandLine) {
        if (commandLine.length != COMMAND_LENGTH) {
            log.info(Messages.COMMAND_LENGTH_EXC);
            return false;
        }
        if (commandLine[NAME_ELEMENT_POSITION].matches(Regexes.NUMBERS_IN_STRING) ||
                !commandLine[VALUE_ELEMENT_POSITION].matches(Regexes.NUMBERS_IN_STRING)) {
            log.info(Messages.COMMAND_STRUCTURE_EXC);
            return false;
        }
        return true;
    }

    @Override
    public void doCommand(String[] commandLine, Calculator calculator) {
        calculator.putInDefinitionMap(commandLine[NAME_ELEMENT_POSITION],
                Double.parseDouble(commandLine[VALUE_ELEMENT_POSITION]));
    }
}
