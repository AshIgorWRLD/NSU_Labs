package ru.nsu;

import lombok.extern.slf4j.Slf4j;
import ru.nsu.commandlistener.CommandListener;
import ru.nsu.exceptions.*;
import ru.nsu.fabric.CommandFabric;
import ru.nsu.globalstrings.Messages;
import ru.nsu.stackcalculator.Calculator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
public class Main {

    private static final int WITH_INPUT_FILE_ARGS_LENGTH = 1;

    public static void main(String[] args) {
        if (args.length > WITH_INPUT_FILE_ARGS_LENGTH) {
            log.info(Messages.WRONG_ARGS_EXC);
            return;
        }
        CommandListener commandListener = new CommandListener(new CommandFabric(), new Calculator());
        try {
            if (args.length == WITH_INPUT_FILE_ARGS_LENGTH) {
                commandListener.scan(new FileInputStream(args[0]));
            } else {
                commandListener.scan(System.in);
            }
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
    }
}
