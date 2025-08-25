package chalk.tasks;

public class TaskStub extends Task {

    private boolean done = false;
    private final String name;

    public TaskStub(String name) {
        super(name);
        this.name = name;
    }

    @Override
    public void markAsDone() {
        done = true;
    }

    @Override
    public void unmarkAsDone() {
        done = false;
    }

    public boolean isDone() {
        return this.done;
    }

    @Override
    public String toString() {
        return (done ? "[X] " : "[ ] ") + name;
    }

    @Override
    public String toFileStorage() {
        return name + " | " + (done ? "1" : "0");
    }
}
