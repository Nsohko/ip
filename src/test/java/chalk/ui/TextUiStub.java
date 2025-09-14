package chalk.ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Stub for TextUI that records output for testing.
 */
public class TextUiStub extends TextUI {
    public final List<String> replies = new ArrayList<>();
    public final List<String> errors = new ArrayList<>();

    @Override
    public void printReply(String message) {
        replies.add(message);
    }

    @Override
    public void printError(String message) {
        errors.add(message);
    }
}
