package chalk.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

import chalk.storage.Storable;

/**
 * The TaskList class is a wrapper around an ArrayList of Tasks, and provides
 * methods to manipulate the list of tasks.
 */
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
     *     (i.e. the first task is 1)
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
     * @param taskNumber The 1-indexed position of the task to be unmarked
     *     (i.e. the first task is 1)
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
     * @param taskNumber The 1-indexed position of the task to be deleted
     *     (i.e. the first task is 1)
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
     * Searches for tasks whose names contains any string from searchParams
     *
     * @param searchParams The search parameters
     */
    public TaskList searchTasks(String... searchParams) {
        TaskList filteredList = new TaskList();

        this.taskList.stream()
            .filter(t -> Arrays.stream(searchParams)
                    .anyMatch(s -> t.getName().contains(s)))
            .forEach(filteredList::addTask);

        return filteredList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        return IntStream.range(0, this.taskList.size())
            .mapToObj(i -> (i + 1) + ". " + this.taskList.get(i).toString() + "\n")
            .reduce("", (a, b) -> a + b);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toFileStorage() {
        return this.taskList.stream()
                .map(t -> t.toFileStorage() + "\n")
                .reduce("", (a, b) -> a + b);
    }

    /**
     * Checks through taskList to find if any event that conflicts with newTask (or vice versa)
     * using their own checkConflict methods
     *
     * @return An optional containing the first conflicting task (if it exists)
     *     If there is no conflicting task, an empty optional is returned
     */
    public Optional<Task> checkConflict(Task newTask) {
        return this.taskList.stream()
                .filter(t -> t.checkConflict(newTask) || newTask.checkConflict(t))
                .findFirst();
    }
}
