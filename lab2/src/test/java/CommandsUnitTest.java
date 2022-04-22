import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import ru.nsu.commandlistener.CommandListener;
import ru.nsu.exceptions.*;
import ru.nsu.fabric.CommandFabric;
import ru.nsu.globalstrings.Messages;
import ru.nsu.globalstrings.Paths;
import ru.nsu.stackcalculator.Calculator;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class CommandsUnitTest {

    private CommandListener initCommandListener(Calculator calculator) {
        return new CommandListener(new CommandFabric(), calculator);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    void commentsTest() {
        Calculator calculator = new Calculator();
        CommandListener commandListener = initCommandListener(calculator);
        try {
            commandListener.scan(new FileInputStream(
                    Paths.TEST_RESOURCES_FILES_PATH + "comments.txt"));
        } catch (IOException e) {
            log.error(Messages.UNABLE_CREATE_INPUT_STREAM, e);
        } catch (EmptyStackException e) {
            log.error(Messages.EMPTY_STACK_EXC, e);
        } catch (WrongElementsException e) {
            log.error(Messages.WRONG_ARGS_EXC, e);
        } catch (DivisionByZeroException e) {
            log.error(Messages.DIVISION_BY_ZERO_EXC, e);
        } catch (NoCommandException e) {
            log.error(Messages.NO_COMMAND_EXC, e);
        } catch (OperationException e) {
            log.error(Messages.MORE_ELEMENTS_EXC);
        } catch (NoSuchElementException e) {
            log.error(Messages.NO_SUCH_ELEMENT_EXC);
        }
        assertEquals(calculator.pop(), 20);
        log.info("TEST: commentsTest - COMPLETED");
    }

    @Test
    void defineTest() {
        Calculator calculator = new Calculator();
        CommandListener commandListener = initCommandListener(calculator);
        try {
            commandListener.scan(new FileInputStream(Paths.TEST_RESOURCES_FILES_PATH + "define.txt"));
        } catch (IOException e) {
            log.error(Messages.UNABLE_CREATE_INPUT_STREAM, e);
        } catch (EmptyStackException e) {
            log.error(Messages.EMPTY_STACK_EXC, e);
        } catch (WrongElementsException e) {
            log.error(Messages.WRONG_ARGS_EXC, e);
        } catch (DivisionByZeroException e) {
            log.error(Messages.DIVISION_BY_ZERO_EXC, e);
        } catch (NoCommandException e) {
            log.error(Messages.NO_COMMAND_EXC, e);
        } catch (OperationException e) {
            log.error(Messages.MORE_ELEMENTS_EXC);
        } catch (NoSuchElementException e) {
            log.error(Messages.NO_SUCH_ELEMENT_EXC);
        }
        assertEquals(calculator.pop(), -2);
        log.info("TEST: defineTest - COMPLETED");
    }

    @Test
    void loadFileTest() {
        Calculator calculator = new Calculator();
        CommandListener commandListener = initCommandListener(calculator);
        try {
            commandListener.scan(new FileInputStream(
                    Paths.TEST_RESOURCES_FILES_PATH + "loadFile.txt"));
        } catch (IOException e) {
            log.error(Messages.UNABLE_CREATE_INPUT_STREAM, e);
        } catch (EmptyStackException e) {
            log.error(Messages.EMPTY_STACK_EXC, e);
        } catch (WrongElementsException e) {
            log.error(Messages.WRONG_ARGS_EXC, e);
        } catch (DivisionByZeroException e) {
            log.error(Messages.DIVISION_BY_ZERO_EXC, e);
        } catch (NoCommandException e) {
            log.error(Messages.NO_COMMAND_EXC, e);
        } catch (OperationException e) {
            log.error(Messages.MORE_ELEMENTS_EXC);
        } catch (NoSuchElementException e) {
            log.error(Messages.NO_SUCH_ELEMENT_EXC);
        }
        assertEquals(calculator.pop(), 210.2);
        log.info("TEST: loadFileTest - COMPLETED");
    }

    @Test
    void loadFileMistakesTest() {
        Calculator calculator = new Calculator();
        CommandListener commandListener = initCommandListener(calculator);
        try {
            commandListener.scan(new FileInputStream(
                    Paths.TEST_RESOURCES_FILES_PATH + "loadFileMistakes.txt"));
        } catch (IOException e) {
            log.error(Messages.UNABLE_CREATE_INPUT_STREAM, e);
        } catch (EmptyStackException e) {
            log.error(Messages.EMPTY_STACK_EXC, e);
        } catch (WrongElementsException e) {
            log.error(Messages.WRONG_ARGS_EXC, e);
        } catch (DivisionByZeroException e) {
            log.error(Messages.DIVISION_BY_ZERO_EXC, e);
        } catch (NoCommandException e) {
            log.error(Messages.NO_COMMAND_EXC, e);
        } catch (OperationException e) {
            log.error(Messages.MORE_ELEMENTS_EXC);
        } catch (NoSuchElementException e) {
            log.error(Messages.NO_SUCH_ELEMENT_EXC);
        }
        exception.expect(WrongElementsException.class);
        log.info("TEST: loadFileMistakesTest - COMPLETED");
    }

    @Test
    void wrongLoadFileTest() {
        Calculator calculator = new Calculator();
        CommandListener commandListener = initCommandListener(calculator);
        try {
            commandListener.scan(new FileInputStream(
                    Paths.TEST_RESOURCES_FILES_PATH + "wrongLoadFile.txt"));
        } catch (IOException e) {
            log.error(Messages.UNABLE_CREATE_INPUT_STREAM, e);
        } catch (EmptyStackException e) {
            log.error(Messages.EMPTY_STACK_EXC, e);
        } catch (WrongElementsException e) {
            log.error(Messages.WRONG_ARGS_EXC, e);
        } catch (DivisionByZeroException e) {
            log.error(Messages.DIVISION_BY_ZERO_EXC, e);
        } catch (NoCommandException e) {
            log.error(Messages.NO_COMMAND_EXC, e);
        } catch (OperationException e) {
            log.error(Messages.MORE_ELEMENTS_EXC);
        } catch (NoSuchElementException e) {
            log.error(Messages.NO_SUCH_ELEMENT_EXC);
        }
        exception.expect(NoSuchFileException.class);
        log.info("TEST: wrongLoadFileTest - COMPLETED");
    }

    @Test
    void saveFileTest() {
        Calculator calculator = new Calculator();
        CommandListener commandListener = initCommandListener(calculator);
        try {
            commandListener.scan(new FileInputStream(
                    Paths.TEST_RESOURCES_FILES_PATH + "saveFile.txt"));
        } catch (IOException e) {
            log.error(Messages.UNABLE_CREATE_INPUT_STREAM, e);
        } catch (EmptyStackException e) {
            log.error(Messages.EMPTY_STACK_EXC, e);
        } catch (WrongElementsException e) {
            log.error(Messages.WRONG_ARGS_EXC, e);
        } catch (DivisionByZeroException e) {
            log.error(Messages.DIVISION_BY_ZERO_EXC, e);
        } catch (NoCommandException e) {
            log.error(Messages.NO_COMMAND_EXC, e);
        } catch (OperationException e) {
            log.error(Messages.MORE_ELEMENTS_EXC);
        } catch (NoSuchElementException e) {
            log.error(Messages.NO_SUCH_ELEMENT_EXC);
        }
        assertEquals(calculator.pop(), 13);
        log.info("TEST: saveFileTest - COMPLETED");
    }
}
