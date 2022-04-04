package ru.nsu;

import lombok.extern.slf4j.Slf4j;
import ru.nsu.commandlistener.CommandListener;
import ru.nsu.globalstrings.Messages;
import ru.nsu.stackcalculator.Calculator;

@Slf4j
public class Main {

    private static final int WITH_INPUT_FILE_ARGS_LENGTH = 1;

    public static void main(String[] args) {
        if (args.length > WITH_INPUT_FILE_ARGS_LENGTH) {
            log.info(Messages.WRONG_ARGS_EXC);
            return;
        }
        CommandListener commandListener = CommandListener.initCommandListener(new Calculator());
        if (args.length == WITH_INPUT_FILE_ARGS_LENGTH) {
            commandListener.fileScan(args[0]);
        } else {
            commandListener.commandLineScan();
        }
    }
}
