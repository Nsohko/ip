import commands.ChalkCommands;
import java.io.IOException;
import java.util.Scanner;
import storage.FileStorage;
import tasks.Task;
import tasks.TaskList;
import ui.Ui;

public class Chalk {

    private static final String NAME = "Chalk";
    private static final String PATH_TO_STORAGE = "./ChalkData/Storage.txt";

    private final Ui ui;
    private final FileStorage storage;
    private TaskList taskList;
    
    private boolean running;

    public Chalk() {

        this.ui = new Ui();
        this.storage = new FileStorage(PATH_TO_STORAGE);

        try {
            this.taskList = this.storage.load();
            this.ui.reply("Storage Initialized!");
        } catch (IOException e) {
            this.ui.printError("Unable to create File Storage. Terminating early.");
            return;
        }

        String message = """
            Hello! I'm %s
            What can I do for you?
            """.formatted(Chalk.NAME);
        
        this.ui.reply(message);
        this.running = true;

       
    }

    public void terminate() {
        String message = "Bye. Hope to see you again soon!";
        this.ui.reply(message);

        this.running = false;
    }

    public void listTaks() {
        this.ui.reply(this.taskList.toString());
    }

    public void addTask(String command) {
        try {
            Task newTask = Task.fromCommand(command);
            this.storage.addTask(command);
            this.taskList.addTask(newTask);

            String message = """
                Got it. I've added this task:
                    %s
                Now you have %d tasks in the list.
                """.formatted(newTask.toString(), this.taskList.size());
             this.ui.reply(message);

        } catch (IllegalArgumentException | IOException e) {
            String errorMessage = e.getMessage();
            this.ui.printError(errorMessage);
        }
    }

    public void markTaskAsDone(String command) {
        // taskNumber is 1-indexed
        int taskNumber = Integer.parseInt(command.split(" ")[1]);

        try {
            Task task = this.taskList.markAsDone(taskNumber);
            this.storage.overWriteWithTaskList(taskList);
            String message = """
                Nice! I've marked this task as done:
                    %s
                """.formatted(task.toString());
            this.ui.reply(message);
            
        } catch (IndexOutOfBoundsException e) {
            String errorMessage =  "There is no task with that number!";
            this.ui.printError(errorMessage);
            
        } catch (IOException e) {
            String errorMessage =  "Failed to update task in Storage!";
            this.ui.printError(errorMessage);
        }
    }

    public void unmarkTaskAsDone(String command) {
        // taskNumber is 1-indexed
        int taskNumber = Integer.parseInt(command.split(" ")[1]);

        try {
            Task task = this.taskList.unmarkAsDone(taskNumber);
            this.storage.overWriteWithTaskList(taskList);
            String message = """
                OK, I've marked this task as not done yet:
                    %s
                """.formatted(task.toString());
            this.ui.reply(message);
        } catch (IndexOutOfBoundsException e) {
            String errorMessage =  "There is no task with that number!";
            this.ui.printError(errorMessage);
        } catch (IOException e) {
            String errorMessage =  "Failed to update task in Storage!";
            this.ui.printError(errorMessage);
        }
    }

    public void deleteTask (String command) {
        // taskNumber is 1-indexed
        int taskNumber = Integer.parseInt(command.split(" ")[1]);

        try {
            Task task = this.taskList.deleteTask(taskNumber);
            this.storage.overWriteWithTaskList(taskList);
            String message = """
                Noted. I've removed this task:
                    %s
                Now you have %d tasks in the list.
                """.formatted(task.toString(), this.taskList.size());
            this.ui.reply(message);
        } catch (IndexOutOfBoundsException e) {
            String errorMessage =  "There is no task with that number!";
            this.ui.printError(errorMessage);
        } catch (IOException e) {
            String errorMessage = "Failed to delete task from Storage!";
            this.ui.printError(errorMessage);
        }
    }

    public static void main(String[] args) {

        Chalk chalk = new Chalk();

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
