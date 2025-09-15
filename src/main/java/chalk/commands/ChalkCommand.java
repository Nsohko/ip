package chalk.commands;

import chalk.Chalk;

/**
 * The ChalkCommand class is the base class for all commands in Chalk.
 */
public abstract class ChalkCommand {

    /** Flag for ExitCommand */
    private static final String END_CONVERSATION_STRING = "bye";

    /** Flag for ListCommand */
    private static final String LIST_TASKS_STRING = "list";

    /** Flag for MarkDoneCommand */
    private static final String MARK_TASK_AS_DONE_STRING = "mark";

    /** Flag for UnmarkDoneCommand */
    private static final String UNMARK_TASK_AS_DONE_STRING = "unmark";

    /** Flag for DeleteCommand */
    private static final String DELETE_TASK_STRING = "delete";

    /** Flag for FindCommand */
    private static final String FIND_TASK_STRING = "find";

    /**
     * Executes some behaviour of the Chalk object based on the specific command
     * that invokes it
     */
    public abstract void execute(Chalk chalk);

    /**
     * Parses and creates the appropriate command subtype from an input command
     * Acts like a factory method
     *
     * @param input The entire user inpuut to be parsed into a command
     */
    public static ChalkCommand parse(String input) {

        input = input.strip();
        if (input.equalsIgnoreCase(END_CONVERSATION_STRING)) {
            return new ExitCommand();
        } else if (input.equalsIgnoreCase(LIST_TASKS_STRING)) {
            return new ListCommand();
        } else if (input.toLowerCase().startsWith(MARK_TASK_AS_DONE_STRING)) {
            return new MarkDoneCommand(input);
        } else if (input.toLowerCase().startsWith(UNMARK_TASK_AS_DONE_STRING)) {
            return new UnmarkDoneCommand(input);
        } else if (input.toLowerCase().startsWith(DELETE_TASK_STRING)) {
            return new DeleteCommand(input);
        } else if (input.toLowerCase().startsWith(FIND_TASK_STRING)) {
            return new FindCommand(input);
        } else {
            return new AddCommand(input);
        }
    }

    public boolean shouldExit() {
        return false;
    }
}
