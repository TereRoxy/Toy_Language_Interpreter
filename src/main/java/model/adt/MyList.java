package model.adt;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T>{
    private final List<T> list;

    public MyList(){
        this.list = new ArrayList<T>();
    }
    public MyList(List<T> list){
        this.list = list;
    }

    @Override
    public void add(T elem) {
        this.list.add(elem);
    }

    @Override
    public List<T> getAll() {
        return this.list;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        for(T elem : this.list){
            result.append(elem.toString()).append("\n");
        }
        return result.toString();
    }
}
