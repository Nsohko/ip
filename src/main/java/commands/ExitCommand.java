package commands;

import chalkmain.Chalk;

public class ExitCommand extends ChalkCommand {

    @Override
    public void execute (Chalk chalk) {
        chalk.terminate();
    }
    
}
