package chalk.commands;

import chalk.Chalk;
import chalk.ui.GuiUI;

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

    /**
     * {@inheritDoc} Terminates the Chalk object
     *
     * @param chalk The Chalk object to terminate
     */
    @Override
    public void execute(Chalk chalk, GuiUI guiUI) {
        chalk.terminate(guiUI);
    }

    @Override
    public boolean shouldExit() {
        return true;
    }
}
