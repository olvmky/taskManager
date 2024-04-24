package task.manager.taskmanager;

import java.util.*;

/**
 * Command class
 * For commands our application have and run the program
 */
public class Program {
    protected static Functionality function = new Functionality();
//    protected static CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand();
    protected static String category;
    protected static Priority priority;
    protected static String dueDate;
    protected static String description;
    protected static List<Task> tasks = new ArrayList<>();
    protected static String text;
    protected static boolean completed;
    protected static String filePath;

    /**
     * Provide a brief description of all supported command
     * @return String of what command we have
     */
    public static String help(){
        String output = "";
        output += "--help <command>: for full description of the command\n";
        output += "--csv-file <path/to/file>: for uploading csv file for tasks\n";
        output += "--add-Task: to add new task\n";
        output += "--Task-text <description of Task>: Set the description of new Task\n";
        output += "--completed: Set completed status of new Task to true\n";
        output += "--due <MONTH/DAY/YEAR>: Set due date of new task\n";
        output += "--priority <1, 2, or 3>: Set priority of a new task\n";
        output += "--category <category name>: Set category of new task\n";
        output += "--complete-Task <id>: mark task with proved id as complete\n";
        output += "--display: display tasks, if none of the optional arg provided, will display all tasks\n";
        output += "--show-incomplete: if --display is provided, only incomplete tasks displayed\n";
        output += "--show-category <category>: if --display provided, only tasks with given category displayed\n";
        output += "--sort-by-date: if --display provided, sort list of task by date in ascending\n";
        output += "--sort-by-priority: if --display provided, sort list of tasks by priority in ascending\n\n";

        output += "command options: csv-file, add-Task, Task-text, completed, due, priority, category, \n";
        output += "                 complete-Task, display, display--show-incomplete, display--show-category, \n";
        output += "                 display--sort-by-date, display--sort-by-priority\n";
        return output;
    }

    /**
     * Return the description of command depending on inputs
     * @param command The command user may have questions on
     * @return Description of the command
     */
    public static String help(String command){
        String output = "";
        if(command.equals("csv-file")) output += "--csv-file <path/to/file>: for uploading csv file for tasks\n";
        else if(command.equals("add-Task")) output += "--add-Task: to add new task\n";
        else if(command.equals("Task-text"))
            output += "--Task-text <description of Task>: Set the description of new Task\n";
        else if(command.equals("completed")) output += "--completed: Set completed status of new Task to true\n";
        else if(command.equals("due")) output += "--due <YEAR/MONTH/DAY>: Set due date of new task\n";
        else if(command.equals("priority")) output += "--priority <1, 2, or 3>: Set priority of a new task\n";
        else if(command.equals("category")) output += "--category <category name>: Set category of new task\n";
        else if(command.equals("complete-Task"))
            output += "--complete-Task <id>: mark task with proved iid as complete\n";
        else if(command.equals("display"))
            output += "--display: display tasks, if none of the optional arg provided, will display all tasks\n";
        else if(command.equals("display--show-incomplete"))
            output += "--show-incomplete: if --display is provided, only incomplete tasks displayed\n";
        else if(command.equals("display--show-category"))
            output += "--show-category <category>: if --display provided, only tasks with given category displayed\n";
        else if(command.equals("display--sort-by-date"))
            output += "--sort-by-date: if --display provided, sort list of task by date in ascending\n";
        else if(command.equals("display--sort-by-priority"))
            output += "--sort-by-priority: if --display provided, sort list of tasks by priority in ascending\n";
        else {
            output += "The command you have entered is incorrect, please re-enter\n";
            output += "enter --help for more help if needed.\n";
        }

        return output;
    }

    /**
     * Checks if the given string follows the correct format.
     *
     * @param given The string to be checked.
     * @return true if the string follows the correct format, false otherwise.
     */
    public static boolean correctFormat(String given){
        if(given.charAt(0) != '-' && given.charAt(1) != '-') return false;
        return true;
    }

    /**
     * This method is used to process multiple commands entered by the user.
     *
     * @param command The multiple commands entered by the user.
     */
    public static void multipleCommand(String command){
        int add = 0;
        String[] parts = command.split("--");
        if(parts.length < 2){
            if(command.substring(2, 10).equals("category")){
                category = command.substring(11);
                System.out.println("category have been saved");
            } else if(command.substring(2,11).equals("Task-text")){
                text = command.substring(12);
                System.out.println("The description for new task have been saved!\n");
            }
            return;
        }

        for(String i:parts) {
            i = i.trim();
            if(i.equals("add-Task")) add++;
            else if(i.length() >= 7 && !i.substring(0,7).equals("display") &&
                    i.length() >= 13 && !i.substring(0,13).equals("complete-Task")){
                System.out.println("Can only add, complete and display in one run.");
                System.out.println("Please re-enter");
            }
            if(add > 1) {
                System.out.println("Cannot add more than one Task in one run");
                return;
            }
            StringBuilder temp = new StringBuilder();
            temp.append("--");
            temp.append(i);
            if(temp.length() > 2) System.out.println(temp.toString());
            if(temp.length() > 2) detectCommand(temp.toString());
        }
    }

