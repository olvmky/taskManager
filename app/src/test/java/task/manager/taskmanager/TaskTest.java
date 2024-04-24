/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package task.manager.taskmanager;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    public void testTaskCreation() {
        Task task1 = new Task("1", "Fix Bug 1234 in ZIP code validator", false, "2023/22/08",
                Priority.HIGH, "Preme");
        Task task2 = new Task("2", "Create Mock-up for new View in Grocery Mobile App", true,
                null, Priority.MEDIUM, "Grocery");
        Task task3 = new Task("3", "Organize Code Review with ASW On-line and Marketing teams", true, null, Priority.LOW,
                "ASW");

        assertEquals("Fix Bug 1234 in ZIP code validator", task1.getText());
        assertTrue(task2.isCompleted());
        assertEquals(Priority.LOW, task3.getPriority());
        assertEquals("ASW", task3.getCategory());
    }

    @Test
    public void testTaskProperties() {
        Task task = new Task("1", "Fix Bug 1234 in ZIP code validator", false, "2023/22/08",
                Priority.HIGH, "Preme");

        assertEquals("2023/22/08", task.getDue());
        assertEquals(Priority.HIGH, task.getPriority());
        assertEquals("Preme", task.getCategory());
        assertNotNull(task.getId());

        task.setCompleted(true);
        assertTrue(task.isCompleted());
    }

    @Test
    public void testEmptyText() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Task("1", "", false, "2023/22/08", Priority.HIGH, "Preme");
        });
    }

    @Test
    public void testGetText() {
        Task task = new Task("1", "Fix Bug 1234 in ZIP code validator", false, "2023/22/08",
                Priority.HIGH, "Preme");
        assertEquals("Fix Bug 1234 in ZIP code validator", task.getText());
    }

    @Test
    public void testIsCompleted() {
        Task task = new Task("1", "Fix Bug 1234 in ZIP code validator", false, "2023/22/08",
                Priority.HIGH, "Preme");
        assertFalse(task.isCompleted());
    }

    @Test
    public void testSetCompleted() {
        Task task = new Task("1", "Fix Bug 1234 in ZIP code validator", false, "2023/22/08",
                Priority.HIGH, "Preme");
        task.setCompleted(true);
        assertTrue(task.isCompleted());
    }

    @Test
    public void testGetDue() {
        Task task = new Task("1","Fix Bug 1234 in ZIP code validator", false, "2023/22/08",
                Priority.HIGH, "Preme");
        assertEquals("2023/22/08", task.getDue());
    }

    @Test
    public void testGetCategory() {
        Task task = new Task("1","Fix Bug 1234 in ZIP code validator", false, "2023/22/08",
                Priority.HIGH, "Preme");
        assertEquals("Preme", task.getCategory());
    }

    @Test
    public void testGetId() {
        Task task = new Task("1","Fix Bug 1234 in ZIP code validator", false, "2023/22/08",
                Priority.HIGH, "Preme");
        assertNotNull(task.getId());
    }

    @Test
    public void testGetPriority() {
        Task task = new Task("1","Fix Bug 1234 in ZIP code validator", false, "2023/22/08",
                Priority.HIGH, "Preme");
        assertEquals(Priority.HIGH, task.getPriority());
    }

    @Test
    public void testTaskCreationWithNullDueDate() {
        Task task = new Task("1", "Fix Bug 1234 in ZIP code validator", false, null, Priority.HIGH, "Preme");
        assertEquals("", task.getDue()); // Due date should default to empty string if null
    }

    @Test
    public void testTaskCreationWithNullPriority() {
        Task task = new Task("1", "Fix Bug 1234 in ZIP code validator", false, "2023/22/08", null, "Preme");
        assertEquals(Priority.LOW, task.getPriority()); // Priority should default to LOW if null
    }

    @Test
    public void testTaskCreationWithNullCategory() {
        Task task = new Task("1", "Fix Bug 1234 in ZIP code validator", false, "2023/22/08", Priority.HIGH, null);
        assertEquals("", task.getCategory()); // Category should default to empty string if null
    }


}
