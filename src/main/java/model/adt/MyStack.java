package model.adt;

import exception.EmptyStackException;
import java.util.Stack;

public class MyStack <T> implements MyIStack<T> {
    private final Stack<T> stack;

    public MyStack() {
        stack = new Stack<>();
    }

    public Stack<T> getStack() {
        return this.stack;
    }

    @Override
    public T pop() throws EmptyStackException {
        if (this.stack.isEmpty()) {
            throw new EmptyStackException("The stack is empty!");
        }
        return this.stack.pop();
    }

    @Override
    public void push(T elem) {
        this.stack.push(elem);
    }

    @Override
    public int size() {
        return this.stack.size();
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder();
        MyStack<T> tmpStack = new MyStack<>();
        try {
            while (!this.stack.isEmpty()) {
                T elem = this.stack.pop();
                tmpStack.push(elem);
                answer.append(elem.toString()).append('\n');
            }
            while (!tmpStack.isEmpty()) {
                this.stack.push(tmpStack.pop());
            }
        } catch (EmptyStackException exception) {
            throw new RuntimeException(exception.getMessage());
        }
        return answer.toString();
    }
}


