package com.university;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoadStudentTask extends Task<ObservableList<Student>> {
    @Override
    protected ObservableList<Student> call() throws Exception {
        ObservableList<Student> students = FXCollections.observableArrayList();
        String sql = "SELECT * FROM students";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                students.add(new Student(
                    rs.getInt("student_id"),
                    rs.getString("registration_number"),
                    rs.getString("first_name"),
                    rs.getString("last_name")
                ));
            }
        }
        return students;
    }
}