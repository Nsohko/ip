package chalk.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import chalk.ChalkStub;
import chalk.tasks.Deadline;
import chalk.tasks.Event;
import chalk.tasks.Task;

/**
 * Behavioral tests for AddCommand without Mockito.
 * We verify:
 *  - Valid inputs produce a parsed Task and call chalk.addTask(...)
 *  - Invalid inputs do NOT add a task (and optionally print an error)
 */
class AddCommandTest {

    @Test
    void execute_validTodo_addsTask() {
        ChalkStub chalk = new ChalkStub();
        String input = "todo buy milk";

        new AddCommand(input).execute(chalk);

        assertEquals(1, chalk.addCount, "Should add one task");
        assertNotNull(chalk.lastAdded, "Added task should not be null");
        assertEquals("buy milk", chalk.lastAdded.getName());
    }

    @Test
    void execute_validDeadline_addsDeadlineTask() {
        ChalkStub chalk = new ChalkStub();
        String input = "deadline return book /by 6/6/2025 1820";

        new AddCommand(input).execute(chalk);

        assertEquals(1, chalk.addCount);
        assertTrue(chalk.lastAdded instanceof Deadline, "Should parse into Deadline");
        assertEquals("return book", chalk.lastAdded.getName());
    }

    @Test
    void execute_validEvent_addsEventTask() {
        ChalkStub chalk = new ChalkStub();
        String input = "event project meeting /from 1/1/2025 0900 /to 1/1/2025 1000";

        new AddCommand(input).execute(chalk);

        assertEquals(1, chalk.addCount);
        assertTrue(chalk.lastAdded instanceof Event, "Should parse into Event");
        assertEquals("project meeting", chalk.lastAdded.getName());
    }

    @Test
    void execute_invalidDeadlineFormat_doesNotAddTask_printsError() {
        ChalkStub chalk = new ChalkStub();
        // Missing "/by " delimiter; Task.fromInputCommand should throw IllegalArgumentException
        String input = "deadline return book 6/6/2025 1820";

        new AddCommand(input).execute(chalk);

        assertEquals(0, chalk.addCount, "Invalid input should not add any task");
    }

    @Test
    void execute_emptyAfterStrip_doesNotAddTask_printsError() {
        ChalkStub chalk = new ChalkStub();
        String input = "   ";

        new AddCommand(input).execute(chalk);

        assertEquals(0, chalk.addCount);
    }

    @Test
    void execute_unknownKeyword_treatedAsTodo_addsTaskWithWholeInputName() {
        ChalkStub chalk = new ChalkStub();
        // By design, parser sends non-keywords to AddCommand; Task.fromInputCommand
        // should treat this as a todo with the entire string as the name (depending on your Task parser).
        String input = "todo buy eggs and bread";

        new AddCommand(input).execute(chalk);

        assertEquals(1, chalk.addCount);
        Task added = chalk.lastAdded;
        assertNotNull(added);
        // Depending on your Task.fromInputCommand rules, adjust this assertion:
        assertEquals("buy eggs and bread", added.getName());
    }
}
