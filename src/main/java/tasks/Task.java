package tasks;

public abstract class Task {

    private final String taskName;
    private boolean isDone;

    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void unmarkAsDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return "["
                + (this.isDone ? "X" : " ")
                + "] "
                + this.taskName;
    }

    // factory method to create the appropriate task subtype from a command
    public static Task fromCommand(String command) {

        if (command.startsWith("todo ")) {
            // skip the beginning 5 chars ("todo ")
            String taskName = command.substring(5).trim();
            return new Todo(taskName);

        } else if (command.startsWith("deadline ")) {
            // e.g. "deadline return book /by Sunday"
            // split into an array containing [taskName, deadlineTime]
            String[] parts = command.substring(9).split(" /by ", 2);
            String taskName = parts[0];
            String deadlineTime = parts[1];
            return new Deadline(taskName, deadlineTime);

        } else if (command.startsWith("event ")) {
            // e.g. "event project meeting /from Mon 2pm /to 4pm"
             // split into an array containing [taskName, startTime, endTime]
            String[] parts = command.substring(6).split(" /from | /to ");
            String taskName = parts[0];
            String startTime = parts[1];
            String endTime = parts[2];
            return new Event(taskName, startTime, endTime);
        }
        return null;
    }
}
