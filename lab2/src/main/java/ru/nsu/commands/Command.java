package ru.nsu.commands;

import ru.nsu.exceptions.*;
import ru.nsu.stackcalculator.Calculator;

import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class Command {

    public abstract boolean isCommandStructureRight(String[] commandLine);

    public abstract void doCommand(String[] commandLine, Calculator calculator) throws OperationException, WrongElementsException, DivisionByZeroException, IOException, EmptyStackException, NoSuchElementException;
}
