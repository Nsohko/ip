package chalk;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

import chalk.commands.ChalkCommand;
import chalk.storage.FileStorage;
import chalk.tasks.Task;
import chalk.tasks.TaskList;
import chalk.ui.GuiUI;
import chalk.ui.TextUI;

/**
 * The Chalk class is the main class of the Chalk application.
 */
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
     * Chalk's textUI object. Responsible for output onto CLI
     */
    private final TextUI textUI;

    /**
     * Chalk's guiUI object. Responsible for output onto JavaFX
     * Optional because Chalk can be run in CLI mode only
     * Initialized to empty optional by default
     */
    private Optional<GuiUI> guiUI = Optional.empty();

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
    private boolean isRunning;

    /**
     * Initializes the Chalk object, and starts its running
     * If an error occurs during initializations, terminates Chalk and returns early
     * If an explicit non-null GuiUI object is passed in, Chalk will also use it for JavaFX output
     */
    public Chalk(GuiUI guiUI) {

        this.guiUI = Optional.ofNullable(guiUI);

        this.textUI = new TextUI();
        

        this.storage = new FileStorage(PATH_TO_STORAGE);

        try {
            this.taskList = this.storage.load();
            this.printReply("Storage Initialized!");
        } catch (IOException e) {
            this.printError("Unable to create File Storage. Terminating early.");
            return;
        }

        String message = """
            Hello! I'm %s
            What can I do for you?
            """.formatted(Chalk.NAME);

        this.printReply(message);
        this.isRunning = true;

        assert this.textUI != null;
        assert this.storage != null;
        assert this.guiUI != null;
        assert (!this.isRunning) || (this.taskList != null);
    }

    /**
     * Initializes the Chalk object, and starts its running
     * If no guiUI is passed, Chalk will run in CLI mode only
     */
    public Chalk() {
        this(null);
    }

    /**
     * Prints out a message with appropriate formatting
     * Prints to both CLI and JavaFX, if guiUI is present
     * Else just prints to CLI
     *
     * @param message The message to be printed out
     */
    public final void printReply(String message) {
        this.textUI.printReply(message);
        this.guiUI.ifPresent(gui -> gui.printReply(message));
    }

    /**
     * Prints out an error message with appropriate formatting
     * Prints to both CLI and JavaFX, if guiUI is present
     * Else just prints to CLI
     *
     * @param message The error to be printed out
     */
    public final void printError(String message) {
        this.textUI.printError(message);
        this.guiUI.ifPresent(gui -> gui.printError(message));
    }

    /**
     * Terminates the chalk object
     */
    public void terminate() {
        String message = "Bye. Hope to see you again soon!";
        this.printReply(message);

        this.isRunning = false;

        assert this.isRunning == false;
    }

    /**
     * Lists all tasks stored inside the task list
     */
    public void listTasks() {
        this.printReply(this.taskList.toString());
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
            this.printReply(message);

        } catch (IllegalArgumentException | IOException e) {
            this.printError(e.getMessage());
        }
    }

    /**
     * Marks the corresponding task as done
     *
     * @param taskNumber The 1-indexed position of the task to be marked as done
     *     (i.e. the first task is 1)
     */
    public void markTaskAsDone(int taskNumber) {
        try {
            Task task = this.taskList.markAsDone(taskNumber);
            this.storage.overWriteWithTaskList(taskList);
            String message = """
                Nice! I've marked this task as done:
                    %s
                """.formatted(task.toString());
            this.printReply(message);

        } catch (IndexOutOfBoundsException | IOException e) {
            this.printError(e.getMessage());
        }
    }

    /**
     * Unmarks the corresponding task
     *
     * @param taskNumber The 1-indexed position of the task to be unmarked
     *     (i.e. the first task is 1)
     */
    public void unmarkTaskAsDone(int taskNumber) {
        try {
            Task task = this.taskList.unmarkAsDone(taskNumber);
            this.storage.overWriteWithTaskList(taskList);
            String message = """
                OK, I've marked this task as not done yet:
                    %s
                """.formatted(task.toString());
            this.printReply(message);
        } catch (IndexOutOfBoundsException | IOException e) {
            this.printError(e.getMessage());
        }
    }

    /**
     * Deletes the corresponding task
     *
     * @param taskNumber The 1-indexed position of the task to be deleted
     *     (i.e. the first task is 1)
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
            this.printReply(message);
        } catch (IndexOutOfBoundsException | IOException e) {
            this.printError(e.getMessage());
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
        this.printReply(message);
    }

    public static void main(String[] args) {

        Chalk chalk = new Chalk();

        try (Scanner s = new Scanner(System.in)) {
            String userInput;

            while (chalk.isRunning && s.hasNext()) {
                userInput = s.nextLine();
                ChalkCommand command = ChalkCommand.parse(userInput);
                command.execute(chalk);
            }
        }

    }
}
