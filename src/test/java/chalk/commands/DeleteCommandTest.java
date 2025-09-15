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

        assertEquals(1, chalk.getDeleteCount());
        assertEquals(3, chalk.getLastDeleted());
    }

    @Test
    void execute_multipleSpaces_stillParsesIndex() {
        ChalkStub chalk = new ChalkStub();

        new DeleteCommand("delete     12").execute(chalk);

        assertEquals(1, chalk.getDeleteCount());
        assertEquals(12, chalk.getLastDeleted());
    }

    @Test
    void execute_missingIndex_doesNotDelete() {
        ChalkStub chalk = new ChalkStub();

        new DeleteCommand("delete").execute(chalk);

        assertEquals(0, chalk.getDeleteCount());
    }

    @Test
    void execute_nonNumericIndex_doesNotDelete() {
        ChalkStub chalk = new ChalkStub();

        new DeleteCommand("delete two").execute(chalk);

        assertEquals(0, chalk.getDeleteCount());
    }

    @Test
    void execute_trailingWhitespace_success() {
        ChalkStub chalk = new ChalkStub();

        new DeleteCommand("delete 2   ").execute(chalk);

        // Current implementation still works because split("\\s+") ignores trailing ws.
        assertEquals(1, chalk.getDeleteCount());
        assertEquals(2, chalk.getLastDeleted());
    }
}
