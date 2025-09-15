package chalk.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import chalk.ChalkStub;

class ExitCommandTest {

    @Test
    void execute_callsTerminate() {
        ChalkStub chalk = new ChalkStub();
        ExitCommand cmd = new ExitCommand();

        cmd.execute(chalk);

        assertTrue(chalk.terminated, "ExitCommand should call chalk.terminate()");
    }

    @Test
    void shouldExit_returnsTrue() {
        ExitCommand cmd = new ExitCommand();
        assertTrue(cmd.shouldExit(), "ExitCommand.shouldExit() should return true");
    }
}
