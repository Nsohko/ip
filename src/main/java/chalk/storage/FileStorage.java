package chalk.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import chalk.tasks.Task;
import chalk.tasks.TaskList;

public class FileStorage {

    /**
     * The path to the storage file
     */
    private final String storagePath;

    /**
     * Constructor for FileStorage object
     *
     * @param storagePath The path to the storage file
     */
    public FileStorage(String storagePath) {
        this.storagePath = storagePath;
    }

    /**
     * Load the TaskList based on the information currently stored in the
     * storage file If the storage file does not exist yet, it will create it
     *
     * @throws IOException If unable to parse the file as Task data
     */
    public TaskList load() throws IOException {
        File storage = new File(this.storagePath);
        if (!storage.exists()) {
            storage.getParentFile().mkdirs();
            storage.createNewFile();
        }

        int taskNumber = 1;
        TaskList taskList = new TaskList();

        try (Scanner s = new Scanner(storage)) {
            while (s.hasNext()) {
                String[] taskInfo = s.nextLine().split(" \\| ");

                if (taskInfo.length != 2) {
                    throw new IOException("Error parsing task data");
                }

                boolean isDone = false;
                if (taskInfo[1].equals("1")) {
                    isDone = true;
                }

                try {
                    Task newTask = Task.fromInputCommand(taskInfo[0]);
                    if (isDone) {
                        newTask.markAsDone();
                    }
                    taskList.addTask(newTask);
                } catch (IllegalArgumentException e) {
                    System.out.println("Unable to read task " + taskNumber + ". Skipping task");
                }
                taskNumber++;
            }
        }
        return taskList;
    }

    /**
     * Adds a new task to the file storage via append
     *
     * @param task The new task to be added to the file storage
     */
    public void addTask(Task task) throws IOException {
        try (FileWriter fw = new FileWriter(this.storagePath, true)) {
            fw.write(task.toFileStorage() + "\n");
        } catch (IOException e) {
            throw new IOException("Failed to write Task information to file!");
        }
    }

    /**
     * Overwrites the file storage with Task data stored in a TaskList
     *
     * @param taskList The TaskList used to overwrite the file storage
     */
    public void overWriteWithTaskList(TaskList taskList) throws IOException {
        try (FileWriter fw = new FileWriter(this.storagePath)) {
            fw.write(taskList.toFileStorage());
        } catch (IOException e) {
            throw new IOException("Failed to update task in Storage!");
        }
    }
}
