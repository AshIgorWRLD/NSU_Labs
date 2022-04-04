package ru.nsu.commands;

import ru.nsu.stackcalculator.Calculator;

public abstract class Command {

    public abstract boolean isCommandStructureRight(String[] commandLine);

    public abstract void doCommand(String[] commandLine, Calculator calculator);
}
