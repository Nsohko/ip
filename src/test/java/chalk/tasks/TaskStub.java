package chalk.tasks;

/**
 * A stub class for Task to be used in testing.
 */
public class TaskStub extends Task {

    /**
     * isDone represents whether or not the task has been finished
     */
    private boolean isDone = false;
    private final String name;

    /**
     * Base constructor for Task Stub
     *
     * @param name The name of the Task
     */
    public TaskStub(String name) {
        super(name);
        this.name = name;
    }

    @Override
    public void markAsDone() {
        this.isDone = true;
    }

    @Override
    public void unmarkAsDone() {
        this.isDone = false;
    }

    public boolean isDone() {
        return this.isDone;
    }

    @Override
    public String toString() {
        return (this.isDone ? "[X] " : "[ ] ") + name;
    }

    @Override
    public String toFileStorage() {
        return name + " | " + (this.isDone ? "1" : "0");
    }
}
