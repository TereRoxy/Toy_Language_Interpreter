import controller.Controller;
import controller.IController;
import model.adt.*;
import model.expressions.*;
import model.state.PrgState;
import model.statements.*;
import model.types.*;
import model.value.*;
import repository.*;
import view.commands.*;
import view.TextMenu;

import java.io.BufferedReader;
import java.util.function.Supplier;

public class Interpreter {

    private static final String LOG_FILE = "log.txt";

    public static void main(String[] args) {
        boolean execOneStep = false;
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        // Shared repository and controller for all program states
        IRepo repo = new Repo(null, LOG_FILE);
        IController controller = new Controller(repo);

        // Map storing program examples with keys and their suppliers
        MyIDictionary<String, Supplier<IStatement>> programDictionary = new MyDictionary<>();
        programDictionary.insert("1", Interpreter::exampleProgram1);
        programDictionary.insert("2", Interpreter::exampleProgram2);
        programDictionary.insert("3", Interpreter::exampleProgram3);
        programDictionary.insert("4", Interpreter::fileExample);
        programDictionary.insert("5", Interpreter::heapReadExample);
        programDictionary.insert("6", Interpreter::heapWriteExample);
        programDictionary.insert("7", Interpreter::heapAllocExample);
        programDictionary.insert("8", Interpreter::garbageCollectorExample);
        programDictionary.insert("9", Interpreter::whileExample);

        // Add commands dynamically with program suppliers
        for (String key : programDictionary.getKeys()) {
            try {
                Supplier<IStatement> programSupplier = programDictionary.getValue(key);
                // Use a supplier to set up the new PrgState in the existing Controller and Repo when needed
                // Lambda expression to update the controller to use the right program state
                RunExample command = new RunExample(
                        key,
                        "Example " + key + ": " + programSupplier.get().toString(),
                        () -> updateController(controller, repo, programSupplier),
                        controller,
                        execOneStep
                );
                menu.addCommand(command);
            }
            catch (Exception e){
                System.out.println("An exception occurred when creating the menu interface: " + e.getMessage());
            }
        }
        menu.show();

    }

    // Update the controller and repo with a new PrgState created from the provided program
    private static void updateController(IController controller, IRepo repo, Supplier<IStatement> programSupplier) {
        IStatement program = programSupplier.get();
        PrgState prgState = inputProgram(program);
        repo.setCurrentProgram(prgState); // Update the repository with the new program state
        controller.setRepo(repo);   // Ensure the controller uses the updated repository
    }

    //Wrapper for creating a new program state
    private static PrgState inputProgram(IStatement ex) {
        MyIStack<IStatement> stack = new MyStack<>();
        MyIDictionary<String, IValue> symTable = new MyDictionary<>();
        MyIList<IValue> out = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
        MyIHeap heap = new MyHeap();
        return new PrgState(symTable, stack, out, ex, fileTable, heap);
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
}
