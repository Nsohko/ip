package chalk.commands;

import chalk.Chalk;
import chalk.ui.GuiUI;

/**
 * The DeleteCommand class represents a command to delete a task from the Chalk object.
 */
public class DeleteCommand extends ChalkCommand {

    /**
     * The full command associated with this object
     */
    private final String inputCommand;

    /**
     * Constructor for DeleteCommand
     *
     * @param inputCommand The full command inputted by the user
     */
    public DeleteCommand(String inputCommand) {
        this.inputCommand = inputCommand;
    }

    /**
     * {@inheritDoc} Deletes a task from the Chalk object's task list
     *
     * @param chalk The Chalk object from which to delete the task
     */
    @Override
    public void execute(Chalk chalk) {
        try {
            // taskNumber is 1-indexed
            int taskNumber = Integer.parseInt(this.inputCommand.split(" ")[1]);
            chalk.deleteTask(taskNumber);
        } catch (NumberFormatException e) {
            chalk.textUI.printError("""
                Invalid task number!
                Usage: delete [taskNumber]
                """);
        }
    }

    /**
     * {@inheritDoc} Deletes a task from the Chalk object's task list
     *
     * @param chalk The Chalk object from which to delete the task
     */
    @Override
    public void execute(Chalk chalk, GuiUI guiUI) {
        try {
            // taskNumber is 1-indexed
            int taskNumber = Integer.parseInt(this.inputCommand.split(" ")[1]);
            chalk.deleteTask(taskNumber, guiUI);
        } catch (NumberFormatException e) {
            guiUI.error("""
                Invalid task number!
                Usage: delete [taskNumber]
                """);
        }
    }

}
