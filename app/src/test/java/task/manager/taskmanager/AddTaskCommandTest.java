package task.manager.taskmanager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddTaskCommandTest {

    // Reusing FunctionalityStub from the CompleteTaskCommandTest
    static class FunctionalityStub extends Functionality {
        Task addedTask = null;
        String filePathUsed = null;

        @Override
        public void addTask(Task task, String filePath) {
            this.addedTask = task;
            this.filePathUsed = filePath;
        }
    }

    // TaskManagerStub to simulate ID generation
    static class TaskManagerStub extends TaskManager {
        @Override
        public String generateRandomNumericId() {
            // Return a predefined ID to verify correct usage
            return "test123";
        }
    }

    @Test
    public void testExecuteCreatesAndAddsTaskCorrectly() {
        FunctionalityStub functionalityStub = new FunctionalityStub();
        TaskManagerStub taskManagerStub = new TaskManagerStub();
        String filePath = "dummy/path";
        String text = "Task text";
        boolean completed = true;
        String dueDate = "2024-01-01";
        String category = "Test Category";

        AddTaskCommand command = new AddTaskCommand(functionalityStub, filePath, text, completed, dueDate, category, taskManagerStub);
        command.execute();

        // Verify that addTask was correctly called with a task
        assertTrue(functionalityStub.addedTask != null, "addTask should be called with a task");
        // Check that the task has the predefined ID 'test123'
        assertEquals("test123", functionalityStub.addedTask.getId(), "The task should have the ID 'test123'");
        // Ensure that the task text matches the expected value
        assertEquals(text, functionalityStub.addedTask.getText(), "The task text should match");
        // Verify that the completed status of the task matches the expected value
        assertEquals(completed, functionalityStub.addedTask.isCompleted(), "The task completed status should match");
        // Confirm that the task due date matches the expected value
        assertEquals(dueDate, functionalityStub.addedTask.getDue(), "The task due date should match");
        // Verify that the task category matches the expected value
        assertEquals(category, functionalityStub.addedTask.getCategory(), "The task category should match");
        // Ensure the file path used matches the expected value
        assertEquals(filePath, functionalityStub.filePathUsed, "The file path should match");
    }
}
