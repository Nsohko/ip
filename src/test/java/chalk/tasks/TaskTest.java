package chalk.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TaskTest {

    // --- Factory: todo ---
    @Test
    void fromInputCommand_todo_success() {
        Task t = Task.fromInputCommand("todo buy milk");
        assertTrue(t instanceof Todo);
        assertEquals("buy milk", t.getName());
    }

    @Test
    void fromInputCommand_todoEmptyName_throws() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()
                -> Task.fromInputCommand("todo   "));

        assertEquals("""
                    Todo task name cannot be empty.
                    Usage: todo [taskName]
                    """,
                ex.getMessage());
    }

    // --- Factory: deadline ---
    @Test
    void fromInputCommand_deadline_success() {
        Task t = Task.fromInputCommand("deadline return book /by 6/6/2025 1820");
        assertTrue(t instanceof Deadline);
        assertEquals("return book", t.getName());
        // We don't assert the subtype's toString formatting here to keep it simple.
    }

    @Test
    void fromInputCommand_deadlineMissingParts_throws() {
        IllegalArgumentException ex;

        String expectedError = """
                            Deadline task name and due date cannot be empty.
                            Usage: deadline [taskName] /by [dueDate]
                            """;

        ex = assertThrows(IllegalArgumentException.class, ()
                -> Task.fromInputCommand("deadline finish hw"));
        assertEquals(expectedError, ex.getMessage());

        ex = assertThrows(IllegalArgumentException.class, ()
                -> Task.fromInputCommand("deadline  /by 6/6/2025 1820"));
        assertEquals(expectedError, ex.getMessage());

        ex = assertThrows(IllegalArgumentException.class, ()
                -> Task.fromInputCommand("deadline finish hw /by   "));
        assertEquals(expectedError, ex.getMessage());
    }

    @Test
    void fromInputCommand_deadlineBadDate_throws() {

        IllegalArgumentException ex;

        String expectedError = """
                        Provide deadline due date in the following format:
                        dd/mm/yyyy HHmm (e.g. 31/10/2025 1800 for 31 October 2025, 6pm)
                        """;

        // wrong format (colons) or impossible date
        ex = assertThrows(IllegalArgumentException.class, ()
                -> Task.fromInputCommand("deadline a /by abcs"));
        assertEquals(expectedError, ex.getMessage());

        ex = assertThrows(IllegalArgumentException.class, ()
                -> Task.fromInputCommand("deadline a /by 1233"));
        assertEquals(expectedError, ex.getMessage());
    }

    // --- Factory: event ---
    @Test
    void fromInputCommand_event_success() {
        Task t = Task.fromInputCommand(
                "event project meeting /from 1/1/2025 0900 /to 1/1/2025 1000");
        assertTrue(t instanceof Event);
        assertEquals("project meeting", t.getName());
    }

    @Test
    void fromInputCommand_eventFlippedToFrom_success() {
        Task t = Task.fromInputCommand(
                "event project meeting /to 20/11/2026 1800 /from 1/1/2025 0900");
        assertTrue(t instanceof Event);
        assertEquals("project meeting", t.getName());
    }

    @Test
    void fromInputCommand_eventMissingParts_throws() {
        IllegalArgumentException ex;

        String expectedError = """
                        Event task name, start time and end time cannot be empty.
                        Usage: event [eventName] /from [startTime] /to [endTime]
                        """;

        // Missing /from and /to or empty segments
        ex = assertThrows(IllegalArgumentException.class, ()
                -> Task.fromInputCommand("event meeting"));
        assertEquals(expectedError, ex.getMessage());

        ex = assertThrows(IllegalArgumentException.class, ()
                -> Task.fromInputCommand("event  /from 1/1/2025 0900 /to 1/1/2025 1000"));
        assertEquals(expectedError, ex.getMessage());

        ex = assertThrows(IllegalArgumentException.class, ()
                -> Task.fromInputCommand("event meeting /from   /to 1/1/2025 1000"));
        assertEquals(expectedError, ex.getMessage());

        ex = assertThrows(IllegalArgumentException.class, ()
                -> Task.fromInputCommand("event meeting /from 1/1/2025 0900 /to   "));
        assertEquals(expectedError, ex.getMessage());
    }

    @Test
    void fromInputCommand_eventBadDates_throws() {
        IllegalArgumentException ex;

        String expectedError = """
                        Provide event start and end time in the following format:
                        dd/mm/yyyy HHmm (e.g. 31/10/2025 1800 for 31 October 2025, 6pm)
                        """;

        ex = assertThrows(IllegalArgumentException.class, ()
                -> Task.fromInputCommand("event x /from 2025-01-01 0900 /to 1/1/2025 1000"));
        assertEquals(expectedError, ex.getMessage());

        ex = assertThrows(IllegalArgumentException.class, ()
                -> Task.fromInputCommand("event x /from 1/1/2025 0900 /to 2025-01-01 1000"));
        assertEquals(expectedError, ex.getMessage());

        assertEquals(expectedError, ex.getMessage());
    }

    // --- Factory: unknown ---
    @Test
    void fromInputCommand_unknownCommand_throws() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()
                -> Task.fromInputCommand("remind me to sleep"));
        assertTrue(ex.getMessage().contains("Unknown Command"));
    }
}
