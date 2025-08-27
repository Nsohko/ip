package chalk.commands;

import chalk.Chalk;
import chalk.tasks.Task;

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
            chalk.ui.printError(e.getMessage());
        }
    }
}
