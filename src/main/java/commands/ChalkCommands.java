package commands;

public enum ChalkCommands {

    BYE, LIST, MARK, UNMARK, DELETE, ADD;

    private static final String END_CONVERSATION_STRING = "bye";
    private static final String LIST_TASKS_STRING = "list";

    /*
     * The following commands are of the folloing format [commandName] [taskNumber]
     * Currently, the regex for these will match negative inputs as well, to 
     * However, this is obviously erroneous input so it should be handled in their respective methods (e.g. markTaskAsDone, unmarkTaskAsDone, etc.)
     * The reason we allow negative input at this stage is to direct the code to the correct handler, so that a more specific error message can be generated if necessary
     */
    private static final String MARK_TASK_AS_DONE_STRING = "mark -?\\d+"; // matches string that starts with "mark " and has one or more digits after
    private static final String UNMARK_TASK_AS_DONE_STRING = "unmark -?\\d+"; // matches string that starts with "unmark " and has one or more digits after
    private static final String DELETE_TASK_STRING = "delete -?\\d+"; // matches string that starts with "delete " and has one or more digits after

    public static ChalkCommands parse(String input) {

        if (input.equals(END_CONVERSATION_STRING)) {
            return ChalkCommands.BYE;
        } else if(input.equals(LIST_TASKS_STRING)) {
            return ChalkCommands.LIST;
        } else if(input.matches(MARK_TASK_AS_DONE_STRING)) {
            return ChalkCommands.MARK;
        } else if (input.matches(UNMARK_TASK_AS_DONE_STRING)) {
            return ChalkCommands.UNMARK;
        } else if (input.matches(DELETE_TASK_STRING)) {
            return ChalkCommands.DELETE;
        } else {
            return ChalkCommands.ADD;
        }
    }
    
}
