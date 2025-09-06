package chalk.commands;

import chalk.Chalk;
import chalk.ui.GuiUI;

/**
 * The FindCommand class represents a command to find tasks in the Chalk object.
 */
public class FindCommand extends ChalkCommand {

    /**
     * The full command associated with this object
     */
    private final String inputCommand;

    /**
     * Constructor for FindCommand
     *
     * @param inputCommand The full command inputted by the user
     */
    public FindCommand(String inputCommand) {
        this.inputCommand = inputCommand;
    }

    /**
     * {@inheritDoc} Searches for matching tasks in the Chalk object
     *
     * @param chalk The Chalk object to search for tasks in
     */
    @Override
    public void execute(Chalk chalk) {
        // skip 5 chars ("find ")
        String searchParam = this.inputCommand.substring(5);
        chalk.searchTasks(searchParam);
    }

    /**
     * {@inheritDoc} Searches for matching tasks in the Chalk object
     *
     * @param chalk The Chalk object to search for tasks in
     */
    @Override
    public void execute(Chalk chalk, GuiUI guiUI) {
        // skip 5 chars ("find ")
        String searchParam = this.inputCommand.substring(5);
        chalk.searchTasks(searchParam, guiUI);
    }
}
