package task.manager.taskmanager;

import java.util.*;
import java.io.*;

/**
 * Provides core functionalities for task management including adding, completing tasks,
 * and managing task persistence through CSV files.
 */
public class Functionality{
    protected static String path;
    protected static List<Task> tasks = new ArrayList<>();
    protected static final String CSV_HEADER = "id,text,completed,due,priority,category";

    /**
     * Adds a task to the task manager and updates the CSV file.
     *
     * @param task The task to be added.
     * @param filePath The file path of the CSV file.
     */
    public void addTask(Task task, String filePath){
        if(filePath == null) {
            System.out.println("Error, the file path had not been defined!");
            return;
        }
        try (FileWriter writer = new FileWriter(filePath, true);
        BufferedWriter bw = new BufferedWriter(writer);
        PrintWriter out = new PrintWriter(bw)) {
            if (!new File(filePath).exists()) {
                out.println(CSV_HEADER);
            }
            out.println(tripleSpaceToComma(commaToSpecial(taskToCsv(task))));
            System.out.println("Task added successfully!");
            tasks.add(task);
            System.out.println(taskToCsv(task));
        } catch (IOException e) {
            System.out.println("Error occurred while adding task: " + e.getMessage());
        }
    }


    /**
     * Completes a task by updating its status in the CSV file.
     *
     * @param taskId The ID of the task to complete.
     * @param path The file path of the CSV file.
     */
    public void completeTask(String taskId, String path){
        if (taskId == null || taskId.isEmpty()) {
            System.out.println("Error: Task ID is null or empty.");
            return;
        }
        try {
            Functionality.path = path;
            // Read the existing CSV content into memory
            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }

            // Update the status of the task with the specified ID
            for (int i = 1; i < lines.size(); i++) { // Start from index 1 to skip the header line
                String[] parts = lines.get(i).split(",");
                long currentId = Long.parseLong(parts[0]);
                if (parts.length >= 2 && currentId == Long.parseLong(taskId)) { // Check if the ID matches
                    parts[2] = String.valueOf(true); // Update the status
                }
                lines.set(i, String.join(",", parts));
            }
            // Write the modified content back to the CSV file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            }

            System.out.println("Task status updated successfully!\n");

        } catch (IOException e) {
            System.out.println("Error occurred while updating task status: " + e.getMessage());
        }
    }

    /**
     * Display tasks based on specified filters and sorting options.
     * Two filter argu can be combine but only one sort can be applied at a time.
     *
     * @param includeIncomplete Whether to include only incomplete tasks.
     * @param category The category to filter tasks by (null for no filtering).
     * @param sortByDate Whether to sort tasks by date (true for ascending, false for descending).
     * @param sortByPriority Whether to sort tasks by priority (true for ascending, false for descending).
     * @param filePath The path to the CSV file containing the tasks.
     * @return A list of tasks after applying the sorting.
     */
    public List<Task> displayTasks(boolean includeIncomplete, String category,
                            boolean sortByDate, boolean sortByPriority, String filePath) {
        if(filePath == null || filePath.isEmpty()){
            System.out.println("File path is empty");
            return new ArrayList<>();
        }
        if(sortByDate && sortByPriority){
            System.out.println("Error! Cannot sort by date and priority simulatneously.");
            System.out.println("Please re-enter how you want to sort the list. ");
            return new ArrayList<>();
        }
        
        List<Task> tasks = readTasksFromCSV(filePath); // Read tasks from CSV file

        // Apply filters
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            if ((!includeIncomplete || !task.isCompleted()) && (category == null ||
                    category.equals(task.getCategory()))) {
                filteredTasks.add(task);
            }
        }

        // Apply sorting
        if(sortByDate) {
            Collections.sort(filteredTasks, (t1, t2) -> {
                String dueDate1 = t1.getDue();
                String dueDate2 = t2.getDue();
                if (dueDate1 == null || dueDate1.isEmpty()) {
                    return dueDate2 == null || dueDate2.isEmpty() ? 0 : 1;
                } else if (dueDate2 == null || dueDate2.isEmpty()) {
                    return -1;
                } else {
                    return dueDate1.compareTo(dueDate2);
                }
            });
        } else if (sortByPriority) {
            Collections.sort(filteredTasks, (t1, t2) -> {
                Priority p1 = t1.getPriority();
                Priority p2 = t2.getPriority();
                // Compare priorities based on their ordinal values
                return Integer.compare(p1.ordinal(), p2.ordinal());
            });
        }
        if(filteredTasks.size() == 0) System.out.println("All tasks have been completed");
        // Display tasks
        for (Task task : filteredTasks) {
            System.out.println(specialToComma(taskToCsv(task)));
        }
        System.out.println();
        return filteredTasks;
    }

    /**
     * Reads the contents of a file and returns a list of String arrays.
     *
     * @param filePath The path of the file to read.
     * @return A list of String arrays containing the file contents.
     */
    public List<String[]> readFileContents(String filePath){
        this.path = filePath;
        List<String[]> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) { // Skip the header line
                    firstLine = false;
                    continue;
                }
                line = commaToTripleSpace(line);
                line = specialToComma(line);
                String[] parts = line.split("   ");
                if(parts.length < 6) {
                    String[] copy = new String[6];
                    int j = 0;
                    for(String i:parts){
                        copy[j++] = i;
                    }
                    parts = copy;
                }
                list.add(parts);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath + "\n" + e.getMessage());
        }
        return list;
    }

    /**
     * Read from csv and return a list of Task objects
     *
     * @param path Path of csv file
     * @return what the csv file contains
     */
    public List<Task> readTasksFromCSV(String path) {
        this.path = path;
        List<Task> tasks = new ArrayList<>();
        List<String[]> parts = readFileContents(path);
        Priority priority = null;
        for(String[] i:parts) {
            if (i[4] == null || i[4].equals("")) priority = Priority.LOW;
            else priority = Priority.valueOf(i[4]);
            Task task = new Task(i[0], i[1] == null ? "null":i[1], i[2] == null ? false : Boolean.parseBoolean(i[2]),
                    i[3] == null ? null : i[3],
                    priority, i[5] == null ? null : i[5]);
            tasks.add(task);
        }
        return tasks;
    }

    /**
     * Converts an integer number to a Priority enum value.
     *
     * @param num The number to be converted(1, 2, and 3).
     * @return The corresponding Priority enum value. Returns null if the number is invalid.
     */
    public Priority convertToPriorityNum(int num){
        Priority res = null;
        if(num == 1) res = Priority.LOW;
        else if(num == 2) res = Priority.MEDIUM;
        else if(num == 3) res = Priority.HIGH;
        return res;
    }

    /**
     * Converts a string of a priority to a Priority enum value.
     *
     * @param val The string representation of the priority ("low", "medium", or "high").
     * @return The corresponding Priority enum value. Returns null if the string is not a valid priority.
     */
    public Priority convertToPriorityAlpha(String val){
        Priority res = null;
        val = val.toLowerCase();
        if(val.equals("low")) res = Priority.LOW;
        else if(val.equals("medium") || val.equals("med")) res = Priority.MEDIUM;
        else if(val.equals("high")) res = Priority.HIGH;
        return res;
    }

    /**
     * Self implemented method for converting task into a String
     *
     * @param task the task we need
     * @return String of task and its information
     */
    public String taskToCsv(Task task) {
        return task.getId() + "   "
                + task.getText() + "   "
                + task.isCompleted() + "   "
                + (task.getDue() != null ? task.getDue() : "") + "   "
                + (task.getPriority() != null ? task.getPriority() : "") + "   "
                + (task.getCategory() != null ? task.getCategory() : "");
    }

    /**
     * Replaces all occurrences of the comma character (",") in a string with a special character ("�").
     *
     * @param str The input string.
     * @return A new string with all commas replaced by the special character.
     */
    String commaToSpecial(String str) {
        return str.replace(",", "�");
    }

    /**
     * Replaces all occurrences of commas in a string with triple spaces.
     *
     * @param str The input string.
     * @return The modified string with commas replaced by triple spaces.
     */
    String commaToTripleSpace(String str){
        return str.replace(",", "   ");
    }

    /**
     * Replaces triple spaces in a given string with a comma.
     *
     * @param str The string to be processed.
     * @return The string with triple spaces replaced by a comma.
     */
    String tripleSpaceToComma(String str){
        return str.replace("   ", ",");
    }

    /**
     * Replaces special character "�" with a comma in the given string.
     *
     * @param str The string to be processed.
     * @return The string with "�" replaced by a comma.
     */
    String specialToComma(String str){
        return str.replace("�", ",");
    }
}
