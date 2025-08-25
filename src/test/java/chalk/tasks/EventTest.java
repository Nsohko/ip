package chalk.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import chalk.datetime.DateTimeStub;

class EventTest {

    // ---------- toString() tests ----------
    @Test
    void toString_incompleteTask_matchExpected() {
        DateTimeStub startTime = new DateTimeStub("06/08/2025 1820", "6 June 2025 1820hrs");
        DateTimeStub endTime = new DateTimeStub("10/10/2026 0400", "10 October 2026 0400hrs");
        Event e = new Event("return book", startTime, endTime);

        String s = e.toString();

        assertEquals("[E][ ] return book (from: 6 June 2025 1820hrs to: 10 October 2026 0400hrs)", s);
    }

    @Test
    void toString_completeTask_matchExpected() {
        DateTimeStub startTime = new DateTimeStub("06/08/2025 1820", "6 June 2025 1820hrs");
        DateTimeStub endTime = new DateTimeStub("10/10/2026 0400", "10 October 2026 0400hrs");
        Event e = new Event("return book", startTime, endTime);
        e.markAsDone();

        String s = e.toString();

        assertEquals("[E][X] return book (from: 6 June 2025 1820hrs to: 10 October 2026 0400hrs)", s);
    }

    // ---------- toFileStorage() tests ----------
    @Test
    void toFileStorage_incompleteTask_matchExpected() {
        DateTimeStub startTime = new DateTimeStub("10/10/2024 0400", "10 October 2024 0400hrs");
        DateTimeStub endTime = new DateTimeStub("04/11/2024 1820", "4 October 2024 1820hrs");

        Event e = new Event("return book", startTime, endTime);

        String fs = e.toFileStorage();

        assertEquals("event return book /from 10/10/2024 0400 /to 04/11/2024 1820 | 0", fs);
    }

    @Test
    void toFileStorage_completeTask_matchExpected() {
        DateTimeStub startTime = new DateTimeStub("10/10/2024 0400", "10 October 2024 0400hrs");
        DateTimeStub endTime = new DateTimeStub("04/11/2024 1820", "4 October 2024 1820hrs");

        Event e = new Event("return book", startTime, endTime);
        e.markAsDone();

        String fs = e.toFileStorage();

        assertEquals("event return book /from 10/10/2024 0400 /to 04/11/2024 1820 | 1", fs);
    }
}
