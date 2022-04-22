package ru.nsu.commands;

import lombok.extern.slf4j.Slf4j;
import ru.nsu.exceptions.OperationException;
import ru.nsu.globalstrings.Constants;
import ru.nsu.globalstrings.Messages;
import ru.nsu.stackcalculator.Calculator;

@Slf4j
public class AbsCommand extends Command {
    private static final int COMMAND_LENGTH = 1;

    @Override
    public boolean isCommandStructureRight(String[] commandLine) {
        if (commandLine.length != COMMAND_LENGTH) {
            log.error(Messages.COMMAND_LENGTH_EXC);
            return false;
        }
        return true;
    }

    @Override
    public void doCommand(String[] commandLine, Calculator calculator) throws OperationException {
        if (calculator.getStackSize() < Constants.MINIMAL_ABS_ELEMENTS_NUMBER) {
            throw new OperationException();
        }
        calculator.push(Math.abs(calculator.pop()));
    }
}
