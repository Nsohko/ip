package chalk.tasks;

/**
 * A stub class for Task to be used in testing.
 */
public class TaskStub extends Task {

    /**
     * Base constructor for Task Stub
     *
     * @param name The name of the Task
     */
    public TaskStub(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return (this.getIsDone() ? "[X] " : "[ ] ") + getName();
    }
}
