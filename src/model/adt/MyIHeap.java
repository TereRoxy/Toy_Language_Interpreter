package model.adt;

import model.value.IValue;

import java.util.Map;

public interface MyIHeap {

    public int allocate(IValue value);
    public void deallocate(int address);
    public IValue getValue(int key);
    public void setValue(int key, IValue value);
    public void setContent(Map<Integer, IValue> map);
    public MyIDictionary<Integer, IValue> getHeap();
    public boolean contains(int key);
    public String toString();
    public Map<Integer, IValue> getMap();
}
