
import java.util.ArrayList;

class TaskList {

    private final ArrayList<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    public void addTask(Task t) {
        this.taskList.add(t);
    }

    public void addTask(String taskName) {
        Task newTask = new Task(taskName);
        this.addTask(newTask);
    }

    // taskNumber is 1-indexed
    public void markAsDone(int taskNumber) {
        this.taskList.get(taskNumber - 1).markAsDone();
    }

    // taskNumber is 1-indexed
    public void markAsUndone(int taskNumber) {
        this.taskList.get(taskNumber - 1).unmarkAsDone();
    }

    // Override
    public String toString() {
        String res = "";

        Task task;
        for (int i = 0; i < this.taskList.size(); i++) {
            task = this.taskList.get(i);
            res += ((i + 1) + ". " + task.toString() + "\n");
        }

        return res;
    }
}
