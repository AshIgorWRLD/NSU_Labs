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

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class OperationsUnitTest {

    private CommandListener initCommandListener(Calculator calculator) {
        return new CommandListener(new CommandFabric(), calculator);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    void rightOperationsTest() {
        Calculator calculator = new Calculator();
        CommandListener commandListener = initCommandListener(calculator);
        try {
            commandListener.scan(new FileInputStream(
                    Paths.TEST_RESOURCES_FILES_PATH + "rightOperations.txt"));
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
        assertEquals(calculator.pop(), 2);
        log.info("TEST: rightOperationsTest - COMPLETED");
    }

    @Test
    void averageTest() {
        Calculator calculator = new Calculator();
        CommandListener commandListener = initCommandListener(calculator);
        try {
            commandListener.scan(new FileInputStream(
                    Paths.TEST_RESOURCES_FILES_PATH + "average.txt"));
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
        assertEquals(calculator.pop(), 7.1);
        log.info("TEST: averageTest - COMPLETED");
    }

    @Test
    void divisionByZeroTest() {
        Calculator calculator = new Calculator();
        CommandListener commandListener = initCommandListener(calculator);
        try {
            commandListener.scan(new FileInputStream(
                    Paths.TEST_RESOURCES_FILES_PATH + "divisionByZero.txt"));
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
        exception.expect(DivisionByZeroException.class);
        log.info("TEST: divisionByZeroTest - COMPLETED");
    }
}
