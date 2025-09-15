package chalk.commands;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import chalk.ChalkStub;

class FindCommandTest {

    @Test
    void execute_singleKeyword_callsSearchWithOneParam() {
        ChalkStub chalk = new ChalkStub();

        new FindCommand("find book").execute(chalk);

        assertEquals(1, chalk.getSearchCount());
        assertArrayEquals(new String[] { "book" }, chalk.getLastSearchParams());
    }

    @Test
    void execute_multipleKeywords_splitsOnWhitespace() {
        ChalkStub chalk = new ChalkStub();

        new FindCommand("find   red   apple   pie").execute(chalk);

        assertEquals(1, chalk.getSearchCount());
        assertArrayEquals(new String[] { "red", "apple", "pie" }, chalk.getLastSearchParams());
    }

    @Test
    void execute_mixedWhitespace_success() {
        ChalkStub chalk = new ChalkStub();

        new FindCommand("find\tfoo\t  bar\tbaz").execute(chalk);

        assertEquals(1, chalk.getSearchCount());
        assertArrayEquals(new String[] { "foo", "bar", "baz" }, chalk.getLastSearchParams());
    }

    @Test
    void execute_onlyFind_doesNotSearch() {
        ChalkStub chalk = new ChalkStub();

        // Current code throws StringIndexOutOfBoundsException here (substring(5)).
        // After the fix (see notes), this should print an error instead.
        new FindCommand("find").execute(chalk);

        assertEquals(0, chalk.getSearchCount());
    }

    @Test
    void execute_findWithOnlySpaces_doesNotSearch() {
        ChalkStub chalk = new ChalkStub();

        new FindCommand("find     ").execute(chalk);

        assertEquals(0, chalk.getSearchCount());
    }
}
