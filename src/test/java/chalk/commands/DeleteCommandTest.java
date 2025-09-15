package chalk.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import chalk.ChalkStub;

/**
 * Tests for DeleteCommand.
 */
class DeleteCommandTest {

    @Test
    void execute_validIndex_callsDeleteWithSameIndex() {
        ChalkStub chalk = new ChalkStub();

        new DeleteCommand("delete 3").execute(chalk);

        assertEquals(1, chalk.deleteCount);
        assertEquals(3, chalk.lastDeleted);
    }

    @Test
    void execute_multipleSpaces_stillParsesIndex() {
        ChalkStub chalk = new ChalkStub();

        new DeleteCommand("delete     12").execute(chalk);

        assertEquals(1, chalk.deleteCount);
        assertEquals(12, chalk.lastDeleted);
    }

    @Test
    void execute_missingIndex_printsError_doesNotDelete() {
        ChalkStub chalk = new ChalkStub();

        new DeleteCommand("delete").execute(chalk);

        assertEquals(0, chalk.deleteCount);
    }

    @Test
    void execute_nonNumericIndex_printsError_doesNotDelete() {
        ChalkStub chalk = new ChalkStub();

        new DeleteCommand("delete two").execute(chalk);

        assertEquals(0, chalk.deleteCount);
    }

    @Test
    void execute_trailingWhitespace_validIndex_ok() {
        ChalkStub chalk = new ChalkStub();

        new DeleteCommand("delete 2   ").execute(chalk);

        // Current implementation still works because split("\\s+") ignores trailing ws.
        assertEquals(1, chalk.deleteCount);
        assertEquals(2, chalk.lastDeleted);
    }

    @Test
    void execute_leadingWhitespace_currentlyFails_suggestStrip() {
        ChalkStub chalk = new ChalkStub();

        // With leading space, split()[1] becomes "delete" -> NumberFormatException -> error.
        new DeleteCommand("  delete 5").execute(chalk);

        assertEquals(0, chalk.deleteCount);
    }
}
