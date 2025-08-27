package chalk.tasks;

import java.util.ArrayList;

import chalk.storage.Storable;

public class TaskList implements Storable {

    /**
     * Array List used to actually store the tasks
     */
    private final ArrayList<Task> taskList;

    /**
     * Constructor for TaskList object
     */
    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    /**
     * Adds a task into the taskList
     *
     * @param t The new task to be added to the task list
     */
    public void addTask(Task t) {
        this.taskList.add(t);
    }

    /**
     * Marks the corresponding task in the taskList as done
     *
     * @param taskNumber The 1-indexed position of the task to be marked as done
     * (i.e. the first task is 1)
     */
    public Task markAsDone(int taskNumber) throws IndexOutOfBoundsException {
        if (taskNumber > this.taskList.size() || taskNumber <= 0) {
            throw new IndexOutOfBoundsException("There is no task with that number!");
        }
        Task task = this.taskList.get(taskNumber - 1);
        task.markAsDone();
        return task;
    }

    /**
     * Unmarks the corresponding task in the taskList
     *
     * @param taskNumber The 1-indexed position of the task to be unmarked (i.e.
     * the first task is 1)
     */
    public Task unmarkAsDone(int taskNumber) throws IndexOutOfBoundsException {
        if (taskNumber > this.taskList.size() || taskNumber <= 0) {
            throw new IndexOutOfBoundsException("There is no task with that number!");
        }
        Task task = this.taskList.get(taskNumber - 1);
        task.unmarkAsDone();
        return task;
    }

    /**
     * Deletes the corresponding task in the taskList
     *
     * @param taskNumber The 1-indexed position of the task to be deleted (i.e.
     * the first task is 1)
     */
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

    /**
     * Searches for tasks whose names contains searchParam
     *
     * @param searchParam The search parameter
     */
    public TaskList searchTasks(String searchParam) {
        TaskList filteredList = new TaskList();

        for (Task t : this.taskList) {
            if (t.getName().contains(searchParam)) {
                filteredList.addTask(t);
            }
        }

        return filteredList;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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
