package model.adt;

import java.util.List;

public interface MyIList <T>{
    public void add(T elem);
    public List<T> getAll();
    public String toString();
}
