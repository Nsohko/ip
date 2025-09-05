package chalk.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TodoTest {

    // ---------- toString() tests ----------
    @Test
    void toString_incompleteTask_matchExpected() {
        Todo t = new Todo("return book");

        String s = t.toString();

        assertEquals("[T][ ] return book", s);
    }

    @Test
    void toString_completeTask_matchExpected() {
        Todo t = new Todo("return book");
        t.markAsDone();

        String s = t.toString();

        assertEquals("[T][X] return book", s);
    }

    // ---------- toFileStorage() tests ----------
    @Test
    void toFileStorage_incompleteTask_matchExpected() {
        Todo t = new Todo("return book");

        String fs = t.toFileStorage();

        assertEquals("todo return book | 0", fs);
    }

    @Test
    void toFileStorage_completeTask_matchExpected() {
        Todo t = new Todo("return book");
        t.markAsDone();

        String fs = t.toFileStorage();

        assertEquals("todo return book | 1", fs);
    }
}
