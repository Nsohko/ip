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
    public static Task fromCommand(String command) throws IllegalArgumentException {

        if (command.startsWith("todo ")) {
            // skip the beginning 5 chars ("todo ")
            String taskName = command.substring(5).trim();

            // trim to ensure it isnt just whitespace
            if (taskName.trim().isEmpty()) {
                throw new IllegalArgumentException("""
                        Todo task name cannot be empty.
                        Usage: todo [taskName]
                        """);
            }

            return new Todo(taskName);

        } else if (command.startsWith("deadline ")) {
            // e.g. "deadline return book /by Sunday"
            // split into an array containing [taskName, deadlineTime]
            String[] parts = command.substring(9).split(" /by ", 2);

            if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                 throw new IllegalArgumentException("""
                        Deadline task name and due date cannot be empty.
                        Usage: deadline [taskName] /by [dueDate]
                        """);
            }

            String taskName = parts[0];
            String deadlineTime = parts[1];
            return new Deadline(taskName, deadlineTime);

        } else if (command.startsWith("event ")) {
            // e.g. "event project meeting /from Mon 2pm /to 4pm"
            // skip beginning 6 chars ("event ")
            String input = command.substring(6);

            String taskName = input.split(" /")[0].trim();

            // index of the /from and /to commands
            int fromIndex = input.indexOf(" /from ");
            int toIndex   = input.indexOf(" /to ");

            if (taskName.trim().isEmpty() || fromIndex == -1 || toIndex == -1) {
                 throw new IllegalArgumentException("""
                        Event task name, start time and end time cannot be empty.
                        Usage: event [eventName] /from [startTime] /to [endTime]
                        """);
            }

            // skip to fromIndex plus 7 chars (" /from "), then split until next subcommand (denoted by a new " /")
            String startTime = input.substring(fromIndex + 7).split(" /")[0].trim();

            // skip to toIndex plus 5 chars (" /to "), then split until next subcommand (denoted by a new " /")
            String endTime = input.substring(toIndex + 5).split(" /")[0].trim();

            if (startTime.strip().isEmpty() || endTime.strip().isEmpty()) {
                throw new IllegalArgumentException("""
                        Event task name, start time and end time cannot be empty.
                        Usage: event [eventName] /from [startTime] /to [endTime]
                        """);
            }
            return new Event(taskName, startTime, endTime);
        }
        throw new IllegalArgumentException("Unknown Command: " + command);
    }
}
