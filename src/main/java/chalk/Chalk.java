package chalk;

import java.io.IOException;
import java.util.Scanner;

import chalk.commands.ChalkCommand;
import chalk.storage.FileStorage;
import chalk.tasks.Task;
import chalk.tasks.TaskList;
import chalk.ui.Ui;

public class Chalk {

    private static final String NAME = "Chalk";
    private static final String PATH_TO_STORAGE = "./ChalkData/Storage.txt";

    public final Ui ui;
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

    public void listTasks() {
        this.ui.reply(this.taskList.toString());
    }

    public void addTask(Task newTask) {
        try {
            this.storage.addTask(newTask);
            this.taskList.addTask(newTask);

            String message = """
                Got it. I've added this task:
                    %s
                Now you have %d tasks in the list.
                """.formatted(newTask.toString(), this.taskList.size());
             this.ui.reply(message);

        } catch (IllegalArgumentException | IOException e) {
            this.ui.printError(e.getMessage());
        }
    }

    public void markTaskAsDone(int taskNumber) {
        try {
            Task task = this.taskList.markAsDone(taskNumber);
            this.storage.overWriteWithTaskList(taskList);
            String message = """
                Nice! I've marked this task as done:
                    %s
                """.formatted(task.toString());
            this.ui.reply(message);
            
        } catch (IndexOutOfBoundsException | IOException e) {
            this.ui.printError(e.getMessage());
        }
    }

    public void unmarkTaskAsDone(int taskNumber) {
        try {
            Task task = this.taskList.unmarkAsDone(taskNumber);
            this.storage.overWriteWithTaskList(taskList);
            String message = """
                OK, I've marked this task as not done yet:
                    %s
                """.formatted(task.toString());
            this.ui.reply(message);
        } catch (IndexOutOfBoundsException | IOException  e) {
            this.ui.printError(e.getMessage());
        }
    }

    public void deleteTask (int taskNumber) {
        try {
            Task task = this.taskList.deleteTask(taskNumber);
            this.storage.overWriteWithTaskList(taskList);
            String message = """
                Noted. I've removed this task:
                    %s
                Now you have %d tasks in the list.
                """.formatted(task.toString(), this.taskList.size());
            this.ui.reply(message);
        } catch (IndexOutOfBoundsException | IOException e) {
            this.ui.printError(e.getMessage());
        }
    }

    public static void main(String[] args) {

        Chalk chalk = new Chalk();

        Scanner scanner = new Scanner(System.in);
        String userInput;

        while (chalk.running && scanner.hasNext()) {
            userInput = scanner.nextLine();
            ChalkCommand command = ChalkCommand.parse(userInput);
            command.execute(chalk);
        }

        scanner.close();
    }
}
