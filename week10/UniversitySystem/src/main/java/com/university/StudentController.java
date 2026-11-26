package com.university;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.concurrent.Task;
import java.sql.*;

public class StudentController {

    @FXML private TextField regField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private Button saveButton;
    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> colReg;
    @FXML private TableColumn<Student, String> colFirst;
    @FXML private TableColumn<Student, String> colLast;
    @FXML private Label statusLabel;

    private ObservableList<Student> studentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colReg.setCellValueFactory(new PropertyValueFactory<>("registration_number"));
        colFirst.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLast.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        studentTable.setItems(studentList);
        loadStudents();
    }

    private void loadStudents() {
        statusLabel.setText("Loading students...");
        LoadStudentTask task = new LoadStudentTask();
        task.setOnSucceeded(e -> {
            studentList.setAll(task.getValue());
            statusLabel.setText("Loaded " + studentList.size() + " students");
        });
        task.setOnFailed(e -> {
            statusLabel.setText("Failed to load: " + task.getException().getMessage());
        });
        new Thread(task).start();
    }

    @FXML
    public void handleSaveStudent() {
        String reg = regField.getText().trim();
        String first = firstNameField.getText().trim();
        String last = lastNameField.getText().trim();

        if (reg.isEmpty() || first.isEmpty() || last.isEmpty()) {
            statusLabel.setText("Please fill all fields");
            return;
        }

        Task<Void> saveTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                try (Connection conn = DatabaseUtil.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO students (registration_number, first_name, last_name) VALUES (?, ?, ?)")) {
                    stmt.setString(1, reg);
                    stmt.setString(2, first);
                    stmt.setString(3, last);
                    stmt.executeUpdate();
                }
                return null;
            }
        };

        saveTask.setOnSucceeded(e -> {
            statusLabel.setText("Student saved!");
            regField.clear(); firstNameField.clear(); lastNameField.clear();
            loadStudents();
        });
        saveTask.setOnFailed(e -> {
            statusLabel.setText("Save failed: " + saveTask.getException().getMessage());
        });
        new Thread(saveTask).start();
    }
}