package chalk.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import chalk.ChalkStub;

class MarkDoneCommandTest {

    @Test
    void execute_validIndex_marksThatIndex() {
        ChalkStub chalk = new ChalkStub();

        new MarkDoneCommand("mark 3").execute(chalk);

        assertEquals(1, chalk.markCount);
        assertEquals(3, chalk.lastMarked);
    }

    @Test
    void execute_multipleSpaces_stillParsesIndex() {
        ChalkStub chalk = new ChalkStub();

        new MarkDoneCommand("mark     12").execute(chalk);

        assertEquals(1, chalk.markCount);
        assertEquals(12, chalk.lastMarked);
    }

    @Test
    void execute_trailingWhitespace_validIndex_ok() {
        ChalkStub chalk = new ChalkStub();

        new MarkDoneCommand("mark 2   ").execute(chalk);

        assertEquals(1, chalk.markCount);
        assertEquals(2, chalk.lastMarked);
    }

    @Test
    void execute_missingIndex_printsError_doesNotMark() {
        ChalkStub chalk = new ChalkStub();

        new MarkDoneCommand("mark").execute(chalk);

        assertEquals(0, chalk.markCount);
    }

    @Test
    void execute_nonNumericIndex_printsError_doesNotMark() {
        ChalkStub chalk = new ChalkStub();

        new MarkDoneCommand("mark two").execute(chalk);

        assertEquals(0, chalk.markCount);
    }

    @Test
    void execute_leadingWhitespace_currentlyErrors_unlessYouStrip() {
        ChalkStub chalk = new ChalkStub();

        // With current implementation (no strip before split), this becomes:
        // ["", "mark", "5"] -> parts[1] = "mark" -> NumberFormatException -> error.
        new MarkDoneCommand("  mark 5").execute(chalk);

        assertEquals(0, chalk.markCount);
    }
}
