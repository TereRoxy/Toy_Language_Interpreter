package model.adt;

import exception.EmptyStackException;

import java.util.List;

public interface MyIStack <T>{
    T pop() throws EmptyStackException;
    void push(T elem);
    int size();
    boolean isEmpty();
    String toString();
    List<T> getContent();
}
