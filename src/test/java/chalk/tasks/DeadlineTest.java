package chalk.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import chalk.datetime.DateTimeStub;

class DeadlineTest {

    // ---------- toString() tests ----------
    @Test
    void toString_incompleteTask_matchExpected() {
        DateTimeStub dt = new DateTimeStub("06/08/2025 1820", "6 June 2025 1820hrs");
        Deadline d = new Deadline("return book", dt);

        String s = d.toString();

        assertEquals("[D][ ] return book (by: 6 June 2025 1820hrs)", s);
    }

    @Test
    void toString_completeTask_matchExpected() {
        DateTimeStub dt = new DateTimeStub("06/08/2025 1820", "6 June 2025 1820hrs");
        Deadline d = new Deadline("return book", dt);
        d.markAsDone();

        String s = d.toString();

        assertEquals("[D][X] return book (by: 6 June 2025 1820hrs)", s);
    }

    // ---------- toFileStorage() tests ----------
    @Test
    void toFileStorage_incompleteTask_matchExpected() {
        DateTimeStub dt = new DateTimeStub("10/10/2024 0400", "10 October 2024 0400hrs");
        Deadline d = new Deadline("return book", dt);

        String fs = d.toFileStorage();

        assertEquals("deadline return book /by 10/10/2024 0400 | 0", fs);
    }

    @Test
    void toFileStorage_completeTask_matchExpected() {
        DateTimeStub dt = new DateTimeStub("10/10/2024 0400", "10 October 2024 0400hrs");
        Deadline d = new Deadline("return book", dt);
        d.markAsDone();

        String fs = d.toFileStorage();

        assertEquals("deadline return book /by 10/10/2024 0400 | 1", fs);
    }
}
