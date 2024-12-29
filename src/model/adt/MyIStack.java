package model.adt;

import exception.EmptyStackException;

public interface MyIStack <T>{
    public T pop() throws EmptyStackException;
    public void push(T elem);
    public int size();
    public boolean isEmpty();
    public String toString();
}
