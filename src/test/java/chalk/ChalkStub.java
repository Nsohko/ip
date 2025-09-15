package chalk;

import chalk.tasks.Task;

/**
 * Minimal Chalk test double.
 * - Captures tasks passed to addTask(...)
 * - Does not override printError (it's final in your code)
 */
public class ChalkStub extends Chalk {
    private Task lastAdded;
    private int addCount = 0;

    private int deleteCount = 0;
    private int lastDeleted;

    private boolean terminated = false;

    private int searchCount = 0;
    private String[] lastSearchParams;

    private int listCount = 0;

    private int markCount = 0;
    private int lastMarked;

    private int unmarkCount = 0;
    private int lastUnmarked;

    @Override
    public void addTask(Task task) {
        this.lastAdded = task;
        this.addCount++;
    }

    public int getAddCount() {
        return this.addCount;
    }

    public Task getLastAdded() {
        return this.lastAdded;
    }

    @Override
    public void deleteTask(int taskNumber) {
        this.deleteCount++;
        this.lastDeleted = taskNumber;
    }

    public int getLastDeleted() {
        return this.lastDeleted;
    }

    public int getDeleteCount() {
        return this.deleteCount;
    }

    @Override
    public void terminate() {
        this.terminated = true;
    }

    public boolean isTerminated() {
        return this.terminated;
    }

    @Override
    public void searchTasks(String[] params) {
        this.searchCount++;
        this.lastSearchParams = params;
    }

    public int getSearchCount() {
        return this.searchCount;
    }

    public String[] getLastSearchParams() {
        return this.lastSearchParams;
    }

    @Override
    public void listTasks() {
        this.listCount++;
    }

    public int getListCount() {
        return this.listCount;
    }

    @Override
    public void markTaskAsDone(int taskNumber) {
        this.markCount++;
        this.lastMarked = taskNumber;
    }

    public int getMarkCount() {
        return this.markCount;
    }

    public int getLastMarked() {
        return this.lastMarked;
    }

    @Override
    public void unmarkTaskAsDone(int taskNumber) {
        this.unmarkCount++;
        this.lastUnmarked = taskNumber;
    }

    public int getUnmarkCount() {
        return this.unmarkCount;
    }

    public int getLastUnmarked() {
        return this.lastUnmarked;
    }
}
