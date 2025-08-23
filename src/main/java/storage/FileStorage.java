package storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import tasks.Task;
import tasks.TaskList;

public class FileStorage {

    private static final String PATH_TO_STORAGE = "./ChalkData/Storage.txt";

    public TaskList initialize() throws IOException {
        File storage = new File(FileStorage.PATH_TO_STORAGE);
        if (!storage.exists()) {
            storage.getParentFile().mkdirs();
            storage.createNewFile();
        }

        int taskNumber = 1;
        TaskList taskList = new TaskList();

        Scanner s = new Scanner(storage);
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

    public void addTask(String command) throws IOException{
        try {
            FileWriter fw = new FileWriter(FileStorage.PATH_TO_STORAGE, true); // create a FileWriter in append mode
            fw.write(command +  " | " + "0");
            fw.close();   
        } catch (IOException e) {
            throw new IOException("Failed to write Task Info to file");
        }
    }

    public void overWriteWithTaskList(TaskList taskList) throws IOException{
        FileWriter fw = new FileWriter(FileStorage.PATH_TO_STORAGE);
        fw.write(taskList.toFileStorage());
        fw.close();

    }
}
