package chalk.tasks;

/**
 * The Todo class represents a Todo task in Chalk.
 */
public class Todo extends Task {

    /**
     * Constructor for Todo object
     *
     * @param taskName The name of the Todo
     */
    public Todo(String taskName) {
        super(taskName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toFileStorage() {
        return "todo " + this.getName()
                + super.toFileStorage();
    }

    /**
     * Creates a Todo task from an input command string
     *
     * @param inputCommand The input command string
     * @return A Todo task created from the input command string
     * @throws IllegalArgumentException If the input command string is invalid
     */
    public static Todo fromInputCommand(String inputCommand) throws IllegalArgumentException {
        // skip the beginning 5 chars ("todo")
        String taskName = inputCommand.substring(4).stripLeading();

        // strip to ensure it isnt just whitespace
        if (taskName.strip().isEmpty()) {
            throw new IllegalArgumentException("""
                    Todo task name cannot be empty.
                    Usage: todo [taskName]
                    """);
        }

        return new Todo(taskName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }

        Todo otherTodo = (Todo) other;
        return super.equals(otherTodo);
    }
}
