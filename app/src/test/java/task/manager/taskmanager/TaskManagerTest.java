package task.manager.taskmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class TaskManagerTest {

    @Test
    public void testGenerateRandomNumericId() throws NoSuchFieldException, IllegalAccessException {
        // Instantiate TaskManager
        TaskManager taskManager = new TaskManager();

        // Generate a set of unique IDs
        Set<String> generatedIds = new HashSet<>();
        int expectedSize = 10;
        while (generatedIds.size() < expectedSize) {
            String id = taskManager.generateRandomNumericId();
            Assertions.assertFalse(generatedIds.contains(id));
            generatedIds.add(id);
        }

        // Reflectively access the set of IDs in TaskManager
        Field generatedIdsField = TaskManager.class.getDeclaredField("generatedIds");
        generatedIdsField.setAccessible(true);
        Set<String> taskManagerGeneratedIds = (Set<String>) generatedIdsField.get(taskManager);

        // Check the size of the set, it should be equal to the expected size
        Assertions.assertEquals(taskManagerGeneratedIds.size(), generatedIds.size());

        // Check that the set in the taskManager matches the set we generated
        Assertions.assertEquals(generatedIds, taskManagerGeneratedIds);
    }

}
