package view;

import view.commands.Command;

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
            String key = scanner.nextLine();
            Command command = commands.get(key);
            if (command == null) {
                System.out.println("Invalid option");
                continue;
            }
            try{
                command.execute();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                running = false;
            }
        }
    }
}
