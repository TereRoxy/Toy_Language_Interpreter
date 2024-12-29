package model.adt;

import model.value.IValue;

import java.util.Map;

public interface MyIHeap {

    int allocate(IValue value);
    void deallocate(int address);
    IValue getValue(int key);
    void setValue(int key, IValue value);
    void setContent(Map<Integer, IValue> map);
    MyIDictionary<Integer, IValue> getHeap();
    boolean contains(int key);
    String toString();
    Map<Integer, IValue> getMap();
}
