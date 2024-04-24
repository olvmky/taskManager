package task.manager.taskmanager;

import task.manager.taskmanager.Command;
import task.manager.taskmanager.Functionality;

/**
 * The CompleteTaskCommand class represents a command that completes a task.
 * It implements the Command interface.
 */
public class CompleteTaskCommand implements Command {
    private final Functionality functionality;
    private final String taskId;
    private final String filePath;

    /**
     * Creates a new CompleteTaskCommand.
     *
     * @param functionality The functionality to use for completing the task.
     * @param taskId The ID of the task.
     * @param filePath The file path where the completed task will be saved.
     */
    public CompleteTaskCommand(Functionality functionality, String taskId, String filePath) {
        this.functionality = functionality;
        this.taskId = taskId;
        this.filePath = filePath;
    }

    /**
     * The execute() method is used to execute the CompleteTaskCommand by calling the completeTask() method of the Functionality class.
     * This method updates the status of a task based on the task id and updates the CSV file at the same time.
     */
    @Override
    public void execute() {
        functionality.completeTask(taskId, filePath);
    }
}
