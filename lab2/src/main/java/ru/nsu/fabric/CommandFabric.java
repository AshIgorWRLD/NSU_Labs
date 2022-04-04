package ru.nsu.fabric;

import lombok.extern.slf4j.Slf4j;
import ru.nsu.commands.Command;
import ru.nsu.globalstrings.Messages;
import ru.nsu.globalstrings.SplitSymbols;
import ru.nsu.stackcalculator.Calculator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class CommandFabric {

    private final Map<String, String> commandDictionary;
    private final Calculator calculator;
    private final Map<String, Command> commands;

    public CommandFabric(Map<String, String> commandDictionary, Calculator calculator) {
        this.commandDictionary = commandDictionary;
        this.calculator = calculator;
        this.commands = new HashMap<>();
    }

    public void parseCommand(String commandLine) {
        String[] subCommand = commandLine.split(SplitSymbols.SPACE_PARSE_SYMBOL);
        if (Objects.equals(commandDictionary.get(subCommand[0]), null)) {
            log.info(Messages.NO_COMMAND_EXC);
            return;
        }
        if (Objects.equals(commands.get(subCommand[0]), null)) {
            createCommand(subCommand[0]);
        }
        Command command = commands.get(subCommand[0]);
        if (command.isCommandStructureRight(subCommand)) {
            command.doCommand(subCommand, calculator);
        }
    }

    private void createCommand(String name) {
        try {
            Class<?> cls = Class.forName(commandDictionary.get(name));
            Constructor<?> constructor = cls.getConstructor();
            commands.put(name, (Command) constructor.newInstance());
            log.info("'" + name + "' CLASS WAS UPLOADED");
        } catch (ClassNotFoundException e) {
            log.error(Messages.UNABLE_CLASS_LOAD_EXC, e);
        } catch (NoSuchMethodException e) {
            log.error(Messages.NO_CONSTRUCTOR_CLASS_LOAD_EXC, e);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            log.error(Messages.UNABLE_CREATE_NEW_INSTANCE_EXC);
        }
    }
}
