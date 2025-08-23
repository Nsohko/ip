package ui;

public class Ui {

    private static String lineBreak = "-------------------------------";

    public void reply(String sentenceString) {

        System.out.println(Ui.lineBreak);

        // split each line based on new line character, then prepend 4 spaces
        // not the best solution, because it doesnt account for when long lines wrap around
        // but it will do for now
        for (String line : sentenceString.split("\n")) {
            System.out.println("    " + line);
        }

        System.out.println(Ui.lineBreak);
    }

    public void printError(String sentenceString) {

        System.out.println(Ui.lineBreak);
        System.out.println("    ERROR!");
        for (String line : sentenceString.split("\n")) {
            System.out.println("    " + line);
        }
        System.out.println(Ui.lineBreak);
    }
    
}
