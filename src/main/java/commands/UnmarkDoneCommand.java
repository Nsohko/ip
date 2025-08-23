package commands;

import chalkmain.Chalk;

public class UnmarkDoneCommand extends ChalkCommand {

    private final String inputCommand;

    public UnmarkDoneCommand(String inputCommand) {
        this.inputCommand = inputCommand;
    }

    @Override
    public void execute (Chalk chalk) {
        try {
             // taskNumber is 1-indexed
            int taskNumber = Integer.parseInt(inputCommand.split(" ")[1]);
            chalk.unmarkTaskAsDone(taskNumber);
        } catch (NumberFormatException e) {
             chalk.ui.printError("""
                Invalid task number!
                Usage: unmark [taskNumber]
                """);
        }
    }
    
}
