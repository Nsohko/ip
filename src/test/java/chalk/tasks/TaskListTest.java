package chalk.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class TaskListTest {

    @Test
    void newTask_addTask_increasesSize() {
        TaskList list = new TaskList();
        assertEquals(0, list.size());

        list.addTask(new TaskStub("task1"));
        assertEquals(1, list.size());
    }

    @Test
    void markAsDone_markTask_marksCorrectTask() {
        TaskList list = new TaskList();
        TaskStub t1 = new TaskStub("task1");
        list.addTask(t1);

        Task returned = list.markAsDone(1);
        assertTrue(returned.toString().contains("[X]"));
    }

    @Test
    void unmarkAsDone_unmarkTask_unmarksCorrectTask() {
        TaskList list = new TaskList();
        TaskStub t1 = new TaskStub("task1");
        list.addTask(t1);

        list.markAsDone(1);
        Task returned = list.unmarkAsDone(1);
        assertTrue(returned.toString().contains("[ ]"));
    }

    @Test
    void deleteTask_deleteTask_removesCorrectTask() {
        TaskList list = new TaskList();
        list.addTask(new TaskStub("task1"));
        list.addTask(new TaskStub("task2"));

        Task deleted = list.deleteTask(1);

        assertEquals("[ ] task1", deleted.toString());
        assertEquals(1, list.size());
        assertTrue(list.toString().contains("task2"));
    }

    @Test
    void findTask_returnsCorrectTask() {
        TaskList list = new TaskList();
        list.addTask(new TaskStub("book1"));
        list.addTask(new TaskStub("abc"));
        list.addTask(new TaskStub("book2"));

        TaskList filteredList = list.searchTasks("book");

        String expected = """
                1. [ ] book1
                2. [ ] book2
                """;

        assertEquals(expected, filteredList.toString());
        
    }

    @Test
    void markAsDone_invalidTaskNumber_throwsException() {
        TaskList list = new TaskList();
        list.addTask(new TaskStub("task1"));

        IndexOutOfBoundsException ex = assertThrows(IndexOutOfBoundsException.class, () -> list.markAsDone(2));

        assertEquals("There is no task with that number!", ex.getMessage());
    }

    @Test
    void unmarkAsDone_invalidTaskNumber_throwsException() {
        TaskList list = new TaskList();
        list.addTask(new TaskStub("task1"));

        IndexOutOfBoundsException ex = assertThrows(IndexOutOfBoundsException.class, () -> list.unmarkAsDone(-1));

        assertEquals("There is no task with that number!", ex.getMessage());
    }

    @Test
    void deleteTask_invalidTaskNumber_throwsException() {
        TaskList list = new TaskList();
        list.addTask(new TaskStub("task1"));

        IndexOutOfBoundsException ex = assertThrows(IndexOutOfBoundsException.class, () -> list.deleteTask(-1));

        assertEquals("There is no task with that number!", ex.getMessage());
    }

    @Test
    void findTasks_noResult() {
        TaskList list = new TaskList();
        list.addTask(new TaskStub("book1"));
        list.addTask(new TaskStub("abc"));
        list.addTask(new TaskStub("book2"));

        TaskList filteredList = list.searchTasks("test");

        assertEquals(0, filteredList.size());
    }

    @Test
    void toString_normalUse_printsAllTasks() {
        TaskList list = new TaskList();
        list.addTask(new TaskStub("task1"));
        list.addTask(new TaskStub("task2"));
        list.markAsDone(2);

        assertEquals("""
            1. [ ] task1
            2. [X] task2
            """,
                list.toString());
    }

    @Test
    void toFileStorage_printsAllTasks() {
        TaskList list = new TaskList();
        list.addTask(new TaskStub("task1"));
        list.addTask(new TaskStub("task2"));
        list.markAsDone(2);

        assertEquals("""
            task1 | 0
            task2 | 1
            """,
                list.toFileStorage());
    }
}
