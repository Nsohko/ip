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
}
