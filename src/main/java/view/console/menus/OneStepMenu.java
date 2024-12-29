package view.console.menus;

import exception.CompletedProgramException;
import view.console.commands.Command;

import java.util.Scanner;

public class OneStepMenu extends TextMenu {
    public OneStepMenu() {
        super();
    }

    @Override
    public void show() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Input the option: ");
            Integer key = scanner.nextInt();
            Command command = commands.get(key);
            if (command == null) {
                System.out.println("Invalid option");
                continue;
            }
            try {
                command.execute();
            }catch (CompletedProgramException e){
                System.out.println("All program threads have finished executing");
                running = false;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                running = false;
            }
        }
    }
}
