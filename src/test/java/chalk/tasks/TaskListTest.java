package chalk.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

/**
 * Tests for TaskList using simple Task stubs.
 */
class TaskListTest {

    // ---------- basic list ops ----------

    @Test
    void addTask_increasesSize() {
        TaskList list = new TaskList();
        assertEquals(0, list.size());

        list.addTask(new TaskStub("task1"));
        assertEquals(1, list.size());
    }

    @Test
    void markAsDone_validIndex_marksAndReturnsTask() {
        TaskList list = new TaskList();
        TaskStub t1 = new TaskStub("task1");
        list.addTask(t1);

        Task returned = list.markAsDone(1);
        assertSame(t1, returned);
        assertTrue(returned.toString().contains("[X]"));
    }

    @Test
    void unmarkAsDone_validIndex_unmarksAndReturnsTask() {
        TaskList list = new TaskList();
        TaskStub t1 = new TaskStub("task1");
        list.addTask(t1);

        list.markAsDone(1);
        Task returned = list.unmarkAsDone(1);
        assertSame(t1, returned);
        assertTrue(returned.toString().contains("[ ]"));
    }

    @Test
    void deleteTask_validIndex_removesAndReturnsTask() {
        TaskList list = new TaskList();
        Task t1 = new TaskStub("task1");
        Task t2 = new TaskStub("task2");
        list.addTask(t1);
        list.addTask(t2);

        Task deleted = list.deleteTask(1);
        assertSame(t1, deleted);
        assertEquals(1, list.size());
        assertTrue(list.toString().contains("task2"));
    }

    // ---------- invalid indices ----------

    @Test
    void markAsDone_invalidIndex_throws() {
        TaskList list = new TaskList();
        list.addTask(new TaskStub("task1"));

        IndexOutOfBoundsException ex =
                assertThrows(IndexOutOfBoundsException.class, () -> list.markAsDone(2));
        assertEquals("There is no task with that number!", ex.getMessage());
    }

    @Test
    void unmarkAsDone_invalidIndex_throws() {
        TaskList list = new TaskList();
        list.addTask(new TaskStub("task1"));

        IndexOutOfBoundsException ex =
                assertThrows(IndexOutOfBoundsException.class, () -> list.unmarkAsDone(-1));
        assertEquals("There is no task with that number!", ex.getMessage());
    }

    @Test
    void deleteTask_invalidIndex_throws() {
        TaskList list = new TaskList();
        list.addTask(new TaskStub("task1"));

        IndexOutOfBoundsException ex =
                assertThrows(IndexOutOfBoundsException.class, () -> list.deleteTask(0));
        assertEquals("There is no task with that number!", ex.getMessage());
    }

    // ---------- search (varargs) ----------

    @Test
    void searchTasks_singleParam_matchesContains() {
        TaskList list = new TaskList();
        list.addTask(new TaskStub("book1"));
        list.addTask(new TaskStub("abc"));
        list.addTask(new TaskStub("book2"));

        TaskList filtered = list.searchTasks("book");

        assertEquals("""
                1. [ ] book1
                2. [ ] book2
                """, filtered.toString());
    }

    @Test
    void searchTasks_multipleParams_matchesAny() {
        TaskList list = new TaskList();
        list.addTask(new TaskStub("alpha"));
        list.addTask(new TaskStub("beta"));
        list.addTask(new TaskStub("gamma"));

        TaskList filtered = list.searchTasks("ph", "et"); // "alpha" & "beta"

        assertEquals("""
                1. [ ] alpha
                2. [ ] beta
                """, filtered.toString());
    }

    @Test
    void searchTasks_noParams_returnsEmptyList() {
        TaskList list = new TaskList();
        list.addTask(new TaskStub("foo"));
        list.addTask(new TaskStub("bar"));

        TaskList filtered = list.searchTasks(); // Arrays.stream(empty) => no matches
        assertEquals(0, filtered.size());
        assertEquals("", filtered.toString());
    }

    @Test
    void searchTasks_noMatches_returnsEmptyList() {
        TaskList list = new TaskList();
        list.addTask(new TaskStub("book1"));
        list.addTask(new TaskStub("abc"));
        list.addTask(new TaskStub("book2"));

        TaskList filtered = list.searchTasks("xyz");
        assertEquals(0, filtered.size());
    }

    // ---------- printing ----------

    @Test
    void toString_listsAllWithIndicesAndNewlines() {
        TaskList list = new TaskList();
        list.addTask(new TaskStub("task1"));
        list.addTask(new TaskStub("task2"));
        list.markAsDone(2);

        assertEquals("""
                1. [ ] task1
                2. [X] task2
                """, list.toString());
    }

    @Test
    void toFileStorage_printsAllLinesWithTrailingNewlines() {
        TaskList list = new TaskList();
        list.addTask(new TaskStub("task1"));
        list.addTask(new TaskStub("task2"));
        list.markAsDone(2);

        assertEquals("""
                 | 0
                 | 1
                """, list.toFileStorage());
    }

    // ---------- conflict checking ----------

    @Test
    void checkConflict_existingTaskConflictsWithNew_returnsExisting() {
        TaskList list = new TaskList();
        Task c1 = new ConflictTaskStub("A", "new"); // conflicts with any task named "new"
        Task c2 = new TaskStub("B");
        list.addTask(c1);
        list.addTask(c2);

        Task newTask = new TaskStub("new"); // existing c1.checkConflict(newTask) => true
        Optional<Task> result = list.checkConflict(newTask);

        assertTrue(result.isPresent());
        assertSame(c1, result.get());
    }

    @Test
    void checkConflict_newTaskConflictsWithExisting_returnsExisting() {
        TaskList list = new TaskList();
        Task existing = new TaskStub("old");
        list.addTask(existing);

        // existing.checkConflict(new) returns false, but new.checkConflict(existing) returns true
        Task newTask = new ConflictTaskStub("new", "old");

        Optional<Task> result = list.checkConflict(newTask);
        assertTrue(result.isPresent());
        assertSame(existing, result.get());
    }

    @Test
    void checkConflict_multipleMatches_returnsFirstInList() {
        TaskList list = new TaskList();
        Task first = new ConflictTaskStub("first", "hit");
        Task second = new ConflictTaskStub("second", "hit");
        list.addTask(first);
        list.addTask(second);

        Task newTask = new TaskStub("hit");
        Optional<Task> result = list.checkConflict(newTask);

        assertTrue(result.isPresent());
        assertSame(first, result.get()); // should return the first conflicting task
    }

    @Test
    void checkConflict_noneFound_returnsEmpty() {
        TaskList list = new TaskList();
        list.addTask(new TaskStub("a"));
        list.addTask(new TaskStub("b"));

        Optional<Task> result = list.checkConflict(new TaskStub("c"));
        assertTrue(result.isEmpty());
    }
}
