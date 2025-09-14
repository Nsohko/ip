package chalk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chalk.storage.FileStorageStub;
import chalk.tasks.TaskList;
import chalk.tasks.TaskStub;
import chalk.ui.TextUiStub;

/**
 * Basic JUnit tests for Chalk, using simple stubs.
 */
public class ChalkTest {

    private Chalk chalk;
    private TextUiStub textUI;
    private TaskList taskList;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        // Prepare empty TaskList and stubs
        taskList = new TaskList();
        textUI = new TextUiStub();
        FileStorageStub storage = new FileStorageStub(taskList);

        // Build Chalk manually with stubs
        chalk = new Chalk(textUI, storage, taskList);
    }

    @Test
    void testAddTask_success() {
        TaskStub t = new TaskStub("read book");
        chalk.addTask(t);

        assertEquals(1, taskList.size());
        assertTrue(textUI.replies.stream().anyMatch(s -> s.contains("read book")));
    }

    @Test
    void testMarkTaskAsDone_success() {
        TaskStub t = new TaskStub("do homework");
        chalk.addTask(t);

        chalk.markTaskAsDone(1);

        assertTrue(t.isDone());
        assertTrue(textUI.replies.stream().anyMatch(s -> s.contains("marked this task as done")));
    }

    @Test
    void testUnmarkTaskAsDone_success() {
        TaskStub t = new TaskStub("exercise");
        chalk.addTask(t);
        chalk.markTaskAsDone(1);

        chalk.unmarkTaskAsDone(1);

        assertFalse(t.isDone());
        assertTrue(textUI.replies.stream().anyMatch(s -> s.contains("not done yet")));
    }

    @Test
    void testDeleteTask_success() {
        TaskStub t = new TaskStub("sleep");
        chalk.addTask(t);

        chalk.deleteTask(1);

        assertEquals(0, taskList.size());
        assertTrue(textUI.replies.stream().anyMatch(s -> s.contains("removed this task")));
    }

    @Test
    void testSearchTasks_noMatches() {
        chalk.searchTasks("nothing");

        assertTrue(textUI.replies.stream().anyMatch(s -> s.contains("No tasks found")));
    }
}
