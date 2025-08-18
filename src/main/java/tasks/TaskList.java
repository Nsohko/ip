package tasks;

import java.util.ArrayList;

public class TaskList {

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
    public Task markAsDone(int taskNumber) {
        Task task = this.taskList.get(taskNumber - 1);
        task.markAsDone();
        return task;
    }

    // taskNumber is 1-indexed
    public Task unmarkAsDone(int taskNumber) {
        Task task = this.taskList.get(taskNumber - 1);
        task.unmarkAsDone();
        return task;
    }

    public int size() {
        return this.taskList.size();
    }

    @Override
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
