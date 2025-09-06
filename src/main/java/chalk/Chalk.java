package chalk;

import java.io.IOException;
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
    public final TextUI textUI;

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
     * Initializes the Chalk object, and starts its running If an error occurs
     * during initializations, terminates Chalk and returns early
     */
    public Chalk() {

        this.textUI = new TextUI();

        this.storage = new FileStorage(PATH_TO_STORAGE);

        try {
            this.taskList = this.storage.load();
            this.textUI.reply("Storage Initialized!");
        } catch (IOException e) {
            this.textUI.printError("Unable to create File Storage. Terminating early.");
            return;
        }

        String message = """
            Hello! I'm %s
            What can I do for you?
            """.formatted(Chalk.NAME);

        this.textUI.reply(message);
        this.isRunning = true;
    }

    /**
     * Initializes the Chalk object, and starts its running If an error occurs
     * during initializations, terminates Chalk and returns early
     * Also prints to the JavaFX UI
     */
    public Chalk(GuiUI guiUI) {

        this.textUI = new TextUI();

        this.storage = new FileStorage(PATH_TO_STORAGE);

        try {
            this.taskList = this.storage.load();
            guiUI.reply("Storage Initialized!");
        } catch (IOException e) {
            guiUI.error("Unable to create File Storage. Terminating early.");
            return;
        }

        String message = """
            Hello! I'm %s
            What can I do for you?
            """.formatted(Chalk.NAME);

        guiUI.reply(message);
        this.isRunning = true;
    }

    /**
     * Terminates the chalk object
     */
    public void terminate() {
        String message = "Bye. Hope to see you again soon!";
        this.textUI.reply(message);

        this.isRunning = false;
    }

    /**
     * Terminates the chalk object, prints to javaFX
     */
    public void terminate(GuiUI guiUI) {
        String message = "Bye. Hope to see you again soon!";
        guiUI.reply(message);

        this.isRunning = false;
    }

    /**
     * Lists all tasks stored inside the task list
     */
    public void listTasks() {
        this.textUI.reply(this.taskList.toString());
    }

    /**
     * Lists all tasks stored inside the task list
     * Prints to JavaFX
     */
    public void listTasks(GuiUI guiUI) {
        guiUI.reply(this.taskList.toString());
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
            this.textUI.reply(message);

        } catch (IllegalArgumentException | IOException e) {
            this.textUI.printError(e.getMessage());
        }
    }

    /**
     * Adds a task to the chalk object
     * Prints to JavFX
     *
     * @param newTask The new task to be added
     */
    public void addTask(Task newTask, GuiUI guiUI) {
        try {
            this.storage.addTask(newTask);
            this.taskList.addTask(newTask);

            String message = """
                Got it. I've added this task:
                    %s
                Now you have %d tasks in the list.
                """.formatted(newTask.toString(), this.taskList.size());
            guiUI.reply(message);

        } catch (IllegalArgumentException | IOException e) {
            guiUI.error(e.getMessage());
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
            this.textUI.reply(message);

        } catch (IndexOutOfBoundsException | IOException e) {
            this.textUI.printError(e.getMessage());
        }
    }

    /**
     * Marks the corresponding task as done
     * Prints to JavaFx
     *
     * @param taskNumber The 1-indexed position of the task to be marked as done
     *     (i.e. the first task is 1)
     */
    public void markTaskAsDone(int taskNumber, GuiUI guiUI) {
        try {
            Task task = this.taskList.markAsDone(taskNumber);
            this.storage.overWriteWithTaskList(taskList);
            String message = """
                Nice! I've marked this task as done:
                    %s
                """.formatted(task.toString());
            guiUI.reply(message);

        } catch (IndexOutOfBoundsException | IOException e) {
            guiUI.error(e.getMessage());
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
            this.textUI.reply(message);
        } catch (IndexOutOfBoundsException | IOException e) {
            this.textUI.printError(e.getMessage());
        }
    }

    /**
     * Unmarks the corresponding task
     * Prints to JavaFX
     *
     * @param taskNumber The 1-indexed position of the task to be unmarked
     *     (i.e. the first task is 1)
     */
    public void unmarkTaskAsDone(int taskNumber, GuiUI guiUI) {
        try {
            Task task = this.taskList.unmarkAsDone(taskNumber);
            this.storage.overWriteWithTaskList(taskList);
            String message = """
                OK, I've marked this task as not done yet:
                    %s
                """.formatted(task.toString());
            guiUI.reply(message);
        } catch (IndexOutOfBoundsException | IOException e) {
            guiUI.error(e.getMessage());
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
            this.textUI.reply(message);
        } catch (IndexOutOfBoundsException | IOException e) {
            this.textUI.printError(e.getMessage());
        }
    }

    /**
     * Deletes the corresponding task
     * Prints to JavaFX
     *
     * @param taskNumber The 1-indexed position of the task to be deleted
     *     (i.e. the first task is 1)
     */
    public void deleteTask(int taskNumber, GuiUI guiUI) {
        try {
            Task task = this.taskList.deleteTask(taskNumber);
            this.storage.overWriteWithTaskList(taskList);
            String message = """
                Noted. I've removed this task:
                    %s
                Now you have %d tasks in the list.
                """.formatted(task.toString(), this.taskList.size());
            guiUI.reply(message);
        } catch (IndexOutOfBoundsException | IOException e) {
            guiUI.error(e.getMessage());
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
        this.textUI.reply(message);
    }

    /**
     * Searches for tasks whose name contains searchParam
     * Prints to JavaFX
     *
     * @param searchParam The search parameter to match tasks' names against
     */
    public void searchTasks(String searchParam, GuiUI guiUI) {

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
        guiUI.reply(message);
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
