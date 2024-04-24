
package task.manager.taskmanager;
import java.util.*;

/**
 * Represents a task with text, completion status, due date, category, priority, and ID.
 */
public class Task {
    public static final String manager = null;
    protected String text;
    protected boolean completed; // false by fault
    protected String due;
    final String category;  //Optional field, can be "Preme", "ASW", "Grocery"
    protected Priority priority;
    final String id;
    private static Set<String> generatedIds = new HashSet<>();

    /**
     * Creates a new Task object with the given parameters.
     *
     * @param id The ID of the task.
     * @param text The text/description of the task.
     * @param completed The completion status of the task.
     * @param due The due date of the task.
     * @param priority The priority of the task.
     * @param category The category of the task.
     */
    public Task(String id, String text, boolean completed, String due, Priority priority, String category){
        validateText(text); // Validate text
        this.text = text;
        this.completed = completed;
        this.due = due == null ? "":due;
        this.priority = priority == null? Priority.LOW : priority;
        this.category = category == null ? "":category;
        this.id = id;
    }

    /**
     * Generates a random 5-digit numeric ID that is unique among all generated IDs.
     * The generated ID is formatted as a string.
     *
     * @return A unique 5-digit numeric ID as a string.
     */
    private String generateRandomNumericId() {
        Random random = new Random();
        String numericId;
        do {
            numericId = String.format("%05d", random.nextInt(10000)); // Generate 10-digit numeric ID
        } while (!generatedIds.add(numericId)); // Check uniqueness and add to set
        generatedIds.add(numericId);
        return numericId;
    }

    /**
     * Retrieves the text/description of the task.
     *
     * @return The text/description of the task.
     */
    public String getText() {
        return this.text;
    }

    /**
     * Sets the completed status of the task.
     *
     * @param completed The boolean value indicating whether the task is completed or not.
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Returns the completion status of the task.
     *
     * @return The completion status of the task.
     */
    public boolean isCompleted() {
        return this.completed;
    }

    /**
     * Gets the due date of the task.
     *
     * @return The due date of the task as a string.
     */
    public String getDue() {
        return this.due;
    }

    /**
     * Returns the category of the task.
     *
     * @return The category of the task.
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * Gets the ID of the task.
     *
     * @return The ID of the task.
     */
    public String getId() {
        return this.id;
    } 
    
    /**
     * Gets the priority of the task.
     *
     * @return The priority of the task.
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Validates the given text by checking if it is null or empty after removing leading and trailing white spaces.
     * Throws an IllegalArgumentException if the text is null or empty.
     *
     * @param text The text to be validated.
     * @throws IllegalArgumentException If the text is null or empty.
     */
    private void validateText(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be empty");
        }
    }
}
