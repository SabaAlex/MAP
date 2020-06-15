package model.exeStack;

import exceptions.MyException;

import java.util.Stack;

public interface MyIStack<T> {
    Stack<T> getContainer();
    T top();
    T pop() throws MyException;
    void push(T v);
    boolean isEmpty();
}
