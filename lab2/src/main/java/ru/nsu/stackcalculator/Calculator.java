package ru.nsu.stackcalculator;

import lombok.extern.slf4j.Slf4j;
import ru.nsu.globalstrings.Messages;
import ru.nsu.globalstrings.Regexes;
import ru.nsu.globalstrings.SplitSymbols;

import java.io.*;
import java.util.*;

@Slf4j
public class Calculator {
    private static final int MINIMAL_OPERATION_ELEMENTS_NUMBER = 2;
    private static final int MINIMAL_ABS_ELEMENTS_NUMBER = 1;
    private static final int LOAD_ERROR = -1;

    private final Stack<Double> stack;
    private final Map<String, Double> definitionMap;

    public Calculator() {
        this.stack = new Stack<>();
        this.definitionMap = new HashMap<>();
    }

    public void putInDefinitionMap(String name, Double value) {
        definitionMap.put(name, value);
    }

    public void push(String name) {
        if (!definitionMap.containsKey(name)) {
            log.info("NO ELEMENT WITH NAME '" + name + "'");
            return;
        }
        stack.push(definitionMap.get(name));
    }

    public void push(Double value) {
        stack.push(value);
    }

    public double pop() {
        return stack.pop();
    }

    public void doOperation(Operations operation) {
        switch (operation) {
            case SUM -> {
                checkOperation(getSum());
            }
            case SUB -> {
                checkOperation(getSub());
            }
            case MUL -> {
                checkOperation(getMul());
            }
            case DIV -> {
                checkOperation(getDiv());
            }
            case ABS -> {
                checkOperation(getAbs());
            }
        }
    }

    public void print() {
        if (stack.size() == 0) {
            log.info(Messages.EMPTY_STACK_EXC);
            return;
        }
        System.out.println(stack.peek());
    }

    public void save(String fileName) {
        File saveFile = new File(fileName);
        try {
            saveFile.createNewFile();
        } catch (IOException e) {
            log.error(Messages.UNABLE_CREATE_FILE_EXC, e);
            return;
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            PrintStream printStream = new PrintStream(fileOutputStream);
            printStream.println(takeInfoFromStack());
        } catch (IOException e) {
            log.error(Messages.UNABLE_CREATE_OUTPUT_STREAM_EXC);
        }
    }

    public void load(String fileName) {
        try (Reader reader = new FileReader(fileName);) {
            putFileInfoInStack(reader);
        } catch (FileNotFoundException e) {
            log.error(Messages.NO_FILE_EXC, e);
        } catch (IOException e) {
            log.error(Messages.READER_EXC, e);
        }
    }

    public void getAvg(int elementsAmount) {
        if (elementsAmount == 0) {
            return;
        }
        if (elementsAmount > stack.size()) {
            log.info(Messages.MORE_ELEMENTS_EXC);
            return;
        }
        stack.push(countAverageOf(elementsAmount));
    }

    private String takeInfoFromStack() {
        StringBuilder stringBuilder = new StringBuilder();
        while (!stack.empty()) {
            stringBuilder.append(stack.pop()).append("\n");
        }
        return stringBuilder.toString();
    }

    private void putFileInfoInStack(Reader reader) {
        BufferedReader bufferedReader = new BufferedReader(reader);
        String newLine;
        int amount = 0;
        try {
            newLine = bufferedReader.readLine();
            while (!Objects.equals(newLine, null)
                    && !Objects.equals(newLine, "")) {
                int result = addFromLineToStack(newLine);
                if (result == -1) {
                    System.out.println(amount);
                    clearStack(amount);
                    return;
                }
                amount += result;
                newLine = bufferedReader.readLine();
            }
        } catch (IOException e) {
            log.error(Messages.BUFFERED_READER_EXC, e);
        }
    }

    private int addFromLineToStack(String newLine) {
        String[] values = newLine.split(SplitSymbols.SPACE_PARSE_SYMBOL);
        int amount = 0;
        for (String symbol : values) {
            if (!symbol.matches(Regexes.NUMBERS_IN_STRING)) {
                clearStack(amount);
                log.info(Messages.ONLY_NUMBERS_FILE_EXC);
                return LOAD_ERROR;
            }
            stack.push(Double.parseDouble(symbol));
            amount++;
        }
        return amount;
    }

    private void clearStack(int elementsAmount) {
        for (int i = 0; i < elementsAmount; i++) {
            stack.pop();
        }
    }

    private double countAverageOf(int elementsAmount) {
        double sum = 0;
        for (int i = 0; i < elementsAmount; i++) {
            sum += stack.pop();
        }
        return sum / elementsAmount;
    }

    private void checkOperation(Optional<Double> operationResult) {
        operationResult.ifPresentOrElse(stack::push, () -> log.error(Messages.LOGIC_EXC));
    }

    private Optional<Double> getAbs() {
        if (stack.size() < MINIMAL_ABS_ELEMENTS_NUMBER) {
            return Optional.empty();
        }
        return Optional.of(Math.abs(stack.pop()));
    }

    private Optional<Double> getSum() {
        if (stack.size() < MINIMAL_OPERATION_ELEMENTS_NUMBER) {
            return Optional.empty();
        }
        return Optional.of(stack.pop() + stack.pop());
    }

    private Optional<Double> getSub() {
        if (stack.size() < MINIMAL_OPERATION_ELEMENTS_NUMBER) {
            return Optional.empty();
        }
        double a = stack.pop();
        return Optional.of(stack.pop() - a);
    }

    private Optional<Double> getMul() {
        if (stack.size() < MINIMAL_OPERATION_ELEMENTS_NUMBER) {
            return Optional.empty();
        }
        return Optional.of(stack.pop() * stack.pop());
    }

    private Optional<Double> getDiv() {
        if (stack.size() < MINIMAL_OPERATION_ELEMENTS_NUMBER) {
            return Optional.empty();
        }
        double a = stack.pop();
        if (a == 0) {
            log.info(Messages.DIVISION_BY_ZERO_EXC);
            stack.push(a);
            return Optional.empty();
        }
        return Optional.of(stack.pop() / a);
    }
}