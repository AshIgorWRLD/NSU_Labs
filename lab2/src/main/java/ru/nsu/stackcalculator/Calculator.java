package ru.nsu.stackcalculator;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Calculator {
    private final Stack<Double> stack;
    private final Map<String, Double> definitionMap;

    public Calculator() {
        this.stack = new Stack<>();
        this.definitionMap = new HashMap<>();
    }

    public void putInDefinitionMap(String name, Double value) {
        definitionMap.put(name, value);
    }

    public boolean isInDefinitionMap(String name){
        return definitionMap.containsKey(name);
    }

    public Double getFromDefinitionMap(String name){
        return definitionMap.get(name);
    }

    public void push(Double value) {
        stack.push(value);
    }

    public double pop() {
        return stack.pop();
    }

    public int getStackSize(){
        return stack.size();
    }

    public double peek(){
        return stack.peek();
    }

    public void clearStack(int elementsAmount) {
        for (int i = 0; i < elementsAmount; i++) {
            stack.pop();
        }
    }
}