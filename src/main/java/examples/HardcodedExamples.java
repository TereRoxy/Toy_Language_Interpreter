package examples;

import model.expressions.*;
import model.statements.*;
import model.types.*;
import model.value.*;

public class HardcodedExamples {

    //int a; bool b; a=a+b;
    public static CompoundStatement typeErrorExample() {
        return new CompoundStatement(
                new VariableDecStatement("a", new IntType()),
                new CompoundStatement(
                        new VariableDecStatement("b", new BoolType()),
                        new AssignStatement("a", new ArithmeticalExpression(new VariableExpression("a"), new VariableExpression("b"), ArithmeticalOperator.ADD))
                )
        );
    }

    //int v; Ref int a; v=10;new(a,22);
    //fork(wH(a,30);v=32;print(v);print(rH(a)));
    //print(v);print(rH(a))
    public static CompoundStatement forkExample() {
        return new CompoundStatement(
                new VariableDecStatement("v", new IntType()),
                new CompoundStatement(
                        new VariableDecStatement("a", new RefType(new IntType())),
                        new CompoundStatement(
                                new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(
                                        new HeapAllocStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(
                                                new ForkStatement(
                                                        new CompoundStatement(
                                                                new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                                                                new CompoundStatement(
                                                                        new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new VariableExpression("v")),
                                                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                                        )
                                                                )
                                                        )
                                                ) ,
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                )
                                        )
                                )
                        )
                )
        );
    }

    // Example program: int v; v=2; Print(v)
    public static CompoundStatement exampleProgram1() {
        return new CompoundStatement(
                new VariableDecStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))
                )
        );
    }

    //Example program: int a;int b; a=2+3*5;b=a+1;Print(b)
    public static CompoundStatement exampleProgram2(){
        return new CompoundStatement(
                new VariableDecStatement("a", new IntType()),
                new CompoundStatement(
                        new VariableDecStatement("b", new IntType()),
                        new CompoundStatement(
                                new AssignStatement("a", new ArithmeticalExpression(
                                        new ValueExpression(new IntValue(2)),
                                        new ArithmeticalExpression(
                                                new ValueExpression(new IntValue(3)),
                                                new ValueExpression(new IntValue(5)),
                                                ArithmeticalOperator.MULTIPLY
                                        ),
                                        ArithmeticalOperator.ADD
                                )),
                                new CompoundStatement(
                                        new AssignStatement("b", new ArithmeticalExpression(
                                                new VariableExpression("a"),
                                                new ValueExpression(new IntValue(1)),
                                                ArithmeticalOperator.ADD
                                        )),
                                        new PrintStatement(new VariableExpression("b"))
                                )
                        )
                )
        );
    }

    // Example program: bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)
    public static CompoundStatement exampleProgram3(){
        return new CompoundStatement(
                new VariableDecStatement("a", new BoolType()),
                new CompoundStatement(
                        new VariableDecStatement("v", new IntType()),
                        new CompoundStatement(
                                new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(
                                        new IfStatement(
                                                new VariableExpression("a"),
                                                new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                                new AssignStatement("v", new ValueExpression(new IntValue(3))
                                                )
                                        ),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                )
        );
    }

    //string varf;
    //varf="test.in";
    //openRFile(varf);
    //int varc;
    //readFile(varf,varc);print(varc);
    //readFile(varf,varc);print(varc)
    //closeRFile(varf)
    public static CompoundStatement fileExample(){
        return new CompoundStatement(
                new VariableDecStatement("varf", new StringType()),
                new CompoundStatement(
                        new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(
                                new OpenRFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(
                                        new VariableDecStatement("varc", new IntType()),
                                        new CompoundStatement(
                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(
                                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseRFileStatement(new VariableExpression("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
    public static CompoundStatement heapReadExample(){
        return new CompoundStatement(
                new VariableDecStatement("v", new RefType(new IntType())),
                new CompoundStatement(
                        new HeapAllocStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDecStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(
                                        new HeapAllocStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                                new PrintStatement(new ArithmeticalExpression(
                                                        new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))),
                                                        new ValueExpression(new IntValue(5)),
                                                        ArithmeticalOperator.ADD
                                                ))
                                        )
                                )
                        )
                )
        );
    }

    // Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5)
    public static CompoundStatement heapWriteExample() {
        return new CompoundStatement(
                new VariableDecStatement("v", new RefType(new IntType())),
                new CompoundStatement(
                        new HeapAllocStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                new CompoundStatement(
                                        new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticalExpression(
                                                new ReadHeapExpression(new VariableExpression("v")),
                                                new ValueExpression(new IntValue(5)),
                                                ArithmeticalOperator.ADD
                                        ))
                                )
                        )
                )
        );
    }

    //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
    public static CompoundStatement heapAllocExample(){
        return new CompoundStatement(
                new VariableDecStatement("v", new RefType(new IntType())),
                new CompoundStatement(
                        new HeapAllocStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDecStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(
                                        new HeapAllocStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a"))
                                        )
                                )
                        )
                )
        );
    }

    //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
    public static CompoundStatement garbageCollectorExample(){
        return new CompoundStatement(
                new VariableDecStatement("v", new RefType(new IntType())),
                new CompoundStatement(
                        new HeapAllocStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDecStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(
                                        new HeapAllocStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new HeapAllocStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a")))
                                                )
                                        )
                                )
                        )
                )
        );
    }

    //int v; v=4; (while (v>0) print(v);v=v-1);print(v)
    public static CompoundStatement whileExample(){
        return new CompoundStatement(
                new VariableDecStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(
                                new WhileStatement(
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new AssignStatement("v", new ArithmeticalExpression(
                                                        new VariableExpression("v"),
                                                        new ValueExpression(new IntValue(1)),
                                                        ArithmeticalOperator.SUBTRACT
                                                ))
                                        ),
                                        new RelationalExpression(
                                                new VariableExpression("v"),
                                                new ValueExpression(new IntValue(0)),
                                                RelationalOperator.GREATER
                                        )
                                ),
                                new PrintStatement(new VariableExpression("v"))
                        )
                )
        );
    }
}
