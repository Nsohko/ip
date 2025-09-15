package chalk.tasks;

import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        // if they are the same task (same name and start/end time), they conflict
        if (super.checkConflict(otherTask)) {
            return true;
        }

        // try casting to Event to check for time conflicts
        if (!(otherTask instanceof Event castedOtherTask)) {
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

        // Remove "event" and following spaces
        String s = inputCommand.substring(5).stripLeading();
        if (s.isEmpty()) {
            throw wrongArgs();
        }

        // Find the first subcommand token (either /from or /to)
        Pattern token = Pattern.compile("\\s+/(from|to)\\s+");
        Matcher m = token.matcher(s);

        String taskName;
        int firstCmdStart;
        if (m.find()) {
            taskName = s.substring(0, m.start()).strip();
            firstCmdStart = m.start();
        } else {
            // No /from or /to present
            throw wrongArgs();
        }
        if (taskName.isEmpty()) {
            throw wrongArgs();
        }

        // Collect all subcommands in appearance order (supports arbitrary order & spacing)
        Map<String, String> args = new LinkedHashMap<>();
        int currStart = m.end();
        String currKey = m.group(1); // "from" or "to"
        while (true) {
            if (m.find()) {
                // text between previous token end and next token start
                String value = s.substring(currStart, m.start()).strip();
                if (!value.isEmpty()) {
                    args.put(currKey, value);
                }
                currKey = m.group(1);
                currStart = m.end();
            } else {
                // last segment to end of string
                String value = s.substring(currStart).strip();
                if (!value.isEmpty()) {
                    args.put(currKey, value);
                }
                break;
            }
        }

        String startTimeString = args.get("from");
        String endTimeString = args.get("to");

        if (startTimeString == null || endTimeString == null
            || startTimeString.isEmpty() || endTimeString.isEmpty()) {
            throw wrongArgs();
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

        if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
            throw new IllegalArgumentException("Event start time must be before end time!");
        }

        return new Event(taskName, startTime, endTime);
    }

    private static IllegalArgumentException wrongArgs() {
        return new IllegalArgumentException("""
                Event task name, start time and end time cannot be empty.
                Usage: event [eventName] /from [startTime] /to [endTime]
                """);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }

        Event otherEvent = (Event) other;
        return super.equals(otherEvent)
                && this.startTime.equals(otherEvent.startTime)
                && this.endTime.equals(otherEvent.endTime);
    }
}
