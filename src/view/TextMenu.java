package view;

import view.commands.Command;
import view.commands.RunExample;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;

    public TextMenu() {
        this.commands = new HashMap<>();
    }

    public void addCommand(Command command) {
        commands.put(command.getKey(), command);
    }

    public void printMenu() {
        for (Command command : commands.values()) {
            String line = String.format("%4s: %s", command.getKey(), command.getDescription());
            System.out.println(line);
        }
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("Input the option: ");
            String key = scanner.nextLine();
            Command command = commands.get(key);
            if (command == null) {
                System.out.println("Invalid option");
                continue;
            }
            createSubMenu(command);
        }
    }
    public void createSubMenu(Command command) {
        Scanner scanner = new Scanner(System.in);

        if (!(command instanceof RunExample)) {
            command.execute();
            return;
        }

        RunExample runExampleCommand = (RunExample) command;

        Map<String, Command> subCommands = new HashMap<>();
        subCommands.put("1", new RunExample("1", "Execute one step", runExampleCommand.getController(), true));
        subCommands.put("2", new RunExample("2", "Execute all steps", runExampleCommand.getController(), false));
        subCommands.put("0", new Command("0", "Back") {
            @Override
            public void execute() {
                return;
            }
        });
        while (true) {

            for (Command subCommand : subCommands.values()) {
                String line = String.format("%4s: %s", subCommand.getKey(), subCommand.getDescription());
                System.out.println(line);
            }
            System.out.print("Input the option: ");
            String option = scanner.nextLine();
            Command subCommand = subCommands.get(option);
            if (subCommand == null) {
                System.out.println("Invalid option");
                continue;
            }
            if (option.equals("0")) {
                return;
            }
            subCommand.execute();
        }
    }
}
