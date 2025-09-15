package chalk.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import chalk.datetime.DateTimeStub;

class EventTest {

    // ---------- toString() ----------
    @Test
    void toString_incomplete_matchesExpected() {
        DateTimeStub from = new DateTimeStub("01/01/2026 0900", "1 Jan 2026 0900hrs", 10);
        DateTimeStub to = new DateTimeStub("01/01/2026 1000", "1 Jan 2026 1000hrs", 20);
        Event e = new Event("project meeting", from, to);

        assertEquals("[E][ ] project meeting (from: 1 Jan 2026 0900hrs to: 1 Jan 2026 1000hrs)", e.toString());
    }

    @Test
    void toString_complete_matchesExpected() {
        DateTimeStub from = new DateTimeStub("01/01/2026 0900", "1 Jan 2026 0900hrs", 10);
        DateTimeStub to = new DateTimeStub("01/01/2026 1000", "1 Jan 2026 1000hrs", 20);
        Event e = new Event("project meeting", from, to);
        e.markAsDone();

        assertEquals("[E][X] project meeting (from: 1 Jan 2026 0900hrs to: 1 Jan 2026 1000hrs)", e.toString());
    }

    // ---------- toFileStorage() ----------
    @Test
    void toFileStorage_incomplete_matchesExpected() {
        DateTimeStub from = new DateTimeStub("31/12/2025 2350", "31 Dec 2025 2350hrs", 10);
        DateTimeStub to = new DateTimeStub("31/12/2025 2359", "31 Dec 2025 2359hrs", 20);
        Event e = new Event("NYE", from, to);

        assertEquals("event NYE /from 31/12/2025 2350 /to 31/12/2025 2359 | 0", e.toFileStorage());
    }

    @Test
    void toFileStorage_complete_matchesExpected() {
        DateTimeStub from = new DateTimeStub("31/12/2025 2350", "31 Dec 2025 2350hrs", 10);
        DateTimeStub to = new DateTimeStub("31/12/2025 2359", "31 Dec 2025 2359hrs", 20);
        Event e = new Event("NYE", from, to);
        e.markAsDone();

        assertEquals("event NYE /from 31/12/2025 2350 /to 31/12/2025 2359 | 1", e.toFileStorage());
    }

    // ---------- equals() contract ----------
    @Nested
    @DisplayName("equals() contract")
    class EqualsContract {

        @Test
        void reflexive_sym_transitive() {
            Event a = new Event("meet", new DateTimeStub("01/01/2025 0900", "1 Jan 2025 0900hrs", 1),
                new DateTimeStub("01/01/2025 1000", "1 Jan 2025 1000hrs", 2));
            Event b = new Event("meet", new DateTimeStub("01/01/2025 0900", "1 Jan 2025 0900hrs", 1),
                new DateTimeStub("01/01/2025 1000", "1 Jan 2025 1000hrs", 2));
            Event c = new Event("meet", new DateTimeStub("01/01/2025 0900", "1 Jan 2025 0900hrs", 1),
                new DateTimeStub("01/01/2025 1000", "1 Jan 2025 1000hrs", 2));

            assertEquals(a, a);
            assertEquals(a, b);
            assertEquals(b, c);
            assertEquals(a, c);
        }

        @Test
        void notEqual_whenDifferentName() {
            Event a = new Event("meet", new DateTimeStub("01/01/2025 0900", "x", 1),
                new DateTimeStub("01/01/2025 1000", "y", 2));
            Event b = new Event("sync", new DateTimeStub("01/01/2025 0900", "x", 1),
                new DateTimeStub("01/01/2025 1000", "y", 2));
            assertNotEquals(a, b);
        }

        @Test
        void notEqual_whenDifferentStartTime() {
            Event a = new Event("meet", new DateTimeStub("01/01/2025 0900", "x", 1),
                new DateTimeStub("01/01/2025 1000", "y", 2));
            Event b = new Event("meet", new DateTimeStub("01/01/2025 0800", "x2", 9),
                new DateTimeStub("01/01/2025 1000", "y", 2));
            assertNotEquals(a, b);
        }

        @Test
        void notEqual_whenDifferentEndTime() {
            Event a = new Event("meet", new DateTimeStub("01/01/2025 0900", "x", 1),
                new DateTimeStub("01/01/2025 1000", "y", 2));
            Event b = new Event("meet", new DateTimeStub("01/01/2025 0900", "x", 1),
                new DateTimeStub("01/01/2025 1030", "y2", 3));
            assertNotEquals(a, b);
        }

        @Test
        void notEqual_whenDifferentDoneState() {
            Event a = new Event("meet", new DateTimeStub("s", "p", 1), new DateTimeStub("s2", "p2", 2));
            Event b = new Event("meet", new DateTimeStub("s", "p", 1), new DateTimeStub("s2", "p2", 2));
            b.markAsDone();
            assertNotEquals(a, b);
        }

