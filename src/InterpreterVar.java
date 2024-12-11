import controller.Controller;
import controller.IController;
import model.adt.*;
import model.expressions.*;
import model.state.PrgState;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;
import model.value.StringValue;
import repository.IRepo;
import repository.Repo;
import view.TextMenu;
import view.commands.ExitCommand;
import view.commands.RunExample;

import java.io.BufferedReader;

public class InterpreterVar {

    public static void main(String[] args) {
        // Example program: int v; v=2; Print(v)
        PrgState prg1 = inputProgram(exampleProgram1());
        //Example program: int a;int b; a=2+3*5;b=a+1;Print(b)
        PrgState prg2 = inputProgram(exampleProgram2());
        //Example program: int a;int b; a=2+3*5;b=a+1;Print(b)
        PrgState prg3 = inputProgram(exampleProgram3());
        //string varf;
        //varf="test.in";
        //openRFile(varf);
        //int varc;
        //readFile(varf,varc);print(varc);
        //readFile(varf,varc);print(varc)
        //closeRFile(varf)
        PrgState prg4 = inputProgram(fileExample());
        PrgState prg5 = inputProgram(heapReadExample());
        PrgState prg6 = inputProgram(heapWriteExample());
        PrgState prg7 = inputProgram(heapAllocExample());
        PrgState prg8 = inputProgram(garbageCollectorExample());
        PrgState prg9 = inputProgram(whileExample());

        //Create the repository
        IRepo repo1 = new Repo(prg1, "log1.txt");
        IRepo repo2 = new Repo(prg2, "log2.txt");
        IRepo repo3 = new Repo(prg3, "log3.txt");
        IRepo repo4 = new Repo(prg4, "log4.txt");
        IRepo repo5 = new Repo(prg5, "log5.txt");
        IRepo repo6 = new Repo(prg6, "log6.txt");
        IRepo repo7 = new Repo(prg7, "log7.txt");
        IRepo repo8 = new Repo(prg8, "log8.txt");
        IRepo repo9 = new Repo(prg9, "log9.txt");


        IController ctrl1 = new Controller(repo1);
        IController ctrl2 = new Controller(repo2);
        IController ctrl3 = new Controller(repo3);
        IController ctrl4 = new Controller(repo4);
        IController ctrl5 = new Controller(repo5);
        IController ctrl6 = new Controller(repo6);
        IController ctrl7 = new Controller(repo7);
        IController ctrl8 = new Controller(repo8);
        IController ctrl9 = new Controller(repo9);

        boolean oneStep = false;

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", exampleProgram1().toString(), ctrl1, oneStep));
        menu.addCommand(new RunExample("2", exampleProgram2().toString(), ctrl2, oneStep));
        menu.addCommand(new RunExample("3", exampleProgram3().toString(), ctrl3, oneStep));
        menu.addCommand(new RunExample("4", fileExample().toString(), ctrl4, oneStep));
        menu.addCommand(new RunExample("5", heapReadExample().toString(), ctrl5, oneStep));
        menu.addCommand(new RunExample("6", heapWriteExample().toString(), ctrl6, oneStep));
        menu.addCommand(new RunExample("7", heapAllocExample().toString(), ctrl7, oneStep));
        menu.addCommand(new RunExample("8", garbageCollectorExample().toString(), ctrl8, oneStep));
        menu.addCommand(new RunExample("9", whileExample().toString(), ctrl9, oneStep));

        menu.show();

    }

    //Wrapper for creating a new program state
    private static PrgState inputProgram(IStatement ex) {
        MyIStack<IStatement> stack = new MyStack<>();
        MyIDictionary<String, IValue> symTable = new MyDictionary<>();
        MyIList<IValue> out = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
        return new PrgState(symTable, stack, out, ex, fileTable, new MyHeap());
    }

    // Example program: int v; v=2; Print(v)
    private static CompoundStatement exampleProgram1() {
        return new CompoundStatement(
                new VariableDecStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))
                )
        );
    }

    //Example program: int a;int b; a=2+3*5;b=a+1;Print(b)
    private static CompoundStatement exampleProgram2(){
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
    private static CompoundStatement exampleProgram3(){
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
    private static CompoundStatement fileExample(){
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
    private static CompoundStatement heapReadExample(){
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
    private static CompoundStatement heapWriteExample() {
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
    private static CompoundStatement heapAllocExample(){
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
    private static CompoundStatement garbageCollectorExample(){
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
    private static CompoundStatement whileExample(){
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

    //example
    private static CompoundStatement exampleGC(){
        // Create a sample program
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
                                                new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))))
                                        )
                                )
                        )
                )
        );
    }
}
