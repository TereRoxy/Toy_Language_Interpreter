package model.statements;

import model.adt.MyIStack;
import model.expressions.IExpression;
import model.state.PrgState;

public class WhileStatement implements IStatement{
    IStatement statement;
    IExpression expression;

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
    public String toString() {
        return "While(" + expression.toString() + "){" + statement.toString() + "}";
    }
}
