package chalk.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChalkCommandParseTest {

    // --- Exact matches (case-insensitive), no args ---
    @Test
    @DisplayName("bye -> ExitCommand")
    void parse_bye_returnsExitCommand() {
        ChalkCommand cmd = ChalkCommand.parse("bye");
        assertTrue(cmd instanceof ExitCommand);
    }

    @Test
    @DisplayName("BYE -> ExitCommand (case-insensitive)")
    void parse_byeCaseInsensitive_returnsExitCommand() {
        ChalkCommand cmd = ChalkCommand.parse("BYE");
        assertTrue(cmd instanceof ExitCommand);
    }

    @Test
    @DisplayName("list -> ListCommand")
    void parse_list_returnsListCommand() {
        ChalkCommand cmd = ChalkCommand.parse("list");
        assertTrue(cmd instanceof ListCommand);
    }

    @Test
    @DisplayName("List -> ListCommand (case-insensitive)")
    void parse_listCaseInsensitive_returnsListCommand() {
        ChalkCommand cmd = ChalkCommand.parse("List");
        assertTrue(cmd instanceof ListCommand);
    }

    // --- Prefix commands (case-insensitive), with args ---
    @Test
    @DisplayName("mark 1 -> MarkDoneCommand")
    void parse_markWithArg_returnsMarkDoneCommand() {
        ChalkCommand cmd = ChalkCommand.parse("mark 1");
        assertTrue(cmd instanceof MarkDoneCommand);
    }

    @Test
    @DisplayName("MaRk 23 -> MarkDoneCommand (case-insensitive)")
    void parse_markMixedCaseWithArg_returnsMarkDoneCommand() {
        ChalkCommand cmd = ChalkCommand.parse("MaRk 23");
        assertTrue(cmd instanceof MarkDoneCommand);
    }

    @Test
    @DisplayName("unmark 3 -> UnmarkDoneCommand")
    void parse_unmarkWithArg_returnsUnmarkDoneCommand() {
        ChalkCommand cmd = ChalkCommand.parse("unmark 3");
        assertTrue(cmd instanceof UnmarkDoneCommand);
    }

    @Test
    @DisplayName("delete 4 -> DeleteCommand")
    void parse_deleteWithArg_returnsDeleteCommand() {
        ChalkCommand cmd = ChalkCommand.parse("delete 4");
        assertTrue(cmd instanceof DeleteCommand);
    }

    @Test
    @DisplayName("find abc -> FindCommand")
    void parse_findWithArg_returnsFindCommand() {
        ChalkCommand cmd = ChalkCommand.parse("find abc");
        assertTrue(cmd instanceof FindCommand);
    }

    // --- Prefix tokens with trailing space but no argument ---
    // (These still return the appropriate command type; arg validation happens later.)
    @Test
    @DisplayName("mark  -> MarkDoneCommand (no arg yet)")
    void parse_markSpace_returnsMarkDoneCommand() {
        ChalkCommand cmd = ChalkCommand.parse("mark ");
        assertTrue(cmd instanceof MarkDoneCommand);
    }

    @Test
    @DisplayName("unmark  -> UnmarkDoneCommand (no arg yet)")
    void parse_unmarkSpace_returnsUnmarkDoneCommand() {
        ChalkCommand cmd = ChalkCommand.parse("unmark ");
        assertTrue(cmd instanceof UnmarkDoneCommand);
    }

    @Test
    @DisplayName("delete  -> DeleteCommand (no arg yet)")
    void parse_deleteSpace_returnsDeleteCommand() {
        ChalkCommand cmd = ChalkCommand.parse("delete ");
        assertTrue(cmd instanceof DeleteCommand);
    }

    @Test
    @DisplayName("find  -> FindCommand (no arg yet)")
    void parse_findSpace_returnsFindCommand() {
        ChalkCommand cmd = ChalkCommand.parse("find ");
        assertTrue(cmd instanceof FindCommand);
    }

    // --- Keyword without space/arg is still the right command (due to startsWith) ---
    @Test
    @DisplayName("mark -> MarkDoneCommand")
    void parse_markNoArg_returnsMarkDoneCommand() {
        assertTrue(ChalkCommand.parse("mark") instanceof MarkDoneCommand);
    }

    @Test
    @DisplayName("unmark -> UnmarkDoneCommand")
    void parse_unmarkNoArg_returnsUnmarkDoneCommand() {
        assertTrue(ChalkCommand.parse("unmark") instanceof UnmarkDoneCommand);
    }

    @Test
    @DisplayName("delete -> DeleteCommand")
    void parse_deleteNoArg_returnsDeleteCommand() {
        assertTrue(ChalkCommand.parse("delete") instanceof DeleteCommand);
    }

    @Test
    @DisplayName("find -> FindCommand")
    void parse_findNoArg_returnsFindCommand() {
        assertTrue(ChalkCommand.parse("find") instanceof FindCommand);
    }

    // --- Whitespace handling via strip() ---
    @Test
    @DisplayName("\" mark\" -> MarkDoneCommand (leading whitespace stripped)")
    void parse_leadingWhitespace_mark_returnsMarkDone() {
        assertTrue(ChalkCommand.parse(" mark") instanceof MarkDoneCommand);
    }

    @Test
    @DisplayName("\"bye \" -> ExitCommand (trailing whitespace stripped)")
    void parse_trailingWhitespace_bye_returnsExit() {
        assertTrue(ChalkCommand.parse("bye ") instanceof ExitCommand);
    }

    @Test
    @DisplayName("\"\\tlist\" -> ListCommand (leading tab stripped)")
    void parse_tabList_returnsList() {
        assertTrue(ChalkCommand.parse("\tlist") instanceof ListCommand);
    }

    @Test
    @DisplayName("\"    \" -> AddCommand (empty after strip)")
    void parse_onlyWhitespace_returnsAdd() {
        assertTrue(ChalkCommand.parse("    ") instanceof AddCommand);
    }

    // --- Fallbacks to AddCommand ---
    @Test
    @DisplayName("random text -> AddCommand")
    void parse_randomText_returnsAddCommand() {
        ChalkCommand cmd = ChalkCommand.parse("hello world");
        assertTrue(cmd instanceof AddCommand);
    }

    // --- Document current prefix-overmatch behavior (intentional per startsWith) ---
    // If you later enforce word boundaries, these expectations will need updating.
    @Test
    @DisplayName("\"marker 1\" -> MarkDoneCommand (prefix match)")
    void parse_marker_returnsMarkDone_dueToPrefixMatch() {
        assertTrue(ChalkCommand.parse("marker 1") instanceof MarkDoneCommand);
    }

    @Test
    @DisplayName("\"unmarked 2\" -> UnmarkDoneCommand (prefix match)")
    void parse_unmarked_returnsUnmark_dueToPrefixMatch() {
        assertTrue(ChalkCommand.parse("unmarked 2") instanceof UnmarkDoneCommand);
    }

    @Test
    @DisplayName("\"deletee 3\" -> DeleteCommand (prefix match)")
    void parse_deletee_returnsDelete_dueToPrefixMatch() {
        assertTrue(ChalkCommand.parse("deletee 3") instanceof DeleteCommand);
    }

    @Test
    @DisplayName("\"finder x\" -> FindCommand (prefix match)")
    void parse_finder_returnsFind_dueToPrefixMatch() {
        assertTrue(ChalkCommand.parse("finder x") instanceof FindCommand);
    }
}
