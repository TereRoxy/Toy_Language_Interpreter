package model.statements;

import exception.StatementException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.RefType;
import model.value.IValue;
import model.value.RefValue;

public class WriteHeapStatement implements IStatement {
    private String varName;
    private IExpression expression;

    public WriteHeapStatement(String varName, IExpression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();

        if (!symTable.contains(varName)) {
            throw new StatementException("Variable not defined in symbol table");
        }

        IValue varValue = symTable.getValue(varName);
        if (!(varValue.getType() instanceof RefType)) {
            throw new StatementException("Variable is not of RefType");
        }

        int address = ((RefValue) varValue).getAddress();
        if (!heap.contains(address)) {
            throw new StatementException("Address not found in heap");
        }

        IValue evalValue = expression.eval(symTable, heap);
        if (!evalValue.getType().equals(((RefValue) varValue).getLocationType())) {
            throw new StatementException("Type of expression does not match location type");
        }

        heap.setValue(address, evalValue);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WriteHeapStatement(varName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "WriteHeap(" + varName + ", " + expression.toString() + ")";
    }
}