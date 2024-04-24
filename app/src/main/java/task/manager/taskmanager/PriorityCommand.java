package task.manager.taskmanager;

/**
 * The PriorityCommand class represents a command with priority.
 * It implements the Command interface and provides the logic for executing the command.
 */
public class PriorityCommand implements Command{
    private final String command;
    private final Functionality function;
    private Priority priority;

    /**
     * Constructs a PriorityCommand with the specified command string, functionality,
     * and initial priority setting.
     *
     * @param command The command string.
     * @param function The Functionality instance used for converting input into Priority enums.
     * @param priority The initial priority level, which may be adjusted based on the command string.
     */
    public PriorityCommand(String command, Functionality function, Priority priority) {
        this.command = command;
        this.function = function;
        this.priority = priority;
    }

    /**
     * Executes the priority command.
     * Checks the input to determine the priority value and sets it.
     * If the input is invalid, it displays an error message.
     * Otherwise, it displays a success message.
     */
    @Override
    public void execute() {
        String prior = command.substring(11);
        if(prior.matches("^[a-zA-Z]+$")) priority = function.convertToPriorityAlpha(prior);
        else if(Integer.parseInt(prior) <= 3 && Integer.parseInt(prior) > 0){
            priority = function.convertToPriorityNum(Integer.parseInt(prior));
        }
        if(priority == null) {
            System.out.println("Please enter number 1 - 3, or letters LOW, MED, and HIGH\n");
            return;
        }
        System.out.print("The priority have been saved\n");
    }
}
