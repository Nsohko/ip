package chalk.commands;

import chalk.Chalk;

public class ListCommand extends ChalkCommand {

    @Override
    public void execute(Chalk chalk) {
        chalk.listTasks();
    }
}
