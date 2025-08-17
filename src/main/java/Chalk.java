
import java.util.ArrayList;
import java.util.Scanner;

public class Chalk {

    private static final String NAME = "Chalk";

    private static final String LINE_BREAK = "-------------------------------";

    private static final String END_CONVERSATION = "bye";
    private static final String LIST_TASKS = "list";

    public static void main(String[] args) {

        System.out.println("Hello! I'm " + Chalk.NAME);
        System.out.println("What can I do for you?");
        System.out.println(Chalk.LINE_BREAK);

        Scanner scanner = new Scanner(System.in);
        String userInput;
        ArrayList<String> storedInputs = new ArrayList<>();

        while (true) {
            userInput = scanner.nextLine();
            System.out.println(Chalk.LINE_BREAK);

            if (userInput.equals(END_CONVERSATION)) {
                break;
            } else if (userInput.equals(LIST_TASKS)) {
                for (int i = 0; i < storedInputs.size(); i++) {
                    System.out.println((i + 1) + ". " + storedInputs.get(i));
                }
                System.out.println(Chalk.LINE_BREAK);
                continue;
            }

            System.out.println("added: " + userInput);
            storedInputs.add(userInput);
            System.out.println(Chalk.LINE_BREAK);
        }
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(Chalk.LINE_BREAK);
        scanner.close();
    }
}
