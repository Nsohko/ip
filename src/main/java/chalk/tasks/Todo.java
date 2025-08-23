package chalk.tasks;

class Todo extends Task {

    public Todo(String taskName) {
        super(taskName);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toFileStorage() {
        return "todo  " + this.getName()
            + super.toFileStorage();
    }
}
