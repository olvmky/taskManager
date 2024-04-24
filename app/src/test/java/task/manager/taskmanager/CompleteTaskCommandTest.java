package task.manager.taskmanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class CompleteTaskCommandTest {

    private Functionality functionality;
    private String taskId;
    private String filePath;
    private CompleteTaskCommand completeTaskCommand;

    @BeforeEach
    public void setUp() {
        functionality = mock(Functionality.class);
        taskId = "123";
        filePath = "/path/to/file";
        completeTaskCommand = new CompleteTaskCommand(functionality, taskId, filePath);
    }

    @Test
    public void testExecute() {
        completeTaskCommand.execute();
        verify(functionality, times(1)).completeTask(taskId, filePath);
    }
}
