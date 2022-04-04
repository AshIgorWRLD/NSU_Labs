import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.nsu.commandlistener.CommandListener;
import ru.nsu.globalstrings.Paths;
import ru.nsu.stackcalculator.Calculator;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class CommandsUnitTest {

    @Test
    void commentsTest() {
        Calculator calculator = new Calculator();
        CommandListener commandListener = CommandListener.initCommandListener(calculator);
        commandListener.fileScan(Paths.TEST_RESOURCES_FILES_PATH + "comments.txt");
        assertEquals(calculator.pop(), 20);
        log.info("TEST: commentsTest - COMPLETED");
    }

    @Test
    void defineTest() {
        Calculator calculator = new Calculator();
        CommandListener commandListener = CommandListener.initCommandListener(calculator);
        commandListener.fileScan(Paths.TEST_RESOURCES_FILES_PATH + "define.txt");
        assertEquals(calculator.pop(), -2);
        log.info("TEST: defineTest - COMPLETED");
    }

    @Test
    void commandsMistakesTest() {
        Calculator calculator = new Calculator();
        CommandListener commandListener = CommandListener.initCommandListener(calculator);
        commandListener.fileScan(Paths.TEST_RESOURCES_FILES_PATH + "commandsMistakes.txt");
        assertEquals(calculator.pop(), 9);
        log.info("TEST: commandsMistakesTest - COMPLETED");
    }

    @Test
    void loadFileTest() {
        Calculator calculator = new Calculator();
        CommandListener commandListener = CommandListener.initCommandListener(calculator);
        commandListener.fileScan(Paths.TEST_RESOURCES_FILES_PATH + "loadFile.txt");
        assertEquals(calculator.pop(), 210.2);
        log.info("TEST: loadFileTest - COMPLETED");
    }

    @Test
    void loadFileMistakesTest() {
        Calculator calculator = new Calculator();
        CommandListener commandListener = CommandListener.initCommandListener(calculator);
        commandListener.fileScan(Paths.TEST_RESOURCES_FILES_PATH + "loadFileMistakes.txt");
        assertEquals(calculator.pop(), 2);
        log.info("TEST: loadFileMistakesTest - COMPLETED");
    }

    @Test
    void wrongLoadFileTest() {
        Calculator calculator = new Calculator();
        CommandListener commandListener = CommandListener.initCommandListener(calculator);
        commandListener.fileScan(Paths.TEST_RESOURCES_FILES_PATH + "wrongLoadFile.txt");
        assertEquals(calculator.pop(), 3);
        log.info("TEST: wrongLoadFileTest - COMPLETED");
    }

    @Test
    void saveFileTest() {
        Calculator calculator = new Calculator();
        CommandListener commandListener = CommandListener.initCommandListener(calculator);
        commandListener.fileScan(Paths.TEST_RESOURCES_FILES_PATH + "saveFile.txt");
        assertEquals(calculator.pop(), 13);
        log.info("TEST: saveFileTest - COMPLETED");
    }
}
