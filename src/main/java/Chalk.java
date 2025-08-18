
import java.util.Scanner;
import tasks.Task;
import tasks.TaskList;

public class Chalk {

    private static final String NAME = "Chalk";

    private static final String END_CONVERSATION = "bye";
    private static final String LIST_TASKS = "list";

    // matches string that starts with "mark " and has one or more digits after
    // also allows for negative input (although this is obviously erroneous input, but it is handled by the markAsDone method)
    private static final String MARK_TASK_AS_DONE = "mark -?\\d+";

    // matches string that starts with "unmark " and has one or more digits after
    // also allows for negative input (although this is obviously erroneous input, but it is handled by the unmarkAsDone method)
    private static final String UNMARK_TASK_AS_DONE = "unmark -?\\d+";

    private final TaskList taskList;

    public Chalk() {
        this.taskList = new TaskList();
    }

    public void initialize() {
        String message = """
            Hello! I'm %s
            What can I do for you?
            """.formatted(Chalk.NAME);

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

    public void listTaks() {
        this.say(this.taskList.toString());
    }

    public void addTask(String command) {
        String message;
        try {
            Task newTask = Task.fromCommand(command);
            this.taskList.addTask(newTask);

            message = """
                Got it. I've added this task:
                    %s
                Now you have %d tasks in the list.
                """.formatted(newTask.toString(), this.taskList.size());

        } catch (IllegalArgumentException e) {
            message = e.getMessage();
        }
        
        this.say(message);
    }

    public void markAsDone(String command) {
        // taskNumber is 1-indexed
        int taskNumber = Integer.parseInt(command.split(" ")[1]);

        String message;
        try {
            Task task = this.taskList.markAsDone(taskNumber);
            message = """
                Nice! I've marked this task as done:
                    %s
                """.formatted(task.toString());
            
        } catch (IndexOutOfBoundsException e) {
            message =  "Error! There is no task with that number!";
        }
        this.say(message);
    }

    public void markAsUndone(String command) {
        // taskNumber is 1-indexed
        int taskNumber = Integer.parseInt(command.split(" ")[1]);
        String message;
        try {
            Task task = this.taskList.unmarkAsDone(taskNumber);
            message = """
                OK, I've marked this task as not done yet:
                    %s
                """.formatted(task.toString());
        } catch (IndexOutOfBoundsException e) {
            message =  "Error! There is no task with that number!";
        }

        this.say(message);
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
                chalk.listTaks();
            } else if (userInput.matches(MARK_TASK_AS_DONE)) {
                chalk.markAsDone(userInput);
            } else if (userInput.matches(UNMARK_TASK_AS_DONE)) {
                chalk.markAsUndone(userInput);
            } else {
                chalk.addTask(userInput);
            }
        }
        chalk.terminate();
    }

}
