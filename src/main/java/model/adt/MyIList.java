package model.adt;

import java.util.List;

public interface MyIList <T>{
    void add(T elem);
    List<T> getAll();
    String toString();
}
