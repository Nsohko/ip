package chalk.ui;

import chalk.Chalk;
import chalk.commands.ChalkCommand;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class GuiMainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Chalk chalk;
    private GuiUI guiUI;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image chalkImage = new Image(this.getClass().getResourceAsStream("/images/Chalk.png"));

    /**
     * Intialize the GuiMainWindow
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        this.guiUI = new GuiUI(dialogContainer, chalkImage);
    }

    /** Injects the Chalk instance */
    public void setChalk() {
        this.chalk = new Chalk(this.guiUI);
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input == null || input.isBlank()) {
            return;
        }

        // Show the user bubble immediately
        dialogContainer.getChildren()
                .add(DialogBox.getUserDialog(input, userImage));
        userInput.clear();

        // Parse + execute; the command will push replies via fxUi
        try {
            ChalkCommand command = ChalkCommand.parse(input);
            command.execute(chalk);

            if (command.shouldExit()) {
                // optional UX niceties:
                userInput.setEditable(false);
                sendButton.setDisable(true);
                // close after a short delay so the goodbye bubble renders
                javafx.animation.PauseTransition pt = new javafx.animation.PauseTransition(
                        javafx.util.Duration.millis(200));
                pt.setOnFinished(e -> javafx.application.Platform.exit());
                pt.play();
            }
        } catch (Exception e) {
            guiUI.printError(e.getMessage());
        }
    }
}
