package tasks;

class Event extends Task {

    private final String startTime;
    private final String endTime;

    public Event(String taskName, String startTime, String endTime) {
        super(taskName);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + this.startTime
                + " to: " + this.endTime + ")";
    }
}
