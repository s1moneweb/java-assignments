
import javax.swing.*;
import java.awt.*;

public class RegistrationForm extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JComboBox<String> genderBox;
    private JComboBox<String> courseBox;

    public RegistrationForm() {

        setTitle("Registration Form");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Heading
        JLabel heading = new JLabel("Student Registration Form");
        heading.setFont(new Font("Arial", Font.BOLD, 22));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(heading, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Full Name
        addLabel(panel, gbc, "Full Name:", 1);
        nameField = new JTextField(20);
        addComponent(panel, gbc, nameField, 1);

        // Email
        addLabel(panel, gbc, "Email:", 2);
        emailField = new JTextField(20);
        addComponent(panel, gbc, emailField, 2);

        // Phone
        addLabel(panel, gbc, "Phone:", 3);
        phoneField = new JTextField(20);
        addComponent(panel, gbc, phoneField, 3);

        // Gender
        addLabel(panel, gbc, "Gender:", 4);
        String[] genders = {"Select Gender", "Male", "Female", "Other"};
        genderBox = new JComboBox<>(genders);
        addComponent(panel, gbc, genderBox, 4);

        // Course
        addLabel(panel, gbc, "Course:", 5);
        String[] courses = {
            "Select Course",
            "Computer Science",
            "Information Technology",
            "Data Science"
        };
        courseBox = new JComboBox<>(courses);
        addComponent(panel, gbc, courseBox, 5);

        // Password
        addLabel(panel, gbc, "Password:", 6);
        passwordField = new JPasswordField(20);
        addComponent(panel, gbc, passwordField, 6);

        // Confirm Password
        addLabel(panel, gbc, "Confirm Password:", 7);
        confirmPasswordField = new JPasswordField(20);
        addComponent(panel, gbc, confirmPasswordField, 7);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton registerButton = new JButton("Register");
        JButton clearButton = new JButton("Clear");

        buttonPanel.add(registerButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        // Button actions
        registerButton.addActionListener(e -> register());
        clearButton.addActionListener(e -> clearForm());

        add(panel);
        setVisible(true);
    }

    private void addLabel(JPanel panel, GridBagConstraints gbc,
                          String text, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(text), gbc);
    }

    private void addComponent(JPanel panel, GridBagConstraints gbc,
                              JComponent component, int row) {
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(component, gbc);
    }

    private void register() {

        String name = nameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword =
                new String(confirmPasswordField.getPassword());

        if (name.isEmpty() || email.isEmpty() ||
            password.isEmpty() || confirmPassword.isEmpty()) {

            JOptionPane.showMessageDialog(
                this, "Please fill in all required fields."
            );
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(
                this, "Passwords do not match."
            );
            return;
        }

        JOptionPane.showMessageDialog(
            this, "Registration successful!"
        );
    }

    private void clearForm() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        genderBox.setSelectedIndex(0);
        courseBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistrationForm());
    }
}
