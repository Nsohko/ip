package chalk.commands;

import chalk.Chalk;

/**
 * The MarkDoneCommand class represents a command to mark a task as done in the Chalk object.
 */
public class MarkDoneCommand extends ChalkCommand {

    /**
     * The full command associated with this object
     */
    private final String inputCommand;

    /**
     * Constructor for MarkDoneCommand
     *
     * @param inputCommand The full command inputted by the user
     */
    public MarkDoneCommand(String inputCommand) {
        this.inputCommand = inputCommand;
    }

    /**
     * {@inheritDoc} Marks a task in the Chalk object's taskList as done
     *
     * @param chalk The Chalk object to mark the task in
     */
    @Override
    public void execute(Chalk chalk) {
        try {
            // taskNumber is 1-indexed
            int taskNumber = Integer.parseInt(inputCommand.split(" ")[1]);
            chalk.markTaskAsDone(taskNumber);
        } catch (NumberFormatException e) {
            chalk.ui.printError("""
                Invalid task number!
                Usage: mark [taskNumber]
                """);
        }
    }

}
