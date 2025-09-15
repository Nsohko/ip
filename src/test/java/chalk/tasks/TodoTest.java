package chalk.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class TodoTest {

    // ---------- toString() ----------
    @Test
    void toString_incompleteTask_matchExpected() {
        Todo t = new Todo("return book");
        assertEquals("[T][ ] return book", t.toString());
    }

    @Test
    void toString_completeTask_matchExpected() {
        Todo t = new Todo("return book");
        t.markAsDone();
        assertEquals("[T][X] return book", t.toString());
    }

    // ---------- toFileStorage() ----------
    @Test
    void toFileStorage_incompleteTask_matchExpected() {
        Todo t = new Todo("return book");
        assertEquals("todo return book | 0", t.toFileStorage());
    }

    @Test
    void toFileStorage_completeTask_matchExpected() {
        Todo t = new Todo("return book");
        t.markAsDone();
        assertEquals("todo return book | 1", t.toFileStorage());
    }

    // ---------- fromInputCommand() ----------
    @Test
    void fromInputCommand_validInput_createsTodo() {
        Todo t = Todo.fromInputCommand("todo return book");
        assertEquals("[T][ ] return book", t.toString());
    }

    @Test
    void fromInputCommand_inputWithExtraSpaces_createsTodo() {
        Todo t = Todo.fromInputCommand("todo     return book  ");
        assertEquals("return book", t.getName());
    }

    @Test
    void fromInputCommand_emptyName_throwsException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> Todo.fromInputCommand("todo   ")
        );
        assertTrue(ex.getMessage().contains("Todo task name cannot be empty"));
    }

    // ---------- equals() ----------
    @Test
    void equals_sameContentTodos_areEqual() {
        Todo t1 = new Todo("return book");
        Todo t2 = new Todo("return book");
        assertEquals(t1, t2);
    }

    @Test
    void equals_differentContentTodos_notEqual() {
        Todo t1 = new Todo("return book");
        Todo t2 = new Todo("return pen");
        assertNotEquals(t1, t2);
    }

    @Test
    void equals_otherType_notEqual() {
        Todo t1 = new Todo("return book");
        assertNotEquals(t1, "not a todo");
    }

    @Test
    void equals_null_notEqual() {
        Todo t1 = new Todo("return book");
        assertNotEquals(t1, null);
    }
}
