package commands;

import chalkmain.Chalk;

public class ListCommand extends ChalkCommand {

    @Override
    public void execute (Chalk chalk) {
        chalk.listTasks();
    }
    
}
