package com.mycompany.week6;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label title = new Label("WEEK 6 - JavaFX Demos");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label subtitle = new Label("Pick one to open");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #cbd5e1;");

        Button btnDashboard = styledButton("📊  Open Dashboard", "#3b82f6");
        Button btnLogin = styledButton("🔐  Open Login", "#10b981");
        Button btnFxml = styledButton("🖼️  Open FXML App", "#f59e0b");
        Button btnSystem = styledButton("💻  Open System Info", "#8b5cf6");
        Button btnClickMe = styledButton("👋  Open My First App (Click Me)", "#ec4899");

        btnDashboard.setOnAction(e -> openApp(new DashboardApp()));

        // LOGIN - built in code, no fxml
        btnLogin.setOnAction(e -> openLoginWindow("Login"));

        // FXML APP - same login but different title, also built in code
        btnFxml.setOnAction(e -> openLoginWindow("JavaFX Login"));

        // SYSTEM INFO - built in code, no other file needed
        btnSystem.setOnAction(e -> {
            Stage s = new Stage();
            Label t = new Label("System Info");
            t.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
            Label info = new Label(
                "OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version") + "\n" +
                "User: " + System.getProperty("user.name") + "\n" +
                "Java: " + System.getProperty("java.version") + "\n" +
                "JavaFX: " + System.getProperty("javafx.version")
            );
            info.setStyle("-fx-font-size: 14px;");
            VBox box = new VBox(15, t, info);
            box.setAlignment(Pos.CENTER);
            box.setPadding(new Insets(30));
            box.setStyle("-fx-background-color: #f0f0f0;");
            s.setTitle("System Info");
            s.setScene(new Scene(box, 400, 300));
            s.show();
        });

        // YOUR ORIGINAL CLICK ME APP
        btnClickMe.setOnAction(e -> {
            Stage s = new Stage();
            Label titleLabel = new Label("My First JavaFX App");
            Button clickButton = new Button("Click Me!");
            clickButton.setOnAction(ev -> {
                titleLabel.setText("Button was clicked!");
                System.out.println("Button clicked on: " + Thread.currentThread().getName());
            });
            VBox root = new VBox(15, titleLabel, clickButton);
            root.setStyle("-fx-alignment: center; -fx-padding: 20px; -fx-background-color: #f0f0f0;");
            s.setTitle("JavaFX Programmatic App");
            s.setScene(new Scene(root, 400, 300));
            s.show();
        });

        VBox menu = new VBox(12, title, subtitle, btnDashboard, btnLogin, btnFxml, btnSystem, btnClickMe);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(30));
        menu.setStyle("-fx-background-color: linear-gradient(to bottom, #0f172a, #1e293b);");

        primaryStage.setTitle("Week 6 - Main Menu");
        primaryStage.setScene(new Scene(menu, 420, 480));
        primaryStage.show();
    }

    private void openLoginWindow(String windowTitle) {
        Stage s = new Stage();
        Label titleL = new Label("Login");
        titleL.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TextField user = new TextField();
        user.setPromptText("Username");
        user.setPrefWidth(300);

        PasswordField pass = new PasswordField();
        pass.setPromptText("Password");
        pass.setPrefWidth(300);

        Button loginBtn = new Button("Login");
        loginBtn.setPrefWidth(300);
        loginBtn.setStyle("-fx-background-color: #3b82f6; -fx-text-fill: white; -fx-font-weight: bold;");

        Label hint = new Label("Try: admin / password123");
        hint.setStyle("-fx-text-fill: #64748b; -fx-font-size: 12px;");

        loginBtn.setOnAction(ev -> {
            String u = user.getText();
            String p = pass.getText();
            Alert alert;
            if (u.isEmpty() || p.isEmpty()) {
                alert = new Alert(Alert.AlertType.WARNING, "Fields cannot be empty.");
            } else if (u.equals("admin") && p.equals("password123")) {
                alert = new Alert(Alert.AlertType.INFORMATION, "Welcome, Admin!");
            } else {
                alert = new Alert(Alert.AlertType.ERROR, "Invalid username or password.");
            }
            alert.showAndWait();
        });

        VBox box = new VBox(15, titleL, user, pass, loginBtn, hint);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(30));
        box.setStyle("-fx-background-color: #f0f0f0;");
        s.setTitle(windowTitle);
        s.setScene(new Scene(box, 400, 350));
        s.show();
    }

    private Button styledButton(String text, String color) {
        Button b = new Button(text);
        b.setPrefWidth(340);
        b.setPrefHeight(45);
        b.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 12;");
        return b;
    }

    private void openApp(Application app) {
        try {
            Stage s = new Stage();
            app.start(s);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}