package model.statements;

import exception.TypeException;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.expressions.IExpression;
import model.state.PrgState;
import model.types.IType;

public class WhileStatement implements IStatement{
    final IStatement statement;
    final IExpression expression;

    public WhileStatement(IStatement statement, IExpression expression){
        this.statement = statement;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIStack<IStatement> stack = state.getExeStack();
        IStatement whileStatement = new CompoundStatement(statement, new WhileStatement(statement, expression));
        IStatement ifStatement = new IfStatement(expression, whileStatement, new NopStatement());
        stack.push(ifStatement);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(statement.deepCopy(), expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws Exception {
        IType typeExpression = expression.typecheck(typeEnv);
        if (typeExpression.equals(expression.typecheck(typeEnv))){
            return typeEnv;
        }
        else{
            throw new TypeException("The condition of while is not a boolean");
        }
    }

    @Override
    public String toString() {
        return "While(" + expression.toString() + "){" + statement.toString() + "}";
    }
}
