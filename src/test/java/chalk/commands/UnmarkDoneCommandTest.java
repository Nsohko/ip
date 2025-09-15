package chalk.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import chalk.ChalkStub;

class UnmarkDoneCommandTest {

    @Test
    void execute_validIndex_unmarksThatIndex() {
        ChalkStub chalk = new ChalkStub();

        new UnmarkDoneCommand("unmark 4").execute(chalk);

        assertEquals(1, chalk.getUnmarkCount());
        assertEquals(4, chalk.getLastUnmarked());
    }

    @Test
    void execute_multipleSpaces_stillParsesIndex() {
        ChalkStub chalk = new ChalkStub();

        new UnmarkDoneCommand("unmark     10").execute(chalk);

        assertEquals(1, chalk.getUnmarkCount());
        assertEquals(10, chalk.getLastUnmarked());
    }

    @Test
    void execute_trailingWhitespace_success() {
        ChalkStub chalk = new ChalkStub();

        new UnmarkDoneCommand("unmark 2   ").execute(chalk);

        assertEquals(1, chalk.getUnmarkCount());
        assertEquals(2, chalk.getLastUnmarked());
    }

    @Test
    void execute_missingIndex_doesNotUnmark() {
        ChalkStub chalk = new ChalkStub();

        new UnmarkDoneCommand("unmark").execute(chalk);

        assertEquals(0, chalk.getUnmarkCount());
    }

    @Test
    void execute_nonNumericIndex_doesNotUnmark() {
        ChalkStub chalk = new ChalkStub();

        new UnmarkDoneCommand("unmark two").execute(chalk);

        assertEquals(0, chalk.getUnmarkCount());
    }
}
