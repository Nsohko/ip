package chalk.commands;

import chalk.Chalk;
import chalk.tasks.Task;

public class AddCommand extends ChalkCommand {

    private final String inputCommand;

    public AddCommand(String inputCommand) {
        this.inputCommand = inputCommand;
    }

    @Override
    public void execute(Chalk chalk) {
        try {
            Task newTask = Task.fromInputCommand(inputCommand);
            chalk.addTask(newTask);
        } catch (IllegalArgumentException e) {
            chalk.ui.printError(e.getMessage());
        }
    }
}
