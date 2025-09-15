package chalk.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import chalk.ChalkStub;

class ListCommandTest {

    @Test
    void execute_callsListTasks() {
        ChalkStub chalk = new ChalkStub();
        ListCommand cmd = new ListCommand();

        cmd.execute(chalk);

        assertEquals(1, chalk.listCount, "ListCommand should call chalk.listTasks()");
    }

    @Test
    void execute_multipleTimes_incrementsCount() {
        ChalkStub chalk = new ChalkStub();
        ListCommand cmd = new ListCommand();

        cmd.execute(chalk);
        cmd.execute(chalk);
        cmd.execute(chalk);

        assertEquals(3, chalk.listCount, "ListCommand should call listTasks() once per execute()");
    }
}
