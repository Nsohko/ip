package chalk;

import chalk.tasks.Task;

/**
 * Minimal Chalk test double.
 * - Captures tasks passed to addTask(...)
 * - Does not override printError (it's final in your code)
 */
public class ChalkStub extends Chalk {
    public Task lastAdded;
    public int addCount = 0;

    public int deleteCount = 0;
    public int lastDeleted;

    public boolean terminated = false;

    public int searchCount = 0;
    public String[] lastSearchParams;

    public int listCount = 0;

    public int markCount = 0;
    public int lastMarked;

    public int unmarkCount = 0;
    public int lastUnmarked;

    @Override
    public void addTask(Task task) {
        this.lastAdded = task;
        this.addCount++;
    }

    @Override
    public void deleteTask(int taskNumber) {
        this.deleteCount++;
        this.lastDeleted = taskNumber;
    }

    @Override
    public void terminate() {
        this.terminated = true;
    }

    @Override
    public void searchTasks(String[] params) {
        this.searchCount++;
        this.lastSearchParams = params;
        
    }

    @Override
    public void listTasks() {
        this.listCount++;
    }

    @Override
    public void markTaskAsDone(int taskNumber) {
        this.markCount++;
        this.lastMarked = taskNumber;
    }

    @Override
    public void unmarkTaskAsDone(int taskNumber) {
        this.unmarkCount++;
        this.lastUnmarked = taskNumber;
    }
}
