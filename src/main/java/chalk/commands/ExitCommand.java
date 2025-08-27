package chalk.commands;

import chalk.Chalk;

public class ExitCommand extends ChalkCommand {

    /**
     * {@inheritDoc} Terminates the Chalk object
     *
     * @param chalk The Chalk object to terminate
     */
    @Override
    public void execute(Chalk chalk) {
        chalk.terminate();
    }
}
