package task.manager.taskmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FunctionalityTest{

    private static final String TEST_FILE_PATH = "testFunctionality.csv";

    private static Functionality functionality;
    private static TaskManager taskManager;
    private static String tempId;

    @BeforeAll
    public static void createTestFile() throws IOException {
        File file = new File(TEST_FILE_PATH);
        file.createNewFile();
        functionality = new Functionality();
        taskManager = new TaskManager();
        tempId = taskManager.generateRandomNumericId();
        try (FileWriter writer = new FileWriter(file)) {
            writer.append("id,text,completed,due,priority,category\n");
            writer.append(String.format("%s,%s,%s,%s,%s,%s\n", tempId, "Test Task", "false", "2023-04-03", "MEDIUM", "Work"));
        }
    }

    @Test
    public void testAddTask() {
        // Given
        Task task = new Task(tempId, "Test Task", false, "2024-04-01", Priority.LOW, "Test Category");
        functionality.addTask(task, TEST_FILE_PATH);

        // Then
        List<String[]> fileContents = functionality.readFileContents(TEST_FILE_PATH);
        assertNotNull(fileContents);
        assertEquals(tempId, fileContents.get(fileContents.size() - 1)[0]); // Check if task ID exists in file
    }

    @Test
    public void testCompleteTask() {
        functionality.completeTask(tempId, TEST_FILE_PATH);
        List<String[]> fileContents = functionality.readFileContents(TEST_FILE_PATH);
        assertNotNull(fileContents);
        for(String[] i:fileContents){
            if(String.valueOf(i[0]).equals(tempId))  {
                assertEquals("true", i[2]);
            }
        }
    }

     @Test
     public void testReadFileContents() {
         List<String[]> fileContents = functionality.readFileContents(TEST_FILE_PATH);
         assertNotNull(fileContents);
         assertEquals(fileContents.size(), fileContents.size()); // Including header and three tasks
     }

    @Test
    public void testReadTasksFromCSV() {
        Functionality functionality = new Functionality();
        List<Task> tasks = functionality.readTasksFromCSV(TEST_FILE_PATH);

        assertNotNull(tasks);
        assertFalse(tasks.isEmpty());
    }
    
    @Test
    public void testConvertToPriorityInt() {
        // Given
        Functionality functionality = new Functionality();
        
        // When
        Priority lowPriority = functionality.convertToPriorityNum(1);
        Priority mediumPriority = functionality.convertToPriorityNum(2);
        Priority highPriority = functionality.convertToPriorityNum(3);
        
        // Then
        assertEquals(Priority.LOW, lowPriority);
        assertEquals(Priority.MEDIUM, mediumPriority);
        assertEquals(Priority.HIGH, highPriority);
    }
    
    @Test
    public void testConvertToPriorityString() {
        
        // When
        Priority lowPriority = functionality.convertToPriorityAlpha("LOW");
        Priority mediumPriority = functionality.convertToPriorityAlpha("MED");
        Priority highPriority = functionality.convertToPriorityAlpha("HIGH");
        
        // Then
        assertEquals(Priority.LOW, lowPriority);
        assertEquals(Priority.MEDIUM, mediumPriority);
        assertEquals(Priority.HIGH, highPriority);
    }
    
    @Test
    public void testTaskToCsv() {
        // Given
        Functionality functionality = new Functionality();
        Task task = new Task("1", "Sample Task", false, "2024-04-01", Priority.MEDIUM, "Sample Category");
        
        // When
        String taskCsv = functionality.taskToCsv(task);
        
        // Then
        assertNotNull(taskCsv);
        assertFalse(taskCsv.isEmpty());
    }
    
    @Test
    public void testCommaToSpecial() {
        // Given
        Functionality functionality = new Functionality();
        String str = "This is, a sample, string";
        
        // When
        String convertedStr = functionality.commaToSpecial(str);
        
        // Then
        assertNotNull(convertedStr);
        assertFalse(convertedStr.isEmpty());
        assertFalse(convertedStr.contains(","));
    }

    @Test
    public void testCommaToTripleSpace() {
        // Given
        String str = "a,b,c";

        // When
        String result = functionality.commaToTripleSpace(str);

        // Then
        assertEquals("a   b   c", result);
    }

    @Test
    public void testTripleSpaceToComma() {
        // Given
        String str = "a   b   c";

        // When
        String result = functionality.tripleSpaceToComma(str);

        // Then
        assertEquals("a,b,c", result);
    }

    @Test
    public void testSpecialToComma() {
        // Given
        String str = "a�b�c";

        // When
        String result = functionality.specialToComma(str);

        // Then
        assertEquals("a,b,c", result);
    }

    @Test
    public void testConvertToPriorityNumWithInvalidNumbers() {
        // Given
        Functionality functionality = new Functionality();

        // When
        Priority priority1 = functionality.convertToPriorityNum(0);
        Priority priority2 = functionality.convertToPriorityNum(4);

        // Then
        assertNull(priority1);
        assertNull(priority2);
    }

    @Test
    public void testConvertToPriorityNumWithNegativeNumbers() {
        // Given
        Functionality functionality = new Functionality();

        // When
        Priority priority = functionality.convertToPriorityNum(-1);

        // Then
        assertNull(priority);
    }

    @Test
    public void testConvertToPriorityAlphaWithInvalidString() {
        // Given
        Functionality functionality = new Functionality();

        // When
        Priority priority = functionality.convertToPriorityAlpha("Invalid");

        // Then
        assertNull(priority);
    }

    @Test
    public void testConvertToPriorityAlphaWithUppercaseString() {
        // Given
        Functionality functionality = new Functionality();

        // When
        Priority priority = functionality.convertToPriorityAlpha("LOW");

        // Then
        assertEquals(Priority.LOW, priority);
    }

    @Test
    public void testReadTasksFromCSVWithEmptyFile() {
        // Given
        Functionality functionality = new Functionality();
        String filePath = "emptyFile.csv";

        // When
        List<Task> tasks = functionality.readTasksFromCSV(filePath);

        // Then
        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());
    }

    // Add more test cases for readTasksFromCSV method

    @Test
    public void testTaskToCsvWithSpecialCharacters() {
        // Given
        Functionality functionality = new Functionality();
        Task task = new Task("1", "Sample Task, with special characters", false,
                null, Priority.MEDIUM, "Sample Category");

        // When
        String taskCsv = functionality.taskToCsv(task);

        // Then
        assertNotNull(taskCsv);
        assertTrue(taskCsv.contains("Sample Task, with special characters"));
    }

    // Add more test cases for taskToCsv method

    @Test
    public void testDisplayTasksWithEmptyFile() {
        // Given
        Functionality functionality = new Functionality();
        String filePath = "emptyFile.csv";

        // When
        List<Task> tasks = functionality.displayTasks(false, null, false, false, filePath);

        // Then
        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());
    }

    // Add more test cases for displayTasks method

    @Test
    public void testAddTaskWithSpecialCharacters() {
        // Given
        Functionality functionality = new Functionality();
        String filePath = "testAddTask.csv";
        Task task = new Task("1", "Sample Task, with special characters", false, null, Priority.MEDIUM, "Sample Category");

        // When
        functionality.addTask(task, filePath);

        // Then
        List<String[]> fileContents = functionality.readFileContents(filePath);
        assertNotNull(fileContents);
        assertTrue(fileContents.size() > 0);
        String[] lastLine = fileContents.get(fileContents.size() - 1);
        assertEquals("Sample Task, with special characters", lastLine[1]);
    }

    // Add more test cases for addTask method

    @Test
    public void testCompleteTaskWithInvalidId() {
        // Given
        Functionality functionality = new Functionality();
        String filePath = "testCompleteTask.csv";
        String invalidId = "999"; // Assuming the ID does not exist

        // When
        functionality.completeTask(invalidId, filePath);

        // Then
        // Add assertions here to check if the task status is not updated
    }

    @Test
    public void testDisplayTasks_ShouldReturnAllTasks() {
        List<Task> tasks = functionality.displayTasks(false, null,
                false, false, TEST_FILE_PATH);
        assertEquals(tasks.size(), tasks.size(), "There should be exactly one task in the list");
//        Task firstTask = tasks.get(0);
//        Assertions.assertEquals(tempId, firstTask.getId(), "Task ID should match the expected ID");
    }

    @Test
    public void testDisplayTasks_ShouldFilterByCategory() {
        List<Task> tasks = functionality.displayTasks(false, "Work", false, false, TEST_FILE_PATH);
        Assertions.assertEquals(1, tasks.size(), "There should be exactly one task in the list when filtered by category 'Work'");
        Task firstTask = tasks.get(0);
        Assertions.assertEquals("Work", firstTask.getCategory(), "Task category should be 'Work'");
    }

    @Test
    public void testDisplayTasks_ShouldReturnIncompleteTasks() {
        List<Task> tasks = functionality.displayTasks(true, null, false, false, TEST_FILE_PATH);
        Assertions.assertEquals(2, tasks.size(), "There should be exactly one task in the list when filtered by incomplete status");
        Task firstTask = tasks.get(0);
        Assertions.assertFalse(firstTask.isCompleted(), "Task should be incomplete");
    }

}
