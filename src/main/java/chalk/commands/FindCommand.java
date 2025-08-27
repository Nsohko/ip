package chalk.commands;

import chalk.Chalk;

public class FindCommand extends ChalkCommand {

    private final String inputCommand;

    public FindCommand(String inputCommand) {
        this.inputCommand = inputCommand;
    }


    @Override
    public void execute (Chalk chalk) {
        // skip 5 chars ("find ")
        String searchParam = this.inputCommand.substring(5);
        chalk.searchTasks(searchParam);
    }
    
}
