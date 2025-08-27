package chalk.tasks;

import chalk.datetime.DateTime;

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
