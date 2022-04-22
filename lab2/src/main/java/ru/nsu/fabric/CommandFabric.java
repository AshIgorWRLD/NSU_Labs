package ru.nsu.fabric;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.nsu.commands.Command;
import ru.nsu.globalstrings.Messages;
import ru.nsu.globalstrings.SplitSymbols;
import ru.nsu.stackcalculator.Calculator;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

@Slf4j
public class CommandFabric {

    private static final String COMMANDS_PROPERTIES_PATH = "/commandPaths.properties";

    private final Map<String, Command> commands;
    private final Properties commandsList;

    @SneakyThrows
    public CommandFabric() {
        this.commands = new HashMap<>();
        this.commandsList = new Properties();
        InputStream inputStream = CommandFabric.class
                .getResourceAsStream(COMMANDS_PROPERTIES_PATH);
        commandsList.load(inputStream);
    }

    @SneakyThrows
    public Command getCommand(String commandName) {
        if (Objects.equals(commandsList.get(commandName), null)) {
            log.info(Messages.NO_COMMAND_EXC);
            return null;
        }
        if (Objects.equals(commands.get(commandName), null)) {
            createCommand(commandName);
        }
        return commands.get(commandName);
    }

    private void createCommand(String name) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Class<?> cls = Class.forName(String.valueOf(commandsList.get(name)));
        Constructor<?> constructor = cls.getConstructor();
        commands.put(name, (Command) constructor.newInstance());
        log.info("'" + name + "' CLASS WAS UPLOADED");
    }
}
