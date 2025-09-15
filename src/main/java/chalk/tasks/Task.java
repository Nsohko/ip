package chalk.tasks;

import chalk.storage.Storable;

/**
 * The Task class is the base class for all tasks in Chalk.
 */
public abstract class Task implements Storable {

    /**
     * The name of this Task
     */
    private final String taskName;

    /**
     * Boolean representing whether or not this task is done
     */
    private boolean isDone;

    /**
     * Base constructor for Task
     *
     * @param taskName The name of the Task
     */
    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    /**
     * Marks current task as done
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Unmarks current task
     */
    public void unmarkAsDone() {
        this.isDone = false;
    }

    /**
     * Returns name of current task
     */
    public String getName() {
        return this.taskName;
    }

    /**
     * Returns whether current task is done
     */
    public boolean getIsDone() {
        return this.isDone;
    }

    /**
     * Check if the current task conflicts with the other
     * Base implementation is to check for equality
     */
    public boolean checkConflict(Task otherTask) {
        return this.equals(otherTask) || otherTask.equals(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "["
                + (this.isDone ? "X" : " ")
                + "] "
                + this.taskName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        Task otherTask = (Task) other;
        return this.getName().equals(otherTask.getName())
                && this.getIsDone() == otherTask.getIsDone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toFileStorage() {
        String completionStatus = this.isDone ? "1" : "0";
        return " | " + completionStatus;
    }

    /**
     * Creates the appropriate task subtype from an input command Acts like a
     * factory method
     *
     * @param inputCommand The entire command to be parsed into a task
     */
    public static Task fromInputCommand(String inputCommand) throws IllegalArgumentException {

        inputCommand = inputCommand.strip();
        if (inputCommand.startsWith("todo")) {
            return Todo.fromInputCommand(inputCommand);
        } else if (inputCommand.startsWith("deadline")) {
            return Deadline.fromInputCommand(inputCommand);

        } else if (inputCommand.startsWith("event")) {
            return Event.fromInputCommand(inputCommand);
        }
        throw new IllegalArgumentException("Unknown Command: " + inputCommand);
    }
}
