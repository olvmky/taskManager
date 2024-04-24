package task.manager.taskmanager;

/**
 * Represents a command to add a task to the task manager.
 */
public class AddTaskCommand implements Command{
    private final TaskManager taskManager;
    private final Functionality functionality;
    private final String filePath;
    private final String text;
    private boolean completed;
    private String dueDate;
    private String category;

    /**
     * Represents a command to add a task to the task manager.
     *
     * @param functionality The Functionality object that provides task management functionality.
     * @param filePath The file path to save the task list.
     * @param text The description of the task to be added.
     * @param completed The status of the task (true if completed, false otherwise).
     * @param dueDate The due date of the task.
     * @param category The category of the task.
     * @param taskManager The TaskManager object responsible for managing the tasks.
     */
    public AddTaskCommand(Functionality functionality, String filePath,
                        String text, boolean completed, String dueDate, String category,
                        TaskManager taskManager) {
        this.functionality = functionality;
        this.filePath = filePath;
        this.text = text;
        this.completed = completed;
        this.dueDate = dueDate;
        this.category = category;
        this.taskManager = taskManager;
    }

    /**
     * Executes the command to add a task to the task manager.
     * Generates a random numeric ID for the task.
     * Creates a new Task object with the generated ID, description, completion status,
     * due date, priority, and category.
     * Adds the new task to the task manager using the functionality object.
     */
    @Override
    public void execute(){
        if(text == null){
            System.out.println("Please set up the description by --Task-text before add task\n");
            return;
        }
        if(completed != true) completed = false;
        if(Program.priority == null) Program.priority = Priority.LOW;
        if(dueDate == null) dueDate = "";
        if(category == null) category = "";
        String id = taskManager.generateRandomNumericId();
        Task newTask = new Task(id, text, completed, dueDate, Program.priority, category);
        functionality.addTask(newTask, filePath);
    }
}
