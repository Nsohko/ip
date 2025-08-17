
import java.util.Scanner;

public class Chalk {

    private static final String NAME = "Chalk";

    private static final String END_CONVERSATION = "bye";
    private static final String LIST_TASKS = "list";

    private TaskList taskList;

    public Chalk() {
        this.taskList = new TaskList();
    }

    public void initialize() {
        String message = "Hello! I'm "
                + Chalk.NAME + "\n"
                + "What can I do for you?";

        this.say(message);
    }

    public void terminate() {
        String message = "Bye. Hope to see you again soon!";
        this.say(message);
    }

    public void say(String sentenceString) {

        String lineBreak = "-------------------------------";
        System.out.println(lineBreak);

        // split each line based on new line character, then prepend 4 spaces
        // not the best solution, because it doesnt account for when long lines wrap around
        // but it will do for now
        for (String line : sentenceString.split("\n")) {
            System.out.println("    " + line);
        }

        System.out.println(lineBreak);
    }

    public void say() {
        this.say(this.taskList.toString());
    }

    public void addTask(String taskName) {
        this.say("added: " + taskName);
        this.taskList.addTask(taskName);
    }

    public static void main(String[] args) {

        Chalk chalk = new Chalk();

        chalk.initialize();

        Scanner scanner = new Scanner(System.in);
        String userInput;

        while (true) {
            userInput = scanner.nextLine();

            if (userInput.equals(END_CONVERSATION)) {
                break;
            } else if (userInput.equals(LIST_TASKS)) {
                chalk.say();
                continue;
            }

            chalk.addTask(userInput);
        }
        chalk.terminate();
    }

}
