package chalk.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import chalk.ChalkStub;

class UnmarkDoneCommandTest {

    @Test
    void execute_validIndex_unmarksThatIndex() {
        ChalkStub chalk = new ChalkStub();

        new UnmarkDoneCommand("unmark 4").execute(chalk);

        assertEquals(1, chalk.unmarkCount);
        assertEquals(4, chalk.lastUnmarked);
    }

    @Test
    void execute_multipleSpaces_stillParsesIndex() {
        ChalkStub chalk = new ChalkStub();

        new UnmarkDoneCommand("unmark     10").execute(chalk);

        assertEquals(1, chalk.unmarkCount);
        assertEquals(10, chalk.lastUnmarked);
    }

    @Test
    void execute_trailingWhitespace_validIndex_ok() {
        ChalkStub chalk = new ChalkStub();

        new UnmarkDoneCommand("unmark 2   ").execute(chalk);

        assertEquals(1, chalk.unmarkCount);
        assertEquals(2, chalk.lastUnmarked);
    }

    @Test
    void execute_missingIndex_printsError_doesNotUnmark() {
        ChalkStub chalk = new ChalkStub();

        new UnmarkDoneCommand("unmark").execute(chalk);

        assertEquals(0, chalk.unmarkCount);
    }

    @Test
    void execute_nonNumericIndex_printsError_doesNotUnmark() {
        ChalkStub chalk = new ChalkStub();

        new UnmarkDoneCommand("unmark two").execute(chalk);

        assertEquals(0, chalk.unmarkCount);
    }

    @Test
    void execute_leadingWhitespace_currentlyErrors_unlessStripAdded() {
        ChalkStub chalk = new ChalkStub();

        // With current implementation, "  unmark 5" -> parts[1] = "unmark" -> NumberFormatException -> error.
        new UnmarkDoneCommand("  unmark 5").execute(chalk);

        assertEquals(0, chalk.unmarkCount);
    }
}
