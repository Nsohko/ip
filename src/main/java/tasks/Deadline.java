package tasks;

class Deadline extends Task {

    private final String deadlineTime;

    public Deadline(String taskName, String deadlineTime) {
        super(taskName);
        this.deadlineTime = deadlineTime;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString()
                + " (by: " + this.deadlineTime + ")";
    }

    @Override
    public String toFileStorage() {
        return "deadline " + this.getName() +
            " /by " + this.deadlineTime +
            super.toFileStorage() ;
    }
}
