package chalk.commands;

import chalk.Chalk;

public abstract class ChalkCommand {

    /**
     * Flag for ExitCommand
     */
    private static final String END_CONVERSATION_STRING = "bye";

    /**
     * Flag for ListCommand
     */
    private static final String LIST_TASKS_STRING = "list";

    /**
     * Flag for MarkDoneCommand
     */
    private static final String MARK_TASK_AS_DONE_STRING = "mark ";

    /**
     * Flag for UnmarkDoneCommand
     */
    private static final String UNMARK_TASK_AS_DONE_STRING = "unmark ";

    /**
     * Flag for DeleteCommand
     */
    private static final String DELETE_TASK_STRING = "delete ";

    /**
     * Executes some behaviour of the Chalk object based on the specific command
     * that invokes it
     */
    public abstract void execute(Chalk chalk);

    /**
     * Parses and creates the appropriate command subtype from an input command
     * Acts like a factory method
     *
     * @param input The entire command to be parsed into a command
     */
    public static ChalkCommand parse(String input) {

        if (input.equals(END_CONVERSATION_STRING)) {
            return new ExitCommand();
        } else if (input.equals(LIST_TASKS_STRING)) {
            return new ListCommand();
        } else if (input.startsWith(MARK_TASK_AS_DONE_STRING)) {
            return new MarkDoneCommand(input);
        } else if (input.startsWith(UNMARK_TASK_AS_DONE_STRING)) {
            return new UnmarkDoneCommand(input);
        } else if (input.startsWith(DELETE_TASK_STRING)) {
            return new DeleteCommand(input);
        } else {
            return new AddCommand(input);
        }
    }
}
