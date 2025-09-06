package chalk.commands;

import chalk.Chalk;
import chalk.tasks.Task;
import chalk.ui.GuiUI;

/**
 * AddCommand represents a command to add a task to the Chalk object.
 */
public class AddCommand extends ChalkCommand {

    /**
     * The full command associated with this object
     */
    private final String inputCommand;

    /**
     * Constructor for AddComand
     *
     * @param inputCommand The full command inputted by the user
     */
    public AddCommand(String inputCommand) {
        this.inputCommand = inputCommand;
    }

    /**
     * {@inheritDoc} Add a task to the Chalk object's taskList based on this
     * command's input
     *
     * @param chalk The Chalk object to add the task to
     */
    @Override
    public void execute(Chalk chalk) {
        try {
            Task newTask = Task.fromInputCommand(inputCommand);
            chalk.addTask(newTask);
        } catch (IllegalArgumentException e) {
            chalk.textUI.printError(e.getMessage());
        }
    }

    /**
     * {@inheritDoc} Add a task to the Chalk object's taskList based on this
     * command's input
     *
     * @param chalk The Chalk object to add the task to
     */
    @Override
    public void execute(Chalk chalk, GuiUI guiUI) {
        try {
            Task newTask = Task.fromInputCommand(inputCommand);
            chalk.addTask(newTask, guiUI);
        } catch (IllegalArgumentException e) {
            guiUI.error(e.getMessage());
        }
    }
}
