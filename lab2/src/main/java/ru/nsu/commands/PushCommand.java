package ru.nsu.commands;

import lombok.extern.slf4j.Slf4j;
import ru.nsu.globalstrings.Messages;
import ru.nsu.globalstrings.Regexes;
import ru.nsu.stackcalculator.Calculator;

@Slf4j
public class PushCommand extends Command {
    private static final int COMMAND_LENGTH = 2;
    private static final int VALUE_ELEMENT_POSITION = 1;

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
        if (commandLine[VALUE_ELEMENT_POSITION].matches(Regexes.NUMBERS_IN_STRING)) {
            calculator.push(Double.parseDouble(commandLine[VALUE_ELEMENT_POSITION]));
            return;
        }
        calculator.push(commandLine[VALUE_ELEMENT_POSITION]);
    }
}
