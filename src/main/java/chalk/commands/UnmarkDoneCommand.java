package chalk.commands;

import chalk.Chalk;
import chalk.ui.GuiUI;

/**
 * The UnmarkDoneCommand class represents a command to unmark a task as done in the Chalk object.
 */
public class UnmarkDoneCommand extends ChalkCommand {

    /**
     * The full command associated with this object
     */
    private final String inputCommand;

    /**
     * Constructor for UnmarkDoneCommand
     *
     * @param inputCommand The full command inputted by the user
     */
    public UnmarkDoneCommand(String inputCommand) {
        this.inputCommand = inputCommand;
    }

    /**
     * {@inheritDoc} Unmarks a task in the Chalk object's taskList
     *
     * @param chalk The Chalk object to unmark the task in
     */
    @Override
    public void execute(Chalk chalk) {
        try {
            // taskNumber is 1-indexed
            int taskNumber = Integer.parseInt(inputCommand.split(" ")[1]);
            chalk.unmarkTaskAsDone(taskNumber);
        } catch (NumberFormatException e) {
            chalk.textUI.printError("""
                Invalid task number!
                Usage: unmark [taskNumber]
                """);
        }
    }

    /**
     * {@inheritDoc} Unmarks a task in the Chalk object's taskList
     *
     * @param chalk The Chalk object to unmark the task in
     */
    @Override
    public void execute(Chalk chalk, GuiUI guiUI) {
        try {
            // taskNumber is 1-indexed
            int taskNumber = Integer.parseInt(inputCommand.split(" ")[1]);
            chalk.unmarkTaskAsDone(taskNumber, guiUI);
        } catch (NumberFormatException e) {
            guiUI.error("""
                Invalid task number!
                Usage: unmark [taskNumber]
                """);
        }
    }

}
