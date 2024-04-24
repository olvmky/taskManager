package task.manager.taskmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PriorityCommandTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void testExecuteWithNumericPriority() {
        Functionality functionality = new Functionality();
        PriorityCommand priorityCommand = new PriorityCommand("--priority 2", functionality, null);

        priorityCommand.execute();

        Assertions.assertEquals("The priority have been saved\n", outputStreamCaptor.toString());
    }

    @Test
    public void testExecuteWithAlphabeticPriority() {
        Functionality functionality = new Functionality();
        PriorityCommand priorityCommand = new PriorityCommand("--priority med", functionality, null);

        priorityCommand.execute();

        Assertions.assertEquals("The priority have been saved\n", outputStreamCaptor.toString());
    }

    @Test
    public void testExecuteWithInvalidPriority() {
        Functionality functionality = new Functionality();
        PriorityCommand priorityCommand = new PriorityCommand("priority ABC", functionality, null);

        priorityCommand.execute();

        Assertions.assertEquals("Please enter number 1 - 3, or letters LOW, MED, and HIGH\n\n",
                outputStreamCaptor.toString());
    }
}