        @Test
        void nullAndDifferentClass() {
            Event a = new Event("meet", new DateTimeStub("s", "p", 1), new DateTimeStub("s2", "p2", 2));
            assertNotEquals(a, null);
            assertNotEquals(a, "not-event");
        }
    }

    // ---------- checkConflict() ----------
    @Nested
    @DisplayName("checkConflict() interval overlaps")
    class ConflictChecks {
        @Test
        void noConflict_whenThisBeforeOther_nonOverlapping() {
            Event a = new Event("A", new DateTimeStub("", "", 1), new DateTimeStub("", "", 2));
            Event b = new Event("B", new DateTimeStub("", "", 3), new DateTimeStub("", "", 4));
            assertFalse(a.checkConflict(b)); // [1,2) vs [3,4) -> false
        }

        @Test
        void noConflict_whenTouchingAtEndpoints() {
            Event a = new Event("A", new DateTimeStub("", "", 1), new DateTimeStub("", "", 2));
            Event b = new Event("B", new DateTimeStub("", "", 2), new DateTimeStub("", "", 4));
            // Using: a.start < b.end AND b.start < a.end
            // Here: 1 < 4 true AND 2 < 2 false -> false
            assertFalse(a.checkConflict(b));
        }

        @Test
        void conflict_whenProperOverlap() {
            Event a = new Event("A", new DateTimeStub("", "", 1), new DateTimeStub("", "", 3));
            Event b = new Event("B", new DateTimeStub("", "", 2), new DateTimeStub("", "", 4));
            assertTrue(a.checkConflict(b)); // [1,3) intersects [2,4)
        }

        @Test
        void conflict_whenOneInsideAnother() {
            Event outer = new Event("Out", new DateTimeStub("", "", 1), new DateTimeStub("", "", 10));
            Event inner = new Event("In", new DateTimeStub("", "", 3), new DateTimeStub("", "", 4));
            assertTrue(outer.checkConflict(inner));
            assertTrue(inner.checkConflict(outer));
        }

        @Test
        void conflict_whenIdenticalIntervals_sameNameTriggersSuperConflictToo() {
            Event a = new Event("Same", new DateTimeStub("", "", 5), new DateTimeStub("", "", 8));
            Event b = new Event("Same", new DateTimeStub("", "", 5), new DateTimeStub("", "", 8));
            assertTrue(a.checkConflict(b));
        }
    }

    // ---------- fromInputCommand() parsing ----------
    @Nested
    @DisplayName("fromInputCommand() parsing")
    class Parsing {

        @Test
        void happyPath_normalOrder() {
            Event e = Event.fromInputCommand("event project meeting /from 1/1/2025 0900 /to 1/1/2025 1000");
            assertEquals("project meeting", e.getName());
            // No assertion on formatting; just existence (parsing uses real DateTime)
            assertTrue(e instanceof Event);
        }

        @Test
        void happyPath_reversedOrder() {
            Event e = Event.fromInputCommand("event team sync /to 01/01/2025 1000   /from 01/01/2025 0900");
            assertEquals("team sync", e.getName());
        }

        @Test
        void happyPath_weirdSpacingTabs() {
            Event e = Event.fromInputCommand("event\tDeep Work   /from   31/12/2025 1300\t/to   31/12/2025 1500");
            assertEquals("Deep Work", e.getName());
        }

        @Test
        void error_missingTokens_throwsUsage() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()
                    -> Event.fromInputCommand("event planning session from 1/1/2025 0900 to 1/1/2025 1000"));
            assertTrue(ex.getMessage().contains("Usage: event [eventName] /from [startTime] /to [endTime]"));
        }

        @Test
        void error_emptyName_throwsUsage() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()
                    -> Event.fromInputCommand("event   /from 1/1/2025 0900 /to 1/1/2025 1000"));
            assertTrue(ex.getMessage().contains("cannot be empty"));
        }

        @Test
        void error_emptyFromOrTo_throwsUsage() {
            IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, ()
                    -> Event.fromInputCommand("event a /from   /to 1/1/2025 1000"));
            IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, ()
                    -> Event.fromInputCommand("event a /from 1/1/2025 0900 /to   "));
            assertTrue(ex1.getMessage().contains("cannot be empty"));
            assertTrue(ex2.getMessage().contains("cannot be empty"));
        }

        @Test
        void error_badDateFormats_throwsFormatHelp() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()
                    -> Event.fromInputCommand("event a /from 2025-01-01 09:00 /to 2025-01-01 10:00"));
            assertTrue(ex.getMessage().contains("dd/mm/yyyy HHmm"));
        }

        @Test
        void error_startEqualsEnd_disallowed() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()
                    -> Event.fromInputCommand("event a /from 1/1/2025 0900 /to 1/1/2025 0900"));
            assertTrue(ex.getMessage().contains("start time must be before end time"));
        }

        @Test
        void error_startAfterEnd_disallowed() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()
                    -> Event.fromInputCommand("event a /from 1/1/2025 1100 /to 1/1/2025 1000"));
            assertTrue(ex.getMessage().contains("start time must be before end time"));
        }
    }
}
