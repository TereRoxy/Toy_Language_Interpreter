package model.adt;

import exception.KeyNotFoundException;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class MyDictionary <K, V> implements MyIDictionary<K, V> {
    private final Map<K, V> map;

    public MyDictionary() {
        map = new HashMap<>();
    }

    @Override
    public void insert(K key, V value) {
        this.map.put(key, value);
    }

    @Override
    public V getValue(K key) throws KeyNotFoundException {
        if (!this.map.containsKey(key)) {
            throw new KeyNotFoundException("Key not found in dictionary!");
        }
        return this.map.get(key);
    }

    @Override
    public void remove(K key) throws KeyNotFoundException {
        if (!this.map.containsKey(key)) {
            throw new KeyNotFoundException("Key not found in dictionary!");
        }
        this.map.remove(key);
    }

    @Override
    public boolean contains(K key) {
        return this.map.containsKey(key);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (K key : this.map.keySet()) {
            result.append(key).append(" -> ").append(this.map.get(key)).append("\n");
        }
        return result.toString();
    }

    @Override
    public Set<K> getKeys() {
        return this.map.keySet();
    }

    @Override
    public Map<K, V> getContent() {
        return this.map;
    }

    @Override
    public MyIDictionary<K, V> deepCopy() {
        MyIDictionary<K, V> newDict = new MyDictionary<>();
        for (K key : this.map.keySet()) {
            newDict.insert(key, this.map.get(key));
        }
        return newDict;
    }
}
