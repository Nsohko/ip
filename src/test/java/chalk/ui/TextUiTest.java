package chalk.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TextUiTest {

    private static final String LINE = "-------------------------------";
    private static final String NL = System.lineSeparator();

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

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
        TextUI ui = new TextUI();
        ui.printReply("Hello\nWorld");

        String expected = LINE + NL
                + "    Hello" + NL
                + "    World" + NL
                + LINE + NL;

        assertEquals(normalize(expected), captured());
    }

    @Test
    void printError_withMessage_printsErrorHeaderAndMessage() {
        TextUI ui = new TextUI();
        ui.printError("Something went wrong\nPlease retry");

        String expected = LINE + NL
                + "    ERROR!" + NL
                + "    Something went wrong" + NL
                + "    Please retry" + NL
                + LINE + NL;

        assertEquals(normalize(expected), captured());
    }

    @Test
    void printError_withNullMessage_printsOnlyHeader() {
        TextUI ui = new TextUI();
        ui.printError(null);

        String expected = LINE + NL
                + "    ERROR!" + NL
                + LINE + NL;

        assertEquals(normalize(expected), captured());
    }

    @Test
    void reply_handlesSingleLine_correctFormat() {
        TextUI ui = new TextUI();
        ui.printReply("Hello");

        String expected = LINE + NL
                + "    Hello" + NL
                + LINE + NL;

        assertEquals(normalize(expected), captured());
    }
}
