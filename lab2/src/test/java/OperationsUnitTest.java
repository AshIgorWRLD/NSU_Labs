import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.nsu.commandlistener.CommandListener;
import ru.nsu.globalstrings.Paths;
import ru.nsu.stackcalculator.Calculator;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class OperationsUnitTest {

    @Test
    void rightOperationsTest() {
        Calculator calculator = new Calculator();
        CommandListener commandListener = CommandListener.initCommandListener(calculator);
        commandListener.fileScan(Paths.TEST_RESOURCES_PATH + "rightOperations.txt");
        assertEquals(calculator.pop(), 2);
        log.info("TEST: rightOperationsTest - COMPLETED");
    }

    @Test
    void averageTest() {
        Calculator calculator = new Calculator();
        CommandListener commandListener = CommandListener.initCommandListener(calculator);
        commandListener.fileScan(Paths.TEST_RESOURCES_PATH + "average.txt");
        assertEquals(calculator.pop(), 7.1);
        log.info("TEST: averageTest - COMPLETED");
    }

    @Test
    void divisionByZeroTest() {
        Calculator calculator = new Calculator();
        CommandListener commandListener = CommandListener.initCommandListener(calculator);
        commandListener.fileScan(Paths.TEST_RESOURCES_FILES_PATH + "divisionByZero.txt");
        assertEquals(calculator.pop(), 0);
        log.info("TEST: divisionByZeroTest - COMPLETED");
    }
}
