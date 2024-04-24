package task.manager.taskmanager;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProgramTest {

    @Test
    void testHelp() {
        String output = Program.help();
        assertNotNull(output);
        assertTrue(output.contains("--help"));
        assertTrue(output.contains("--csv-file"));
        assertTrue(output.contains("--add-Task"));
        assertTrue(output.contains("--Task-text"));
        assertTrue(output.contains("--completed"));
        assertTrue(output.contains("--due"));
        assertTrue(output.contains("--priority"));
        assertTrue(output.contains("--category"));
        assertTrue(output.contains("--complete-Task"));
        assertTrue(output.contains("--display"));
        assertTrue(output.contains("--show-incomplete"));
        assertTrue(output.contains("--show-category"));
        assertTrue(output.contains("--sort-by-date"));
        assertTrue(output.contains("--sort-by-priority"));

    }

    @Test
    void testHelpWithCommand() {
        String output = Program.help("add-Task");
        assertTrue(output.contains("--add-Task"));
        assertTrue(output.contains("add new task"));
//        assertTrue(output.contains("Set the description of new Task"));
    }

    @Test
    void testHelpWithInvalidCommand() {
        String output = Program.help("invalid-command");
        assertTrue(output.contains("incorrect"));
    }

    @Test
    void testCorrectFormatWithCorrectCommand() {
        assertTrue(Program.correctFormat("--add-Task"));
    }

    @Test
    void testCorrectFormatWithIncorrectCommand() {
        assertFalse(Program.correctFormat("add-Task"));
    }

    @Test
    void testMultipleCommand() {
        final PrintStream standardOut = System.out;
        final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        Program.multipleCommand("--add-Task--category homework");

        assertTrue(outputStreamCaptor.toString().trim().contains("category have been saved"));
        System.setOut(standardOut);
    }


    @Test
    void testDetectCommandWithInvalidCommand() {
        // Mock System.out to capture console output
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        // Create Program object
        Program program = new Program();

        // Call the method to test with an invalid command
        program.detectCommand("--invalid-command");

        // Verify that the correct error message is printed
        String expectedOutput = "You have entered invalid input, please re-enter.\n\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    @Test
    void testDetectCommandWithAddTaskCommand() {
        Program.detectCommand("--add-Task");
    }

    @Test
    void testDetectCommandWithDisplayCommand() {
        Program.detectCommand("--display");
    }

    @Test
    void testDetectCommandWithCompletedCommand() {
        Program.detectCommand("--completed");
    }

    @Test
    void testDetectCommandWithHelpCommand() {
        Program.detectCommand("--help");
    }

    @Test
    void testDisplay() {
        Program.display("--show-incomplete--sort-by-date");
    }

    @Test
    void testRun() {
        // Prepare test input
        String simulatedUserInput = "exit\n"; // Simulate user typing "exit" to end the program
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

        // Mock System.out to capture console output
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        // Call the method to test
        Program.run();

        // Restore original System.in and System.out
        System.setIn(savedStandardInputStream);

        // Verify that the correct message is printed
        String expectedOutput = "The program have started\n" +
                "Please enter inputs: \n" +
                "--help for brief description for this program\n" +
                "enter exit to end the program\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    @Test
    void testDisplayWithInvalidCommand() {
        // Mock System.out to capture console output
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        // Call the method to test with an invalid command
        Program.display("--invalid-command");

        // Verify that the correct error message is printed
        String expectedOutput = "invalid-command is invalid inputs. Please re-enter\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    @Test
    void testRunWithExitCommand() {
        // Prepare test input
        String simulatedUserInput = "exit"; // Simulate user typing "exit" to end the program
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

        // Mock System.out to capture console output
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        // Call the method to test
        Program.run();

        // Restore original System.in and System.out
        System.setIn(savedStandardInputStream);

        // Verify that the program ends successfully
        assertTrue(outputStreamCaptor.toString().contains("The program have started"));
    }

}