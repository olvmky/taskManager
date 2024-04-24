package task.manager.taskmanager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import java.util.List;


/**
 * The TaskManagerApp class is a GUI application for managing tasks.
 */
public class TaskManagerApp extends JFrame {
    private JLabel filePathOutput;
    private JTable table;
    private JTextField addNewField;
    private JTextField categoryField;
    private JTextField dueDateField;
    private JCheckBox completeBox;
    private JButton chooseFile;
    private JButton addNewButton;
    private JButton updateButton;
    private JComboBox<String> categoryBox;
    private JComboBox<String> comboBox;
    private JComboBox<String> sortingBox;
    private JComboBox<String> priorityBox;
    private JComboBox<String> taskBox;

    private Functionality functionality;
    private TaskManager taskManager;

    private boolean includeIncomplete;
    private boolean sortByDate;
    private boolean sortByPriority;

    private Priority priority = null;
    private boolean completed = false;
    private String category = "";
    private String filePath = "";
    private String dueDate = "";
    private String sortByCategory = "";

    /**
     * This method initializes the Task Manager application.
     * It sets up the user interface components, such as panels, labels, buttons, and tables.
     * It also adds event listeners and handles user input.
     */
    public TaskManagerApp() {
        functionality = new Functionality();
        taskManager = new TaskManager();

        setTitle("Task Manager");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        bottomPanel.setLayout(new FlowLayout());

        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout());

        JLabel inputLabel = new JLabel("File Path:");
        filePathOutput = new JLabel();
        filePathOutput.setPreferredSize(new Dimension(200, 20));
        filePathOutput.setFont(new Font("Arial", Font.PLAIN, 12));
        inputLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        filePathOutput.setBackground(UIManager.getColor("Panel.background")); // Match background color
        JScrollPane filePathScrollPane = new JScrollPane(filePathOutput);

        chooseFile = new JButton("Choose");
        chooseFile.setFont(new Font("Arial", Font.PLAIN, 12));
        inputLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel addNewLabel = new JLabel("Add New");
        JLabel categoryLabel = new JLabel("Category");
        JLabel dueDateLabel = new JLabel("Due Date");
        addNewButton = new JButton("Add");
        addNewField = new JTextField(6);
        categoryField = new JTextField(4);
        dueDateField = new JTextField(5);
        addNewLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        dueDateLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        addNewButton.setFont(new Font("Arial", Font.PLAIN, 12));
        addNewButton.setPreferredSize(new Dimension(addNewButton.getText().length() * 20, 25));

        comboBox = new JComboBox<>();
        JLabel idLabel = new JLabel("Update by id");
        idLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        updateButton = new JButton("Update");
        updateButton.setFont(new Font("Arial", Font.PLAIN, 12));

        priorityBox = new JComboBox<>();
        String[] priorityLevel = new String[]{"Priority", "LOW", "MEDIUM", "HIGH"};
        for(String i:priorityLevel) {
            priorityBox.addItem(i);
        }

        sortingBox = new JComboBox<>();
        sortingBox.addItem("Regular");
        sortingBox.addItem("Due Date");
        sortingBox.addItem("Priority");
        JLabel sortLabel = new JLabel("Sort by");
        completeBox = new JCheckBox("incomplete");

        taskBox = new JComboBox<>();
        String[] taskStatus = new String[]{"Task", "Finish", "Not yet"};
        for(String i:taskStatus){
            taskBox.addItem(i);
        }

        categoryBox = new JComboBox<>();

        //Adjusting size of taskBox
        int maxWidth = 0;
        FontMetrics fontMetrics = taskBox.getFontMetrics(taskBox.getFont());
        for (int i = 0; i < taskBox.getItemCount(); i++) {
            int width = fontMetrics.stringWidth(taskBox.getItemAt(i));
            maxWidth = Math.max(maxWidth, width);
        }
        maxWidth += 30;
        Dimension preferredSize = taskBox.getPreferredSize();
        preferredSize.width = maxWidth;
        taskBox.setPreferredSize(preferredSize);

        //Adjusting size of priorityBox
        int maxWidthPriority = 0;
        FontMetrics fontPriorityMetrics = priorityBox.getFontMetrics(priorityBox.getFont());
        for (int i = 0; i < priorityBox.getItemCount(); i++) {
            int width = fontPriorityMetrics.stringWidth(priorityBox.getItemAt(i));
            maxWidthPriority = Math.max(maxWidthPriority, width);
        }
        maxWidthPriority += 38;
        preferredSize = priorityBox.getPreferredSize();
        preferredSize.width = maxWidthPriority;
        priorityBox.setPreferredSize(preferredSize);

