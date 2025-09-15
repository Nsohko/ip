package chalk.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TaskTest {

    // ---------- basic state & presentation ----------

    @Test
    void getNamegetIsDone_normalUse_success() {
        Task t = new TaskStub("read book");
        assertEquals("read book", t.getName());
        assertFalse(t.getIsDone());

        t.markAsDone();
        assertTrue(t.getIsDone());

        t.unmarkAsDone();
        assertFalse(t.getIsDone());
    }

    @Test
    void toString_usesBaseFormat() {
        Task t = new TaskStub("read book");
        assertEquals("[ ] read book", t.toString());

        t.markAsDone();
        assertEquals("[X] read book", t.toString());
    }

    @Test
    void toFileStorage_usesBaseFormat() {
        Task t = new TaskStub("read book");
        assertEquals(" | 0", t.toFileStorage());

        t.markAsDone();
        assertEquals(" | 1", t.toFileStorage());
    }

    // ---------- equals (base uses getClass + fields) ----------

    @Test
    void equals_sameClassSameFields_true() {
        Task a = new TaskStub("A");
        Task b = new TaskStub("A");
        assertEquals(a, b);

        a.markAsDone();
        b.markAsDone();
        assertEquals(a, b);
    }

    @Test
    void equals_sameClassDifferentFields_false() {
        Task a = new TaskStub("A");
        Task b = new TaskStub("B"); // different name
        assertNotEquals(a, b);

        Task c = new TaskStub("A");
        c.markAsDone(); // different done state
        assertNotEquals(a, c);
    }

    @Test
    void equals_nullAndOtherType_false() {
        Task a = new TaskStub("A");
        assertNotEquals(a, null);
        assertNotEquals(a, "not a task");
    }

    // ---------- checkConflict (default: equals either way) ----------

    @Test
    void checkConflict_equalTasks_conflictTrue() {
        Task a = new TaskStub("A");
        Task b = new TaskStub("A");
        assertTrue(a.checkConflict(b));
        assertTrue(b.checkConflict(a));
    }

    @Test
    void checkConflict_differentTasks_conflictFalse() {
        Task a = new TaskStub("A");
        Task b = new TaskStub("B");
        assertFalse(a.checkConflict(b));
        assertFalse(b.checkConflict(a));
    }

    @Test
    void checkConflict_handlesAsymmetricEquals_returnsTrueIfEitherSideEquals() {
        // Left says equals(right) == true; Right says equals(left) == false.
        Task left = new WeirdLeftEqualsTaskStub("X");
        Task right = new WeirdRightEqualsTaskStub("Y");

        // Default implementation: this.equals(other) || other.equals(this)
        assertTrue(left.checkConflict(right)); // left.equals(right) is true
        assertTrue(right.checkConflict(left)); // other.equals(this) triggers left.equals(right) == true
    }

    // ---------- fromInputCommand factory ----------

    @Test
    void fromInputCommand_todo_routedToTodo() {
        Task t = Task.fromInputCommand("todo buy milk");
        assertTrue(t instanceof Todo);
        assertEquals("buy milk", t.getName());
    }

    @Test
    void fromInputCommand_deadline_routedToDeadline() {
        // Keep this light: assert type; avoid formatting coupling
        Task t = Task.fromInputCommand("deadline submit /by 1/1/2030 0900");
        assertTrue(t instanceof Deadline);
    }

    @Test
    void fromInputCommand_event_routedToEvent() {
        Task t = Task.fromInputCommand("event meeting /from 1/1/2030 0900 /to 1/1/2030 1000");
        assertTrue(t instanceof Event);
    }

    @Test
    void fromInputCommand_stripsWhitespaceBeforeDispatch() {
        Task t = Task.fromInputCommand("   todo   read  ");
        assertTrue(t instanceof Todo);
        assertEquals("read", t.getName());
    }

    @Test
    void fromInputCommand_unknown_throwsWithInputEchoed() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class, () -> Task.fromInputCommand("remind me later")
        );
        assertTrue(ex.getMessage().startsWith("Unknown Command: "));
        assertTrue(ex.getMessage().contains("remind me later"));
    }
}
