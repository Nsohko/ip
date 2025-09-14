package chalk.tasks;

import java.time.format.DateTimeParseException;

import chalk.datetime.DateTime;

/**
 * The Event class represents an Event task in Chalk.
 * An Event has a name, a start time and an end time.
 */
public class Event extends Task {

    /**
     * The start time of this Event
     */
    private final DateTime startTime;

    /**
     * The end time of this event
     */
    private final DateTime endTime;

    /**
     * Constructor for Event object
     *
     * @param taskName The name of the Event
     * @param startTime The start time of this Event
     * @param endTime The end time of this event
     */
    public Event(String taskName, DateTime startTime, DateTime endTime) {
        super(taskName);
        this.startTime = startTime;
        this.endTime = endTime;

        assert this.startTime != null;
        assert this.endTime != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + this.startTime.toString()
                + " to: " + this.endTime.toString() + ")";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toFileStorage() {
        return "event " + this.getName()
                + " /from " + this.startTime.toFileStorage() + " /to " + this.endTime.toFileStorage()
                + super.toFileStorage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkConflict(Task otherTask) {
        if (!(otherTask instanceof Event castedOtherTask)) {
            // Currently, events can only conflict with other events
            return false;
        }

        return this.startTime.isBefore(castedOtherTask.endTime)
            && castedOtherTask.startTime.isBefore(this.endTime);
    }

    /**
     * Creates an Event task from an input command string
     *
     * @param inputCommand The input command string
     * @return An Event task created from the input command string
     * @throws IllegalArgumentException If the input command string is invalid
     */
    public static Event fromInputCommand(String inputCommand) throws IllegalArgumentException {
        // skip beginning 6 chars ("event ")
        String input = inputCommand.substring(6);

        String taskName = input.split(" /")[0].trim();

        // index of the /from and /to commands
        int fromIndex = input.indexOf(" /from ");
        int toIndex = input.indexOf(" /to ");

        if (taskName.trim().isEmpty() || fromIndex == -1 || toIndex == -1) {
            throw new IllegalArgumentException("""
                    Event task name, start time and end time cannot be empty.
                    Usage: event [eventName] /from [startTime] /to [endTime]
                    """);
        }

        // skip to fromIndex plus 7 chars (" /from "), then split until next subcommand (denoted by a new " /")
        String startTimeString = input.substring(fromIndex + 7).split(" /")[0].trim();

        // skip to toIndex plus 5 chars (" /to "), then split until next subcommand (denoted by a new " /")
        String endTimeString = input.substring(toIndex + 5).split(" /")[0].trim();

        if (startTimeString.trim().isEmpty() || endTimeString.trim().isEmpty()) {
            throw new IllegalArgumentException("""
                    Event task name, start time and end time cannot be empty.
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
                Provide event start and end time in the following format:
                dd/mm/yyyy HHmm (e.g. 31/10/2025 1800 for 31 October 2025, 6pm)
                """);
        }
        return new Event(taskName, startTime, endTime);
    }
}
