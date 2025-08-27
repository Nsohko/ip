package chalk.commands;

import chalk.Chalk;

public class DeleteCommand extends ChalkCommand {

    private final String inputCommand;

    public DeleteCommand(String inputCommand) {
        this.inputCommand = inputCommand;
    }

    @Override
    public void execute(Chalk chalk) {
        try {
            // taskNumber is 1-indexed
            int taskNumber = Integer.parseInt(this.inputCommand.split(" ")[1]);
            chalk.deleteTask(taskNumber);
        } catch (NumberFormatException e) {
            chalk.ui.printError("""
                Invalid task number!
                Usage: delete [taskNumber]
                """);
        }

    }

}
