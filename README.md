# Toy Language Interpreter

A small Java interpreter project for exploring how a typed imperative language can be represented and executed. Programs are built from Java statement and expression objects, then evaluated by stepping through an explicit program state. The project includes a console menu with predefined programs and a JavaFX application scaffold.

## Architecture

The interpreter uses a layered, interpreter/virtual-machine design:

- **Model** defines the language: expressions evaluate against a program state, while statements execute and update it. A program state holds an execution stack, symbol table, output list, file table, and heap.
- **Repository** owns the active program-state list and writes state snapshots to a log file.
- **Controller** coordinates execution, removes completed states, performs conservative heap garbage collection, and advances active states. Forked states are scheduled through a fixed thread pool.
- **Views** provide a console menu for selecting and executing predefined examples, plus a JavaFX launcher scaffold.

Statements and expressions support `deepCopy` and type checking, so selected examples are checked before execution. Execution follows small steps: the interpreter pops the next statement from a program state's execution stack and executes it, potentially producing another state when a program forks.

## Language Scope

The implemented language model includes:

- Integer, boolean, string, and reference types
- Variable declaration and assignment
- Arithmetic, logical, and relational expressions
- Compound statements, conditionals, and while loops
- Printing values to the program output
- Heap allocation, heap reads, and heap writes through references
- Opening, reading, and closing input files
- Forking a program state, with child symbol tables and shared output, file table, and heap
- Conservative garbage collection during execution

The console examples are defined in Java code. There is currently no parser for entering programs as toy-language source text. The JavaFX launcher is a starter screen rather than an interpreter GUI; the console menu is the current way to run the examples.

## Technologies

- Java 23
- Maven and the Maven Wrapper
- JavaFX 17 (Controls and FXML)
- ControlsFX and FormsFX dependencies
- JUnit 5 dependencies for testing

## Project Structure

```text
src/main/java/
  controller/   Execution coordination and garbage collection
  examples/     Predefined programs represented as Java objects
  exception/    Interpreter-specific exception types
  model/
    adt/        Custom stack, list, dictionary, and heap abstractions
    expressions/Expression implementations and operators
    state/      Program execution state
    statements/ Statement implementations
    types/      Language type implementations
    value/      Runtime values
  repository/   Program-state storage and execution logging
  view/
    console/    Text menus and console commands
    GUI/        JavaFX application and controller
src/main/resources/view/GUI/
  hello-view.fxml
```

`test.in` is the sample input file used by the file-reading example. The console interpreter writes execution snapshots to `log.txt` in the working directory.

## Build and Run

Install a JDK 23 or newer-compatible Java 23 toolchain, then build with the wrapper:

```powershell
.\mvnw.cmd clean package
```

On macOS or Linux, use `./mvnw clean package` instead.

The configured JavaFX launcher can be started with:

```powershell
.\mvnw.cmd javafx:run
```

To run the interpreter examples, launch `view.console.Interpreter` from the IDE. Choose an example in the console menu, then execute it one step at a time or run it to completion. The file-reading example expects `test.in` to be present in the process working directory.