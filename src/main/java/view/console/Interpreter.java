package view.console;

import controller.Controller;
import controller.IController;
import examples.HardcodedExamples;
import model.adt.*;
import model.statements.*;
import repository.*;
import view.console.commands.Command;
import view.console.commands.ExitCommand;
import view.console.commands.SetupExample;
import view.console.menus.TextMenu;

import java.util.function.Supplier;

public class Interpreter {

    private static final String LOG_FILE = "log.txt";

    public static void main(String[] args) {
        //boolean execOneStep = false;
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand(0, "exit"));

        // Shared repository and controller for all program states
        IRepo repo = new Repo(null, LOG_FILE);
        IController controller = new Controller(repo);

        // Map storing program examples with keys and their suppliers
        MyIDictionary<Integer, Supplier<IStatement>> programDictionary = new MyDictionary<>();
        programDictionary.insert(1, HardcodedExamples::exampleProgram1);
        programDictionary.insert(2, HardcodedExamples::exampleProgram2);
        programDictionary.insert(3, HardcodedExamples::exampleProgram3);
        programDictionary.insert(4, HardcodedExamples::fileExample);
        programDictionary.insert(5, HardcodedExamples::heapReadExample);
        programDictionary.insert(6, HardcodedExamples::heapWriteExample);
        programDictionary.insert(7, HardcodedExamples::heapAllocExample);
        programDictionary.insert(8, HardcodedExamples::garbageCollectorExample);
        programDictionary.insert(9, HardcodedExamples::whileExample);
        programDictionary.insert(10, HardcodedExamples::forkExample);
        programDictionary.insert(11, HardcodedExamples::typeErrorExample);

        // Add commands dynamically with program suppliers
        for (Integer key : programDictionary.getKeys()) {
            try {
                Supplier<IStatement> programSupplier = programDictionary.getValue(key);
                // Use a supplier to set up the new PrgState in the existing Controller and Repo when needed
                // Lambda expression to update the controller to use the right program state
                Command command = new SetupExample(key,
                        programSupplier.get().toString(),
                        programSupplier,
                        controller);
                menu.addCommand(command);
            }
            catch (Exception e){
                System.out.println("An exception occurred when creating the menu interface: " + e.getMessage());
            }
        }
        menu.show();
        System.out.println("Program finished");

    }
}
