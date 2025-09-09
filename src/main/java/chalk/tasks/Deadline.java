package chalk.tasks;

import chalk.datetime.DateTime;

/**
 * The Deadline class represents a Deadline task in Chalk.
 * A Deadline has a name and a due date.
 */
public class Deadline extends Task {

    /**
     * The due date of this Deadline
     */
    private final DateTime deadlineTime;

    /**
     * Constructor for Deadline object
     *
     * @param taskName The name of the Deadline
     * @param deadlineTime The due date of this Deadline
     */
    public Deadline(String taskName, DateTime deadlineTime) {
        super(taskName);
        this.deadlineTime = deadlineTime;

        assert this.deadlineTime != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[D]" + super.toString()
                + " (by: " + this.deadlineTime.toString() + ")";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toFileStorage() {
        return "deadline " + this.getName()
                + " /by " + this.deadlineTime.toFileStorage()
                + super.toFileStorage();
    }
}
