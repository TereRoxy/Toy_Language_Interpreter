package model.statements;

import exception.StatementException;
import exception.TypeException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.IType;
import model.types.RefType;
import model.value.IValue;
import model.value.RefValue;

public class HeapAllocStatement implements IStatement{

    final String variable;
    final IExpression expr;

    public HeapAllocStatement(String variable, IExpression expr){
        this.variable = variable;
        this.expr = expr;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {

        if (!state.getSymTable().contains(variable)) {
            throw new StatementException("Variable name not in symbol table");
        }

        IValue variableValue = state.getSymTable().getValue(variable);
        if (!(variableValue.getType() instanceof RefType)){
            throw new StatementException("Variable is not of RefType");
        }

        IValue evalExpr = expr.eval(state.getSymTable(), state.getHeap());

        if (!evalExpr.getType().equals(((RefType)variableValue.getType()).getInner())){
            throw new StatementException("Variable type and expression type do not match");
        }

        int addr = state.getHeap().allocate(evalExpr);
        state.getSymTable().insert(variable, new RefValue(addr, evalExpr.getType()));

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapAllocStatement(variable, expr);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws Exception {
        IType typeVar = typeEnv.getValue(variable);
        IType typeExpr = expr.typecheck(typeEnv);

        if (typeVar.equals(new RefType(typeExpr))){
            return typeEnv;
        }
        else {
            throw new TypeException("HeapAllocStatement: right hand side and left hand side have different types");
        }
    }

    @Override
    public String toString() {
        return "new(" + variable + ", " + expr.toString() + ")";
    }
}
