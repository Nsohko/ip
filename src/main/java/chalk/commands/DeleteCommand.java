package chalk.commands;

import chalk.Chalk;

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
            chalk.ui.printError("""
                Invalid task number!
                Usage: delete [taskNumber]
                """);
        }

    }

}
