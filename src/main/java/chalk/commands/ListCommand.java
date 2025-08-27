package chalk.commands;

import chalk.Chalk;

public class ListCommand extends ChalkCommand {

    /**
     * {@inheritDoc} Lists all tasks in the Chalk object's taskList
     *
     * @param chalk The Chalk object whose tasks to list
     */
    @Override
    public void execute(Chalk chalk) {
        chalk.listTasks();
    }

}
