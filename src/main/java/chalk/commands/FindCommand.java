package chalk.commands;

import chalk.Chalk;

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

        assert this.inputCommand != null;
    }

    /**
     * {@inheritDoc} Searches for matching tasks in the Chalk object
     *
     * @param chalk The Chalk object to search for tasks in
     */
    @Override
    public void execute(Chalk chalk) {
        // skip 5 chars ("find ")
        String searchParamString = this.inputCommand.substring(4).strip();

        if (searchParamString.isEmpty()) {
            chalk.printError("""
                Search parameter cannot be empty!
                Usage: find [searchwParam]
                """);
            return;
        }

        String[] searchParams = searchParamString.split("\\s+");

        System.out.println("Executing find command...");
        for (String s : searchParams) {
            System.out.println("Param: [" + s + "]");
        }

        System.out.println("Search params: ");
        for (String param : searchParams) {
            System.out.println("[" + param + "]");
        }

        if (searchParams.length == 0) {
            chalk.printError("""
                Search parameter cannot be empty!
                Usage: find [searchwParam]
                """);
            return;
        }
        chalk.searchTasks(searchParams);
    }
}
