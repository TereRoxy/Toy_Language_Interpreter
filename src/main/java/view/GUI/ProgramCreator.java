package view.GUI;

import controller.IController;
import model.adt.*;
import model.state.PrgState;
import model.statements.IStatement;
import model.value.IValue;
import model.value.StringValue;
import repository.IRepo;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class ProgramCreator {

    private IController controller;
    private IStatement program;

    public ProgramCreator(IController controller, IStatement program) {
        this.controller = controller;
        this.program = program;
    }

    public void createProgram() throws Exception {
        // if the program doesn't pass the type checker, an exception will be thrown
        program.typecheck(new MyDictionary<>());

        MyIStack<IStatement> stack = new MyStack<>();
        MyIDictionary<String, IValue> symTable = new MyDictionary<>();
        MyIList<IValue> out = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
        MyIHeap heap = new MyHeap();

        //create the new program state
        PrgState prgState = new PrgState(symTable, stack, out, program, fileTable, heap);
        List<PrgState> newRepoList = new ArrayList<>();
        newRepoList.add(prgState);
        IRepo repo = controller.getRepo();
        repo.setProgramList(newRepoList);
        controller.setRepo(repo);
    }
}
