package ru.nsu.commands;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.nsu.exceptions.OperationException;
import ru.nsu.globalstrings.Constants;
import ru.nsu.globalstrings.Messages;
import ru.nsu.stackcalculator.Calculator;

@Slf4j
public class MultiplicationCommand extends Command {
    private static final int COMMAND_LENGTH = 1;

    @Override
    public boolean isCommandStructureRight(String[] commandLine) {
        if (commandLine.length != COMMAND_LENGTH) {
            log.error(Messages.COMMAND_LENGTH_EXC);
            return false;
        }
        return true;
    }

    @SneakyThrows
    @Override
    public void doCommand(String[] commandLine, Calculator calculator) {
        if (calculator.getStackSize() < Constants.MINIMAL_OPERATION_ELEMENTS_NUMBER) {
            throw new OperationException();
        }
        calculator.push(calculator.pop() * calculator.pop());
    }
}
