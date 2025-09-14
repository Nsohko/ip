package chalk.storage;

import java.io.IOException;

import chalk.tasks.Task;
import chalk.tasks.TaskList;

/**
 * Stub FileStorage that works only in memory.
 */
public class FileStorageStub extends FileStorage {
    private TaskList taskList;

    /**
     * Creates a FileStorageStub with the given TaskList.
     *
     * @param taskList TaskList to use in this stub.
     */
    public FileStorageStub(TaskList taskList) {
        super("./ignored.txt");
        this.taskList = taskList;
    }

    @Override
    public TaskList load() throws IOException {
        return taskList;
    }

    @Override
    public void addTask(Task task) throws IOException {
       // no-op for testing
    }

    @Override
    public void overWriteWithTaskList(TaskList taskList) throws IOException {
        this.taskList = taskList;
    }
}
