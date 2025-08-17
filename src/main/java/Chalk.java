
import java.util.ArrayList;
import java.util.Scanner;

public class Chalk {

    private static final String NAME = "Chalk";

    private static final String LINE_BREAK = "-------------------------------";

    private static final String END_CONVERSATION = "bye";
    private static final String LIST_TASKS = "list";

    public static void main(String[] args) {

        Chalk.say("Hello! I'm " + Chalk.NAME);
        Chalk.say("What can I do for you?");
        System.out.println(Chalk.LINE_BREAK);

        Scanner scanner = new Scanner(System.in);
        String userInput;
        ArrayList<String> storedInputs = new ArrayList<>();

        while (true) {
            userInput = scanner.nextLine();

            if (userInput.equals(END_CONVERSATION)) {
                break;
            } else if (userInput.equals(LIST_TASKS)) {
                System.out.println(Chalk.LINE_BREAK);
                for (int i = 0; i < storedInputs.size(); i++) {
                    Chalk.say((i + 1) + ". " + storedInputs.get(i));
                }
                System.out.println(Chalk.LINE_BREAK);
                continue;
            }

            System.out.println(Chalk.LINE_BREAK);
            Chalk.say("added: " + userInput);
            System.out.println(Chalk.LINE_BREAK);
            storedInputs.add(userInput);
        }

        System.out.println(Chalk.LINE_BREAK);
        Chalk.say("Bye. Hope to see you again soon!");
        System.out.println(Chalk.LINE_BREAK);
        scanner.close();
    }

    private static void say(String sentenceString) {
        System.out.println("    " + sentenceString);
    }
}
