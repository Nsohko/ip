package chalk.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import chalk.datetime.DateTime;
import chalk.datetime.DateTimeStub;

class DeadlineTest {

    // ---------- toString() ----------
    @Test
    void toString_incompleteTask_matchesExpected() {
        DateTime dt = new DateTimeStub("06/06/2025 1820", "6 June 2025 1820hrs");
        Deadline d = new Deadline("return book", dt);

        assertEquals("[D][ ] return book (by: 6 June 2025 1820hrs)", d.toString());
    }

    @Test
    void toString_completeTask_matchesExpected() {
        DateTime dt = new DateTimeStub("06/06/2025 1820", "6 June 2025 1820hrs");
        Deadline d = new Deadline("return book", dt);
        d.markAsDone();

        assertEquals("[D][X] return book (by: 6 June 2025 1820hrs)", d.toString());
    }

    // ---------- toFileStorage() ----------
    @Test
    void toFileStorage_incompleteTask_matchesExpected() {
        DateTime dt = new DateTimeStub("10/10/2024 0400", "10 October 2024 0400hrs");
        Deadline d = new Deadline("return book", dt);

        // Deadline.toFileStorage() = "deadline <name> /by <date> " + super.toFileStorage()
        // super.toFileStorage() expected to append " | 0" or " | 1"
        assertEquals("deadline return book /by 10/10/2024 0400 | 0", d.toFileStorage());
    }

    @Test
    void toFileStorage_completeTask_matchesExpected() {
        DateTime dt = new DateTimeStub("10/10/2024 0400", "10 October 2024 0400hrs");
        Deadline d = new Deadline("return book", dt);
        d.markAsDone();

        assertEquals("deadline return book /by 10/10/2024 0400 | 1", d.toFileStorage());
    }

    // ---------- equals() contract ----------
    @Nested
    @DisplayName("equals() contract")
    class EqualsContract {

        @Test
        void equals_reflexive() {
            Deadline d = new Deadline("a", new DateTimeStub("01/01/2025 0000", "1 Jan 2025 0000hrs"));
            assertEquals(d, d);
        }

        @Test
        void equals_symmetric_and_transitive() {
            DateTimeStub dt = new DateTimeStub("01/01/2025 0000", "1 Jan 2025 0000hrs");
            Deadline a = new Deadline("a", dt);
            Deadline b = new Deadline("a", new DateTimeStub("01/01/2025 0000", "1 Jan 2025 0000hrs"));
            Deadline c = new Deadline("a", new DateTimeStub("01/01/2025 0000", "1 Jan 2025 0000hrs"));

            assertEquals(a, b);
            assertEquals(b, c);
            assertEquals(a, c);
        }

        @Test
        void equals_false_whenDifferentName() {
            DateTimeStub dt = new DateTimeStub("01/01/2025 0000", "1 Jan 2025 0000hrs");
            Deadline a = new Deadline("pay bills", dt);
            Deadline b = new Deadline("return book", dt);

            assertNotEquals(a, b);
        }

        @Test
        void equals_false_whenDifferentDoneState() {
            Deadline a = new Deadline("a",
                    new DateTimeStub("01/01/2025 0000", "1 Jan 2025 0000hrs"));
            Deadline b = new Deadline("a",
                    new DateTimeStub("01/01/2025 0000", "1 Jan 2025 0000hrs"));
            b.markAsDone();

            assertNotEquals(a, b);
        }

        @Test
        void equals_false_whenDifferentDeadlineTime() {
            Deadline a = new Deadline("a",
                    new DateTimeStub("01/01/2025 0000", "1 Jan 2025 0000hrs", 1));
            Deadline b = new Deadline("a",
                    new DateTimeStub("02/01/2025 0000", "2 Jan 2025 0000hrs", 2));

            assertNotEquals(a, b);
        }

        @Test
        void equals_nullOrDifferentClass_returnsFalse() {
            Deadline a = new Deadline("a",
                    new DateTimeStub("01/01/2025 0000", "1 Jan 2025 0000hrs"));
            assertNotEquals(a, null);
            assertNotEquals(a, "not a deadline");
        }
    }

    // ---------- fromInputCommand() parsing ----------
    @Nested
    @DisplayName("fromInputCommand parsing")
    class FromInputCommandParsing {

        @Test
        void fromInputCommand_happyPath_minimalSpaces() {
            // Using real DateTime may parse it; we only assert no exception and type/name
            Deadline d = Deadline.fromInputCommand("deadline return book /by 6/6/2025 1820");
            assertEquals("return book", d.getName());
            assertTrue(d instanceof Deadline);
        }

        @Test
        void fromInputCommand_happyPath_extraSpacesAndMixedCase() {
            Deadline d = Deadline.fromInputCommand("deadline   pay bills    /by   01/01/2026 0000");
            assertEquals("pay bills", d.getName());
        }

        @Test
        void fromInputCommand_happyPath_leadingWhitespaceAllowed() {
            Deadline d = Deadline.fromInputCommand("deadline\tstudy math /by 31/12/2025 2359");
            assertEquals("study math", d.getName());
        }

        @Test
        void fromInputCommand_error_missingByDelimiter() {
            IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> Deadline.fromInputCommand("deadline return book by 6/6/2025 1820") // missing "/by"
            );
            String msg = ex.getMessage();
            assertTrue(msg.contains("Usage: deadline [taskName] /by [dueDate]"));
        }

        @Test
        void fromInputCommand_error_emptyTaskName() {
            IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> Deadline.fromInputCommand("deadline   /by 6/6/2025 1820")
            );
            assertTrue(ex.getMessage().contains("cannot be empty"));
        }

        @Test
        void fromInputCommand_error_emptyDueDate() {
            IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> Deadline.fromInputCommand("deadline return book /by   ")
            );
            assertTrue(ex.getMessage().contains("cannot be empty"));
        }

        @Test
        void fromInputCommand_error_badDateFormat() {
            IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> Deadline.fromInputCommand("deadline return book /by 2025-06-06 18:20") // wrong format
            );
            assertTrue(ex.getMessage().contains("dd/mm/yyyy HHmm"));
        }
    }
}
