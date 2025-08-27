package chalk.tasks;

import java.util.ArrayList;

import chalk.storage.Storable;

public class TaskList implements Storable {

    private final ArrayList<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    public void addTask(Task t) {
        this.taskList.add(t);
    }

    // taskNumber is 1-indexed
    public Task markAsDone(int taskNumber) throws IndexOutOfBoundsException {
        if (taskNumber > this.taskList.size() || taskNumber <= 0) {
            throw new IndexOutOfBoundsException("There is no task with that number!");
        }
        Task task = this.taskList.get(taskNumber - 1);
        task.markAsDone();
        return task;
    }

    // taskNumber is 1-indexed
    public Task unmarkAsDone(int taskNumber) throws IndexOutOfBoundsException {
        if (taskNumber > this.taskList.size() || taskNumber <= 0) {
            throw new IndexOutOfBoundsException("There is no task with that number!");
        }
        Task task = this.taskList.get(taskNumber - 1);
        task.unmarkAsDone();
        return task;
    }

    // taskNumber is 1-indexed
    public Task deleteTask(int taskNumber) throws IndexOutOfBoundsException {
        if (taskNumber > this.taskList.size() || taskNumber <= 0) {
            throw new IndexOutOfBoundsException("There is no task with that number!");
        }
        Task task = this.taskList.get(taskNumber - 1);
        this.taskList.remove(taskNumber - 1);
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

    @Override
    public String toFileStorage() {
        String res = "";

        Task task;
        for (int i = 0; i < this.taskList.size(); i++) {
            task = this.taskList.get(i);
            res += (task.toFileStorage() + "\n");
        }

        return res;
    }
}
