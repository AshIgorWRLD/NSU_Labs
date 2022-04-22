package ru.nsu.commands;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.nsu.exceptions.NoSuchElementException;
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
            log.error(Messages.COMMAND_LENGTH_EXC);
            return false;
        }
        return true;
    }

    @Override
    public void doCommand(String[] commandLine, Calculator calculator) throws NoSuchElementException {
        if (commandLine[VALUE_ELEMENT_POSITION].matches(Regexes.NUMBERS_IN_STRING)) {
            calculator.push(Double.parseDouble(commandLine[VALUE_ELEMENT_POSITION]));
            return;
        }
        String name = commandLine[VALUE_ELEMENT_POSITION];
        if (!calculator.isInDefinitionMap(name)) {
            throw new NoSuchElementException();
        }
        calculator.push(calculator.getFromDefinitionMap(name));
    }
}
