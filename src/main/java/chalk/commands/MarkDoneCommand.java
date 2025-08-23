package chalk.commands;

import chalk.Chalk;

public class MarkDoneCommand extends ChalkCommand {

    private final String inputCommand;

    public MarkDoneCommand(String inputCommand) {
        this.inputCommand = inputCommand;
    }


    @Override
    public void execute (Chalk chalk) {
        try {
             // taskNumber is 1-indexed
            int taskNumber = Integer.parseInt(inputCommand.split(" ")[1]);
            chalk.markTaskAsDone(taskNumber);
        } catch (NumberFormatException e) {
             chalk.ui.printError("""
                Invalid task number!
                Usage: mark [taskNumber]
                """);
        }
    }
    
}
