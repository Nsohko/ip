package chalk.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ChalkCommandParseSimpleTest {

    // --- Exact matches ---
    @Test
    void parse_bye_returnsExitCommand() {
        ChalkCommand cmd = ChalkCommand.parse("bye");
        assertTrue(cmd instanceof ExitCommand);
    }

    @Test
    void parse_list_returnsListCommand() {
        ChalkCommand cmd = ChalkCommand.parse("list");
        assertTrue(cmd instanceof ListCommand);
    }

    // --- Prefix commands (with args) ---
    @Test
    void parse_markWithArg_returnsMarkDoneCommand() {
        ChalkCommand cmd = ChalkCommand.parse("mark 1");
        assertTrue(cmd instanceof MarkDoneCommand);
    }

    @Test
    void parse_unmarkWithArg_returnsUnmarkDoneCommand() {
        ChalkCommand cmd = ChalkCommand.parse("unmark 3");
        assertTrue(cmd instanceof UnmarkDoneCommand);
    }

    @Test
    void parse_deleteWithArg_returnsDeleteCommand() {
        ChalkCommand cmd = ChalkCommand.parse("delete 4");
        assertTrue(cmd instanceof DeleteCommand);
    }

    // --- Prefix tokens with trailing space but no argument ---
    @Test
    void parse_markSpace_returnsMarkDoneCommand() {
        ChalkCommand cmd = ChalkCommand.parse("mark ");
        assertTrue(cmd instanceof MarkDoneCommand);
    }

    @Test
    void parse_unmarkSpace_returnsUnmarkDoneCommand() {
        ChalkCommand cmd = ChalkCommand.parse("unmark ");
        assertTrue(cmd instanceof UnmarkDoneCommand);
    }

    @Test
    void parse_deleteSpace_returnsDeleteCommand() {
        ChalkCommand cmd = ChalkCommand.parse("delete ");
        assertTrue(cmd instanceof DeleteCommand);
    }

    // --- Fallbacks to AddCommand ---
    @Test
    void parse_randomText_returnsAddCommand() {
        ChalkCommand cmd = ChalkCommand.parse("hello world");
        assertTrue(cmd instanceof AddCommand);
    }

    @Test
    void parse_wrongCase_returnsAddCommand() {
        assertTrue(ChalkCommand.parse("LIST") instanceof AddCommand);
        assertTrue(ChalkCommand.parse("Bye") instanceof AddCommand);
    }

    @Test
    void parse_missingSpaceAfterKeyword_returnsAddCommand() {
        assertTrue(ChalkCommand.parse("mark") instanceof AddCommand);
        assertTrue(ChalkCommand.parse("unmark") instanceof AddCommand);
        assertTrue(ChalkCommand.parse("delete") instanceof AddCommand);
    }

    @Test
    void parse_whitespaceIssues_returnsAddCommand() {
        assertTrue(ChalkCommand.parse(" mark") instanceof AddCommand);
        assertTrue(ChalkCommand.parse("bye ") instanceof AddCommand);
        assertTrue(ChalkCommand.parse("    ") instanceof AddCommand);
        assertTrue(ChalkCommand.parse("\tlist") instanceof AddCommand);
    }
}
