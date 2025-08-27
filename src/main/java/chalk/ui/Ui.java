package chalk.ui;

public class Ui {

    /**
     * Line Break used to wrap Chalk's outputs
     */
    private static final String LINE_BREAK = "-------------------------------";

    /**
     * Prints out a message with appropriate formatting
     *
     * @param sentenceString The message to be printed out
     */
    public void reply(String sentenceString) {

        System.out.println(Ui.LINE_BREAK);

        // split each line based on new line character, then prepend 4 spaces
        // not the best solution, because it doesnt account for when long lines wrap around
        // but it will do for now
        for (String line : sentenceString.split("\n")) {
            System.out.println("    " + line);
        }

        System.out.println(Ui.LINE_BREAK);
    }

    /**
     * Prints out an error with appropriate formatting
     *
     * @param sentenceString The erorr message to be printed out
     */
    public void printError(String sentenceString) {

        System.out.println(Ui.LINE_BREAK);
        System.out.println("    ERROR!");
        if (sentenceString != null) {
            for (String line : sentenceString.split("\n")) {
                System.out.println("    " + line);
            }
        }
        System.out.println(Ui.LINE_BREAK);
    }

}
