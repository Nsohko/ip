package chalk.commands;

import chalk.Chalk;

/**
 * The ExitCommand class represents a command to exit the Chalk application.
 */
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

    @Override
    public boolean shouldExit() {
        return true;
    }
}
