package model.adt;

import exception.KeyNotFoundException;

import java.util.Map;
import java.util.Set;

public interface MyIDictionary <K, V>{
    void insert(K key, V value);
    V getValue(K key) throws KeyNotFoundException;
    void remove(K key) throws KeyNotFoundException;
    boolean contains(K key);
    String toString();
    Set<K> getKeys();
    Map<K, V> getContent();
    MyIDictionary<K, V> deepCopy();
}
