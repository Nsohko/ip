import commands.ChalkCommands;
import java.io.IOException;
import java.util.Scanner;
import storage.FileStorage;
import tasks.Task;
import tasks.TaskList;

public class Chalk {

    private static final String NAME = "Chalk";

    private TaskList taskList;
    private final FileStorage storage;
    private boolean running;

    public Chalk() {
        this.taskList = new TaskList();
        this.storage = new FileStorage();
    }

    public void initialize() {

        try {
            this.taskList = this.storage.initialize();
            this.say("Storage Initialized!");
        } catch (IOException e) {
            this.say("Error Creating File Storage. Terminating early.");
            return;
        }
        
        String message = """
            Hello! I'm %s
            What can I do for you?
            """.formatted(Chalk.NAME);

        this.say(message);

        this.running = true;
    }

    public void terminate() {
        String message = "Bye. Hope to see you again soon!";
        this.say(message);

        this.running = false;
    }

    public void say(String sentenceString) {

        String lineBreak = "-------------------------------";
        System.out.println(lineBreak);

        // split each line based on new line character, then prepend 4 spaces
        // not the best solution, because it doesnt account for when long lines wrap around
        // but it will do for now
        for (String line : sentenceString.split("\n")) {
            System.out.println("    " + line);
        }

        System.out.println(lineBreak);
    }

    public void listTaks() {
        this.say(this.taskList.toString());
    }

    public void addTask(String command) {
        String message;
        try {
            Task newTask = Task.fromCommand(command);
            this.storage.addTask(command);
            this.taskList.addTask(newTask);

            message = """
                Got it. I've added this task:
                    %s
                Now you have %d tasks in the list.
                """.formatted(newTask.toString(), this.taskList.size());

        } catch (IllegalArgumentException | IOException e) {
            message = e.getMessage();
        }
        
        this.say(message);
    }

    public void markTaskAsDone(String command) {
        // taskNumber is 1-indexed
        int taskNumber = Integer.parseInt(command.split(" ")[1]);

        String message;
        try {
            Task task = this.taskList.markAsDone(taskNumber);
            this.storage.overWriteWithTaskList(taskList);
            message = """
                Nice! I've marked this task as done:
                    %s
                """.formatted(task.toString());
            
        } catch (IndexOutOfBoundsException e) {
            message =  "Error! There is no task with that number!";
            
        } catch (IOException e) {
            message =  "Error! Failed to update task in Storage!";
        }
        this.say(message);
    }

    public void unmarkTaskAsDone(String command) {
        // taskNumber is 1-indexed
        int taskNumber = Integer.parseInt(command.split(" ")[1]);
        String message;
        try {
            Task task = this.taskList.unmarkAsDone(taskNumber);
            this.storage.overWriteWithTaskList(taskList);
            message = """
                OK, I've marked this task as not done yet:
                    %s
                """.formatted(task.toString());
        } catch (IndexOutOfBoundsException e) {
            message =  "Error! There is no task with that number!";
        } catch (IOException e) {
            message =  "Error! Failed to update task in Storage!";
        }
        this.say(message);
    }

    public void deleteTask (String command) {
        // taskNumber is 1-indexed
        int taskNumber = Integer.parseInt(command.split(" ")[1]);
        String message;
        try {
            Task task = this.taskList.deleteTask(taskNumber);
            this.storage.overWriteWithTaskList(taskList);
            message = """
                Noted. I've removed this task:
                    %s
                Now you have %d tasks in the list.
                """.formatted(task.toString(), this.taskList.size());
        } catch (IndexOutOfBoundsException e) {
            message =  "Error! There is no task with that number!";
        } catch (IOException e) {
            message = "Error! Failed to delete task from Storage!";
        }

        this.say(message);
    }

    public static void main(String[] args) {

        Chalk chalk = new Chalk();

        chalk.initialize();

        Scanner scanner = new Scanner(System.in);
        String userInput;

        while (chalk.running && scanner.hasNext()) {
            userInput = scanner.nextLine();

            switch (ChalkCommands.parse(userInput)) {
                case ChalkCommands.BYE -> chalk.terminate();
                case ChalkCommands.LIST -> chalk.listTaks();
                case ChalkCommands.MARK -> chalk.markTaskAsDone(userInput);
                case ChalkCommands.UNMARK -> chalk.unmarkTaskAsDone(userInput);
                case ChalkCommands.DELETE -> chalk.deleteTask(userInput);
                case ChalkCommands.ADD -> chalk.addTask(userInput);
            }
        }

        scanner.close();
    }
}
