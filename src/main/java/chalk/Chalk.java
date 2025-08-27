package chalk;

import java.io.IOException;
import java.util.Scanner;

import chalk.commands.ChalkCommand;
import chalk.storage.FileStorage;
import chalk.tasks.Task;
import chalk.tasks.TaskList;
import chalk.ui.Ui;

public class Chalk {

    /**
     * Chalk's name
     */
    private static final String NAME = "Chalk";

    /**
     * Default path to storage file
     */
    private static final String PATH_TO_STORAGE = "./ChalkData/Storage.txt";

    /**
     * Chalk's UI object. Responsible for output onto STDIO
     */
    public final Ui ui;

    /**
     * Chalk's FileStorage object. Resposible for managing tasks in file storage
     */
    private final FileStorage storage;

    /**
     * Chalk's TaskList object. Responsible for managing tasks in memory
     */
    private TaskList taskList;

    /**
     * Boolean representing whether or not this Chalk object is running
     */
    private boolean running;

    /**
     * Initializes the Chalk object, and starts its running If an error occurs
     * during initializations, terminates Chalk and returns early
     */
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

    /**
     * Terminates the chalk object
     */
    public void terminate() {
        String message = "Bye. Hope to see you again soon!";
        this.ui.reply(message);

        this.running = false;
    }

    /**
     * Lists all tasks stored inside the
     */
    public void listTasks() {
        this.ui.reply(this.taskList.toString());
    }

    /**
     * Adds a task to the chalk object
     *
     * @param newTask The new task to be added
     */
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

    /**
     * Marks the corresponding task as done
     *
     * @param taskNumber The 1-indexed position of the task to be marked as done
     * (i.e. the first task is 1)
     */
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

    /**
     * Unmarks the corresponding task
     *
     * @param taskNumber The 1-indexed position of the task to be unmarked (i.e.
     * the first task is 1)
     */
    public void unmarkTaskAsDone(int taskNumber) {
        try {
            Task task = this.taskList.unmarkAsDone(taskNumber);
            this.storage.overWriteWithTaskList(taskList);
            String message = """
                OK, I've marked this task as not done yet:
                    %s
                """.formatted(task.toString());
            this.ui.reply(message);
        } catch (IndexOutOfBoundsException | IOException e) {
            this.ui.printError(e.getMessage());
        }
    }

    /**
     * Deletes the corresponding task
     *
     * @param taskNumber The 1-indexed position of the task to be deleted (i.e.
     * the first task is 1)
     */
    public void deleteTask(int taskNumber) {
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

    /**
     * Searches for tasks whose name constains searchParam
     *
     * @param searchParam The search parameter to match tasks' names against
     */
    public void searchTasks(String searchParam) {

        TaskList filteredTaskList = this.taskList.searchTasks(searchParam);

        String message;
        if (filteredTaskList.size() == 0) {
            message = "No tasks found!";
        } else {
            message = """
                Here are the matching tasks in your list:
                %s
                """.formatted(filteredTaskList.toString());
        }
        this.ui.reply(message);
    }

    public static void main(String[] args) {

        Chalk chalk = new Chalk();

        try (Scanner s = new Scanner(System.in)) {
            String userInput;

            while (chalk.running && s.hasNext()) {
                userInput = s.nextLine();
                ChalkCommand command = ChalkCommand.parse(userInput);
                command.execute(chalk);
            }
        }

    }
}
