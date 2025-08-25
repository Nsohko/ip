package chalk.tasks;

import chalk.datetime.DateTime;

public class Event extends Task {

    private final DateTime startTime;
    private final DateTime endTime;

    public Event(String taskName, DateTime startTime, DateTime endTime) {
        super(taskName);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + this.startTime.toString()
                + " to: " + this.endTime.toString() + ")";
    }

    @Override
    public String toFileStorage() {
        return "event " + this.getName()
                + " /from " + this.startTime.toFileStorage() + " /to " + this.endTime.toFileStorage()
                + super.toFileStorage();
    }
}
