package model.adt;

import exception.InvalidAddressException;
import model.adt.MyDictionary;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.value.IValue;

import java.util.HashMap;
import java.util.Map;

public class MyHeap implements MyIHeap {
    private Map<Integer, IValue> heap;
    private int nextFreeAddress;

    public MyHeap() {
        this.heap = new HashMap<>();
        this.nextFreeAddress = 1;
    }

    @Override
    public int allocate(IValue value) {
        heap.put(nextFreeAddress, value);
        return nextFreeAddress++;
    }

    @Override
    public void deallocate(int address) {
        heap.remove(address);
    }

    @Override
    public IValue getValue(int key) {
        try {
            return heap.get(key);
        } catch (NullPointerException e) {
            throw new InvalidAddressException("Invalid address");
        }
    }

    @Override
    public void setValue(int key, IValue value) {
        heap.put(key, value);
    }

    @Override
    public void setContent(Map<Integer, IValue> heap) {
        this.heap = heap;
    }

    @Override
    public MyIDictionary<Integer, IValue> getHeap() {
        MyIDictionary<Integer, IValue> heap = new MyDictionary<>();
        for (Map.Entry<Integer, IValue> entry : this.heap.entrySet()) {
            heap.insert(entry.getKey(), entry.getValue());
        }
        return heap;
    }

    @Override
    public boolean contains(int key) {
        return heap.containsKey(key);
    }

    public Map<Integer, IValue> getMap() {
        return heap;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Integer key : heap.keySet()) {
            result.append(key).append(" -> ").append(heap.get(key)).append("\n");
        }
        return result.toString();
    }
}