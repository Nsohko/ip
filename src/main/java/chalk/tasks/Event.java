package chalk.tasks;

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

        return this.startTime.isBefore(castedOtherTask.endTime) &&
                castedOtherTask.startTime.isBefore(this.endTime);
    }
}
