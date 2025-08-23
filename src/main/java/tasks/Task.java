package tasks;

import datetime.DateTime;
import java.time.format.DateTimeParseException;

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

    public String getName() {
        return this.taskName;
    }

    @Override
    public String toString() {
        return "["
                + (this.isDone ? "X" : " ")
                + "] "
                + this.taskName;
    }


    public String toFileStorage() {
        String completionStatus = this.isDone ? "1" : "0";
        return " | " + completionStatus;
    }

    // factory method to create the appropriate task subtype from a command
    public static Task fromCommand(String command) throws IllegalArgumentException {

        if (command.startsWith("todo ")) {
            // skip the beginning 5 chars ("todo ")
            String taskName = command.substring(5).trim();

            // trim to ensure it isnt just whitespace
            if (taskName.trim().isEmpty()) {
                throw new IllegalArgumentException("""
                        Error! Todo task name cannot be empty.
                        Usage: todo [taskName]
                        """);
            }

            return new Todo(taskName);

        } else if (command.startsWith("deadline ")) {
            // split into an array containing [taskName, deadlineTime]
            String[] parts = command.substring(9).split(" /by ", 2);

            if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                 throw new IllegalArgumentException("""
                        Error! Deadline task name and due date cannot be empty.
                        Usage: deadline [taskName] /by [dueDate]
                        """);
            }

            String taskName = parts[0];

            DateTime deadlineTime;
            try {
                deadlineTime = new DateTime(parts[1]);
            } catch (DateTimeParseException e) {
                  throw new IllegalArgumentException("""
                        Error! Provide deadline due date in the following format:
                        dd/mm/yyyy HHmm (e.g. 31/10/2025 1800 for 31 October 2025, 6pm)
                        """);
            }
            return new Deadline(taskName, deadlineTime);

        } else if (command.startsWith("event ")) {
            // skip beginning 6 chars ("event ")
            String input = command.substring(6);

            String taskName = input.split(" /")[0].trim();

            // index of the /from and /to commands
            int fromIndex = input.indexOf(" /from ");
            int toIndex   = input.indexOf(" /to ");

            if (taskName.trim().isEmpty() || fromIndex == -1 || toIndex == -1) {
                 throw new IllegalArgumentException("""
                        Error! Event task name, start time and end time cannot be empty.
                        Usage: event [eventName] /from [startTime] /to [endTime]
                        """);
            }

            // skip to fromIndex plus 7 chars (" /from "), then split until next subcommand (denoted by a new " /")
            String startTimeString = input.substring(fromIndex + 7).split(" /")[0].trim();

            // skip to toIndex plus 5 chars (" /to "), then split until next subcommand (denoted by a new " /")
            String endTimeString = input.substring(toIndex + 5).split(" /")[0].trim();

            if (startTimeString.trim().isEmpty() || endTimeString.trim().isEmpty()) {
                throw new IllegalArgumentException("""
                        Error! Event task name, start time and end time cannot be empty.
                        Usage: event [eventName] /from [startTime] /to [endTime]
                        """);
            }
            
            DateTime startTime;
            DateTime endTime;
            try {
                startTime = new DateTime(startTimeString);
                endTime = new DateTime(endTimeString);
                
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("""
                    Error! Provide event start and end time in the following format:
                    dd/mm/yyyy HHmm (e.g. 31/10/2025 1800 for 31 October 2025, 6pm)
                    """);
            }
            
            return new Event(taskName, startTime, endTime);
        }
        throw new IllegalArgumentException("Error! Unknown Command: " + command);
    }
}
