package storage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import tasks.Task;
import tasks.TaskList;

public class FileStorage {

    private static final String PATH_TO_STORAGE = "./data/Storage.csv";
    private final File storage;

    public FileStorage() {
        this.storage = new File(FileStorage.PATH_TO_STORAGE);
    }

    public TaskList initialize() throws IOException {
        
        if (!this.storage.exists()) {
            this.storage.createNewFile();
        }

        int taskNumber = 1;
        TaskList taskList = new TaskList();

        Scanner s = new Scanner(this.storage);
        while (s.hasNext()) {
            String[] taskInfo = s.nextLine().split(" | ");

            if (taskInfo.length != 2) {
                throw new IOException();
            }

            boolean isDone = false;
            if (taskInfo[1].equals("1")) {
                isDone = true;
            }

            try {
                Task newTask = Task.fromCommand(taskInfo[0]);
                if (isDone) {
                    newTask.markAsDone();
                }
                taskList.addTask(newTask);
            } catch (IllegalArgumentException e) {
                System.out.println("Error reading task " + taskNumber + ". Skipping task");
            }
            taskNumber ++;
        }
        s.close();
        return taskList;
    }
}