    /**
     * Detects the command entered by the user and performs the action.
     *
     * @param command The command entered by the user
     */
    public static void detectCommand(String command){
        TaskManager taskManager = new TaskManager();
        if(command.substring(2,6).equals("help")){
            if(command.length() <= 7) System.out.println(help());
            else System.out.println(help(command.substring(7)));
        } else if(command.substring(2, 9).equals("display")){
            if(command.length() <= 10)
                function.displayTasks(false, null,
                        false, false, filePath);
            else display(command.substring(9));
        } else if(command != null && command.substring(2).equals("add-Task")){
            AddTaskCommand addTask = new AddTaskCommand(function, filePath, text, completed,
                    dueDate, category, taskManager);
            addTask.execute();
        } else if(command.substring(2).equals("completed")) {
            completed = true;
            System.out.println("Completed status have been set to true\n");
        } else if(command.substring(2, 5).equals("due")){
            dueDate = command.substring(6);
            System.out.println("The due date have been saved for new task.\n");
        } else if(command.substring(2, 10).equals("csv-file")){
            filePath = command.substring(11);
            tasks = function.readTasksFromCSV(command.substring(11));
            for(Task i:tasks){
                System.out.println(function.taskToCsv(i));
            }
            System.out.println();
        } else if(command.substring(2, 10).equals("category")){
            category = command.substring(11);
            System.out.println("category have been saved\n");
        } else if(command.substring(2,11).equals("Task-text")){
            text = command.substring(12);
            System.out.println("The description for new task have been saved!\n");
        }  else if(command.substring(2, 10).equals("priority")){
            PriorityCommand priorityCommand = new PriorityCommand(command, function, priority);
            priorityCommand.execute();
        } else if(command.length() <= 21 && command.substring(2,15).equals("complete-Task")){
            task.manager.taskmanager.CompleteTaskCommand completeCommand =
                    new task.manager.taskmanager.CompleteTaskCommand(function, command.substring(16), filePath);
            completeCommand.execute();
        } else if(command.length() >= 15 && (command.substring(2, 10).equals("complete") ||
                command.substring(2, 5).equals("add") || command.substring(2, 9).equals("display"))) {
            multipleCommand(command);
        } else{
            System.out.println("You have entered invalid input, please re-enter.\n");
        }
    }

    /**
     * Displays tasks based on specified filters and sorting options.
     *
     * @param command The command entered by the user to specify filters and sorting options.
     *                Filter options:
     *                  - "show-incomplete" to include only incomplete tasks
     *                  - "show-category <category>" to filter tasks by category
     *                Sorting options:
     *                  - "sort-by-date" to sort tasks by date (ascending)
     *                  - "sort-by-priority" to sort tasks by priority (ascending)
     * @return A list of tasks after applying the specified filters and sorting options.
     */
    public static void display(String command){
        if(command.isEmpty()){
            System.out.println("Please enter valid input");
            return;
        }
        boolean incomplete = false;
        boolean sortByDate = false;
        boolean sortByPriority = false;
        String category = null;
        String[] parts = command.split("--");
        for(String i:parts) {
            i = i.trim();
            if(i.equals("") || i == null) continue;
            if(i.equals("show-incomplete")) {
                incomplete = true;
                System.out.println("incomplete have been enabled to true");
            }
            else if(i.equals("sort-by-date")) {
                sortByDate = true;
                System.out.println("sort by date have been enabled to true");
            }
            else if(i.length() >= 13 && i.substring(0, 13).equals("show-category")) {
                category = i.substring(14);
                System.out.println("category have been set to " + category);
            }
            else if(i.equals("sort-by-priority")) sortByPriority = true;
            else{
                System.out.println(i + " is invalid inputs. Please re-enter");
                return;
            }
        }
        function.displayTasks(incomplete, category, sortByDate, sortByPriority, filePath);
    }

    /**
     * Runs the program and interacts with the user through the console.
     */
    public static void run(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("The program have started");
        while(true) {
            System.out.println("Please enter inputs: ");
            System.out.println("--help for brief description for this program");
            System.out.println("enter exit to end the program");
            String command = scanner.nextLine();
            if(command == null || command.length() <= 0) {
                System.out.println("Please enter valid input\n");
                continue;
            } else if(command.equals("exit")) break;
            if(!correctFormat(command)){
                System.out.println("You have entered invalid character, please re-enter");
                System.out.println("For more help, please enter --help.\n");
                continue;
            }
            detectCommand(command);
        }
    }

    /**
     * The main method of the program.
     * This method starts the program and prompts the user for inputs. It continuously takes inputs and performs the corresponding actions until the user enters "exit" to end the
     * program.
     * Before processing a command, it checks if the command follows the correct format and prompts the user to re-enter if it's invalid.
     *
     * @param args The command-line arguments passed to the program.
     */
    public static void main(String[] args){
        run();
    }

}