        //Adjust size of filterBox
        maxWidth = 0;
        fontMetrics = sortingBox.getFontMetrics(taskBox.getFont());
        for (int i = 0; i < sortingBox.getItemCount(); i++) {
            int width = fontMetrics.stringWidth(sortingBox.getItemAt(i));
            maxWidth = Math.max(maxWidth, width);
        }
        maxWidth += 38;
        preferredSize = sortingBox.getPreferredSize();
        preferredSize.width = maxWidth;
        sortingBox.setPreferredSize(preferredSize);

        taskBox.setFont(new Font("Arial", Font.PLAIN, 12));
        priorityBox.setFont(new Font("Arial", Font.PLAIN, 12));
        sortingBox.setFont(new Font("Arial", Font.PLAIN, 12));
        completeBox.setFont(new Font("Arial", Font.PLAIN, 12));
        sortLabel.setFont(new Font("Arial", Font.PLAIN, 12));


        String[] columnNames = {"ID", "Description", "Completed", "Due Date", "Priority", "Category"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        Font headerFont = table.getTableHeader().getFont();
        Font largerHeaderFont = headerFont.deriveFont(headerFont.getSize() + 2.0f); // Increase font size by 2 points
        table.getTableHeader().setFont(largerHeaderFont);

        JScrollPane scrollPane = new JScrollPane(table);

        initComponents();

        topPanel.add(inputLabel);
        topPanel.add(filePathScrollPane);
        topPanel.add(chooseFile);

        bottomPanel.add(addNewLabel);
        bottomPanel.add(addNewField);
        bottomPanel.add(priorityBox);
        bottomPanel.add(taskBox);
        bottomPanel.add(categoryLabel);
        bottomPanel.add(categoryField);
        bottomPanel.add(dueDateLabel);
        bottomPanel.add(dueDateField);
        bottomPanel.add(addNewButton);

        topPanel.add(sortLabel);
        topPanel.add(sortingBox);
        topPanel.add(completeBox);
        topPanel.add(categoryBox);

        bottomPanel.add(idLabel);
        bottomPanel.add(comboBox);
        bottomPanel.add(updateButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * The entry point of the application.
     *
     * @param args The command-line arguments passed to the program.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TaskManagerApp app = new TaskManagerApp();
                app.setVisible(true);
            }
        });
    }

    /**
     * Initializes the components of the Task Manager application.
     * Sets up the user interface components and adds event listeners.
     */
    //  Controller
    private void initComponents(){
        chooseFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int result = fileChooser.showOpenDialog(TaskManagerApp.this);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    filePath = selectedFile.getAbsolutePath();
                    readFileCommand();
                    filePathOutput.setText(filePath);
                }
            }
        });

        addNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String categoryText = categoryField.getText();
                String command = addNewField.getText();
                String due = dueDateField.getText();
                categoryTextCommand(categoryText);
                dueDateCommand(due);
                addDescriptionAndAddTask(command);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String command = (String) comboBox.getSelectedItem();
                completeTaskByCommand(command);
            }
        });

        priorityBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String priorityChoose = (String)priorityBox.getSelectedItem();
                priorityPickCommand(priorityChoose);
            }
        });

        completeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean incomplete = completeBox.isSelected();
                handleDisplayCompleteCommand(incomplete);
            }
        });

        sortingBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = (String) sortingBox.getSelectedItem();
                displayCommand(command);
            }
        });

        taskBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = (String)taskBox.getSelectedItem();
                handleTaskCommand(command);
            }
        });

        categoryBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = (String)categoryBox.getSelectedItem();
                handleCategorySorting(command);
            }
        });
    }

    /**
     * Set command to dueDate variable
     * @param dueDate Date
     */
    private void dueDateCommand(String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Handles the sorting of tasks by category based on the given command.
     *
     * @param command The command for sorting tasks by category.
     */
    private void handleCategorySorting(String command) {
        if(command == null) return;
        if(command.equals("Category"))  sortByCategory = null;
        else if(command.equals("Non-category")) sortByCategory = "";
        else{
            sortByCategory = command;
        }
        handleDisplayCommand();
    }

    /**
     * Sets the category for the text command.
     *
     * @param categoryText The category text to set.
     */
    private void categoryTextCommand(String categoryText) {
        if(categoryText.isEmpty()) return;
        category = categoryText;
    }

    /**
     * Handles the given task command.
     *
     * @param command The command to handle.
     */
    private void handleTaskCommand(String command) {
        if(command.equals("Finish")){
            completed = true;
        }
    }

    /**
     * Handles the display complete command.
     *
     * @param command Boolean value indicating whether to include incomplete tasks in the display.
     */
    private void handleDisplayCompleteCommand(boolean command) {
        if(filePath.isEmpty()) return;
        includeIncomplete = command;
        handleDisplayCommand();
    }

    /**
     * Completes a task based on the given command. It updates the status
     * of the task in the CSV file and then reads the file command.
     *
     * @param command The command for completing a task.
     */
    private void completeTaskByCommand(String command) {
        if (command == null || command.isEmpty()) {
            return;
        }
        functionality.completeTask(command, filePath);
        readFileCommand();
    }

    /**
     * Reads the contents of a file and updates the user interface accordingly.
     */
    private void readFileCommand() {
        handleReadFileCommand();
        displayIDs();
        displayCategory();
    }

    /**
     * Adds a description to a task and adds the task to the task manager.
     *
     * @param command The command containing the task description.
     */
    private void addDescriptionAndAddTask(String command){
        if(filePath.isEmpty()){
            return;
        }
        String text = command;
        handleAddTaskCommand(text);
        displayIDs();
        displayCategory();
    }

    /**
     * Displays the command and updates the sorting accordingly.
     *
     * @param command The command to handle.
     */
    private void displayCommand(String command){
        if(command.equals("Due Date")){
            sortByDate = true;
            sortByPriority = false;
        } else if(command.equals("Priority")){
            sortByPriority = true;
            sortByDate = false;
        } else if(command.equals("Regular")){
            sortByDate = false;
            sortByPriority = false;
        }
        handleDisplayCommand();
    }

    /**
     * Sets the priority of a task based on the given command.
     *
     * @param command The command specifying the priority of the task.
     */
    private void priorityPickCommand(String command){
        if(command.equals("MEDIUM")){
            priority = Priority.MEDIUM;
        } else if(command.equals("HIGH")){
            priority = Priority.HIGH;
        }
    }

    /**
     * Reads the contents of a file and updates the user interface accordingly.
     */
    private void handleReadFileCommand() {
        if(filePath.isEmpty()) return;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        List<String[]> lines = functionality.readFileContents(filePath);
        for (String[] i:lines) {
            model.addRow(new Object[]{i[0], i[1], i[2], i[3], i[4], i[5]});
        }
    }

    /**
     * Handles the command to add a new task.
     *
     * @param text The text of the task to be added.
     */
    private void handleAddTaskCommand(String text) {
        if(priority == null) priority = Priority.LOW;

        String id = taskManager.generateRandomNumericId();
        Task newTask = new Task(id, text, completed, dueDate, priority, category);
        functionality.addTask(newTask, filePath);
        handleReadFileCommand();
    }

    /**
     * Handles the display command and updates the table based on the specified sorting and filtering.
     */
    private void handleDisplayCommand() {
        if(includeIncomplete != true) includeIncomplete = false;
        if(sortByDate != true) sortByDate = false;
        if(sortByPriority != true) sortByPriority = false;

        List<Task> list = functionality.displayTasks(includeIncomplete, sortByCategory,
                sortByDate, sortByPriority, filePath);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Task i:list) {
            model.addRow(new Object[]{i.getId(), i.getText(), i.isCompleted(), i.getDue(),
                    i.getPriority(), i.getCategory()});
        }

        if(sortByDate == true) sortByDate = false;
        if(sortByPriority == true) sortByPriority = false;

    }

    /**
     * Displays the IDs of the items in the file on the user interface.
     */
    private void displayIDs() {
        if (filePath.isEmpty()) return;
        List<String[]> lines = functionality.readFileContents(filePath);
        comboBox.removeAllItems();
        for (String[] i : lines) {
            comboBox.addItem(i[0]);
        }
    }

    /**
     * Displays the category values in the user interface.
     * It reads the contents of a file and updates the category dropdown list accordingly.
     * It clears the existing items in the category dropdown list and adds new category values.
     * The category values are sorted in ascending order before adding them to the dropdown list.
     * If the file path is empty, the method does nothing.
     */
    private void displayCategory(){
        if(filePath.isEmpty()) return;
        Set<String> uniqueCategory = new HashSet<>();
        List<String[]> lines = functionality.readFileContents(filePath);
        categoryBox.removeAllItems();
        categoryBox.addItem("Category");
        categoryBox.addItem("Non-category");
        for(String[] i:lines) {
            if(i[5] == null) continue;
            uniqueCategory.add(i[5]);
        }
        List<String> list = new ArrayList<>(uniqueCategory);
        uniqueCategory.clear();
        Collections.sort(list);
        for(String i:list) categoryBox.addItem(i);
    }


}

