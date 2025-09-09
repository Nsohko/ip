package chalk.ui;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 * The GUIUI class is reponsible for managing Chalk's interactions with the user via JavaFX.
 */
public final class GuiUI {
    /**
     * The dialogue box container to display messages
     */
    private final VBox dialogContainer;

    /**
     * The image of Chalk
     */
    private final Image chalkImage;

    /**
     * Constructor for GuiUI object
     *
     * @param dialogContainer The dialogue box container to display messages
     * @param chalkImage The image of Chalk
     */
    public GuiUI(VBox dialogContainer, Image chalkImage) {
        this.dialogContainer = dialogContainer;
        this.chalkImage = chalkImage;
    }

    /**
     * Displays a message on JavaFX
     *
     * @param message The message to be displayed
     */
    public void printReply(String message) {
        Platform.runLater(() ->
                dialogContainer.getChildren()
                        .add(DialogBox.getChalkDialog(message, chalkImage))
        );
    }

    /**
     * Displays out an error with appropriate formatting on JavaFX
     *
     * @param message The erorr message to be displayed
     */
    public void printError(String message) {
        Platform.runLater(() -> {
            var errorDialog = DialogBox.getChalkDialog("ERROR!\n" + message, chalkImage);
            errorDialog.getStyleClass().add("error-dialog");
            dialogContainer.getChildren().add(errorDialog);
        });
    }

}
