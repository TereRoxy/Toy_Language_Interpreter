package model.adt;

import exception.EmptyStackException;

public interface MyIStack <T>{
    T pop() throws EmptyStackException;
    void push(T elem);
    int size();
    boolean isEmpty();
    String toString();
}
