package chalk.commands;

import chalk.Chalk;

public abstract class ChalkCommand {

    private static final String END_CONVERSATION_STRING = "bye";
    private static final String LIST_TASKS_STRING = "list";

    private static final String MARK_TASK_AS_DONE_STRING = "mark "; // matches string that starts with "mark " 
    private static final String UNMARK_TASK_AS_DONE_STRING = "unmark "; // matches string that starts with "unmark " 
    private static final String DELETE_TASK_STRING = "delete "; // matches string that starts with "delete "

    public abstract void execute (Chalk chalk);

    public static ChalkCommand parse(String input) {

        if (input.equals(END_CONVERSATION_STRING)) {
            return new ExitCommand();
        } else if(input.equals(LIST_TASKS_STRING)) {
            return new ListCommand();
        } else if(input.startsWith(MARK_TASK_AS_DONE_STRING)) {
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
