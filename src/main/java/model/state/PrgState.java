package model.state;

import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIList;
import model.adt.MyIStack;
import model.statements.IStatement;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;

public class PrgState {
    private MyIDictionary<String, IValue> symTable;
    private MyIStack<IStatement> exeStack;
    private MyIList<IValue> output;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private MyIHeap heap;
    private static int lastId;
    private final int id = incrementId();

    public PrgState(MyIDictionary<String, IValue> symTable, MyIStack<IStatement> exeStack, MyIList<IValue> output,
                    IStatement initialStatement, MyIDictionary<StringValue, BufferedReader> fileTbl,
                    MyIHeap heap) {
        this.symTable = symTable;
        this.exeStack = exeStack;
        this.output = output;
        IStatement initialState = initialStatement.deepCopy();
        exeStack.push(initialStatement);
        this.fileTable = fileTbl;
        this.heap = heap;
    }

    public Boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() {
        if (exeStack.isEmpty()) {
            throw new RuntimeException("PrgState stack is empty");
        }
        try {
            IStatement currentStatement = exeStack.pop();
            return currentStatement.execute(this);
        } catch (Exception e) {
            throw new RuntimeException("Unhandled Exception: " + e.getMessage());
        }
    }

    //toString
    public String toString() {
        return "Id: " + id + "\n" +
                "ExeStack: \n" + exeStack.toString() + "\n" +
                "SymTable: \n" + symTable.toString() + "\n" +
                "Out: \n" + output.toString() + "\n" +
                "FileTable: \n" + fileTableToString() + "\n" +
                "Heap: \n" + heap.toString() + "\n";

    }

    public String fileTableToString(){
        StringBuilder text = new StringBuilder();
        for (StringValue key : fileTable.getKeys()) {
            text.append(key).append("\n");
        }
        return text.toString();
    }

    //static synchronized method to manage the id
    public static synchronized int incrementId() {
        lastId++;
        return lastId;
    }

    public MyIList<IValue> getOutput() {
        return output;
    }
    public MyIStack<IStatement> getExeStack() { return exeStack; }
    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }
    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }
    public MyIHeap getHeap() { return heap; }
    public int getId() { return id; }

    public void setSymTable(MyIDictionary<String, IValue> symTable) {
        this.symTable = symTable;
    }
    public void setExeStack(MyIStack<IStatement> exeStack) {
        this.exeStack = exeStack;
    }
    public void setOutput(MyIList<IValue> output) {
        this.output = output;
    }
    public void setFileTable(MyIDictionary<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }
    public void setHeap(MyIHeap heap) { this.heap = heap; }
}
