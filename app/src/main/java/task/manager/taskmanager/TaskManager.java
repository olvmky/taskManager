package task.manager.taskmanager;

import java.util.*;

/**
 * The TaskManager class provides functionality for generating unique numeric IDs for tasks.
 * It ensures that each generated ID is unique. The generated IDs are stored in a Set to ensure
 * uniqueness.
 */
public class TaskManager {
    private static Set<String> generatedIds = new HashSet<>();

    /**
     * Generate a unique numeric ID for the task.
     * @return A unique numeric ID
     */
    public String generateRandomNumericId() {
        Random random = new Random();
        String numericId;
        do {
            numericId = String.format("%05d", random.nextInt(10000)); // Generate 10-digit numeric ID
        } while (!generatedIds.add(numericId)); // Check uniqueness and add to set
        generatedIds.add(numericId);
        return numericId;
    }
}
