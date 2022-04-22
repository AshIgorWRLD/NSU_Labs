package ru.nsu.commands;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.nsu.exceptions.WrongElementsException;
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
            log.error(Messages.COMMAND_LENGTH_EXC);
        }
        if (!commandLine[ELEMENTS_AMOUNT_POSITION].matches(Regexes.NUMBERS_IN_STRING)) {
            log.error(Messages.COMMAND_STRUCTURE_EXC);
        }
        return true;
    }

    @Override
    public void doCommand(String[] commandLine, Calculator calculator) throws WrongElementsException {
        int elementsAmount = Integer.parseInt(commandLine[ELEMENTS_AMOUNT_POSITION]);
        if (elementsAmount == 0) {
            return;
        }
        if (elementsAmount > calculator.getStackSize()) {
            throw new WrongElementsException();
        }
        calculator.push(countAverageOf(elementsAmount, calculator));
    }

    private double countAverageOf(int elementsAmount, Calculator calculator) {
        double sum = 0;
        for (int i = 0; i < elementsAmount; i++) {
            sum += calculator.pop();
        }
        return sum / elementsAmount;
    }
}
