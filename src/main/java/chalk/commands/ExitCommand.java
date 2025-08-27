package chalk.commands;

import chalk.Chalk;

public class ExitCommand extends ChalkCommand {

    @Override
    public void execute(Chalk chalk) {
        chalk.terminate();
    }
}
