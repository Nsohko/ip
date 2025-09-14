package chalk.tasks;

import java.time.format.DateTimeParseException;

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

    /**
     * Creates a Deadline task from an input command string
     *
     * @param inputCommand The input command string
     * @return A Deadline task created from the input command string
     * @throws IllegalArgumentException If the input command string is invalid
     */
    public static Deadline fromInputCommand(String inputCommand) throws IllegalArgumentException {
        // split into an array containing [taskName, deadlineTime]
        String[] parts = inputCommand.substring(9).split(" /by ", 2);

        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new IllegalArgumentException("""
                    Deadline task name and due date cannot be empty.
                    Usage: deadline [taskName] /by [dueDate]
                    """);
        }

        String taskName = parts[0];

        DateTime deadlineTime;
        try {
            deadlineTime = new DateTime(parts[1]);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("""
                    Provide deadline due date in the following format:
                    dd/mm/yyyy HHmm (e.g. 31/10/2025 1800 for 31 October 2025, 6pm)
                    """);
        }
        return new Deadline(taskName, deadlineTime);
    }
}
