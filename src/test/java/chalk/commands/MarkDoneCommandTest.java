package chalk.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import chalk.ChalkStub;

class MarkDoneCommandTest {

    @Test
    void execute_validIndex_marksThatIndex() {
        ChalkStub chalk = new ChalkStub();

        new MarkDoneCommand("mark 3").execute(chalk);

        assertEquals(1, chalk.getMarkCount());
        assertEquals(3, chalk.getLastMarked());
    }

    @Test
    void execute_multipleSpaces_stillParsesIndex() {
        ChalkStub chalk = new ChalkStub();

        new MarkDoneCommand("mark     12").execute(chalk);

        assertEquals(1, chalk.getMarkCount());
        assertEquals(12, chalk.getLastMarked());
    }

    @Test
    void execute_trailingWhitespace_success() {
        ChalkStub chalk = new ChalkStub();

        new MarkDoneCommand("mark 2   ").execute(chalk);

        assertEquals(1, chalk.getMarkCount());
        assertEquals(2, chalk.getLastMarked());
    }

    @Test
    void execute_missingIndex_doesNotMark() {
        ChalkStub chalk = new ChalkStub();

        new MarkDoneCommand("mark").execute(chalk);

        assertEquals(0, chalk.getMarkCount());
    }

    @Test
    void execute_nonNumericIndex_doesNotMark() {
        ChalkStub chalk = new ChalkStub();

        new MarkDoneCommand("mark two").execute(chalk);

        assertEquals(0, chalk.getMarkCount());
    }
}
