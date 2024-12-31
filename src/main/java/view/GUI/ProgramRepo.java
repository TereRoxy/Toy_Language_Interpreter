package view.GUI;

import examples.HardcodedExamples;
import model.statements.IStatement;

import java.util.HashMap;

public class ProgramRepo extends HashMap<Integer, IStatement> {
    ProgramRepo() {
        super();
        //hardcoded examples into the program dictionary
        this.put(1, HardcodedExamples.exampleProgram1());
        this.put(2, HardcodedExamples.exampleProgram2());
        this.put(3, HardcodedExamples.exampleProgram3());
        this.put(4, HardcodedExamples.fileExample());
        this.put(5, HardcodedExamples.heapReadExample());
        this.put(6, HardcodedExamples.heapWriteExample());
        this.put(7, HardcodedExamples.heapAllocExample());
        this.put(8, HardcodedExamples.garbageCollectorExample());
        this.put(9, HardcodedExamples.whileExample());
        this.put(10, HardcodedExamples.forkExample());
        this.put(11, HardcodedExamples.typeErrorExample());
    }

}
