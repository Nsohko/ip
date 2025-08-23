package tasks;

import datetime.DateTime;

class Deadline extends Task {

    private final DateTime deadlineTime;

    public Deadline(String taskName, DateTime deadlineTime) {
        super(taskName);
        this.deadlineTime = deadlineTime;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString()
                + " (by: " + this.deadlineTime.toString() + ")";
    }

    @Override
    public String toFileStorage() {
        return "deadline " + this.getName() +
            " /by " + this.deadlineTime.toFileStorage() +
            super.toFileStorage() ;
    }
}
