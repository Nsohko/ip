// src/test/java/chalk/ui/UiTest.java
package chalk.ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UiTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    private static final String NL = System.lineSeparator();
    private static final String LINE = "-------------------------------";

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    @SuppressWarnings("unused")
    void tearDown() {
        System.setOut(originalOut);
    }

    private String captured() {
        // Normalize line endings to \n for stable assertions
        return outContent.toString().replace("\r\n", "\n").replace("\r", "\n");
    }

    private String normalize(String s) {
        return s.replace("\r\n", "\n").replace("\r", "\n");
    }

    @Test
    void reply_printsBoundedBlock_withEachLineIndented() {
        Ui ui = new Ui();
        ui.reply("Hello\nWorld");

        String expected
                = LINE + NL
                + "    Hello" + NL
                + "    World" + NL
                + LINE + NL;

        assertEquals(normalize(expected), captured());
    }

    @Test
    void printError_withMessage_printsErrorHeaderAndMessage() {
        Ui ui = new Ui();
        ui.printError("Something went wrong\nPlease retry");

        String expected
                = LINE + NL
                + "    ERROR!" + NL
                + "    Something went wrong" + NL
                + "    Please retry" + NL
                + LINE + NL;

        assertEquals(normalize(expected), captured());
    }

    @Test
    void printError_withNullMessage_printsOnlyHeader() {
        Ui ui = new Ui();
        ui.printError(null);

        String expected
                = LINE + NL
                + "    ERROR!" + NL
                + LINE + NL;

        assertEquals(normalize(expected), captured());
    }

    @Test
    void reply_handlesSingleLine_correctFormat() {
        Ui ui = new Ui();
        ui.reply("Hello");

        String expected
                = LINE + NL
                + "    Hello" + NL
                + LINE + NL;

        assertEquals(normalize(expected), captured());
    }
}
