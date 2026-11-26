package com.example.week8;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListenerDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        Person person = new Person();

        // 1. InvalidationListener (Fires when the property becomes "invalid")
        person.nameProperty().addListener((observable) -> {
            System.out.println("1. [InvalidationListener] Name was changed!");
        });

        // 2. ChangeListener (Provides old and new values)
        person.nameProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("2. [ChangeListener] Name changed from '"
                    + oldValue + "' to '" + newValue + "'");
        });

        // Trigger the changes
        System.out.println("--- Setting name to Alice ---");
        person.setName("Alice");

        System.out.println("\n--- Setting name to Bob ---");
        person.setName("Bob");

        System.out.println("\n--- Setting name to Bob (same value) ---");
        person.setName("Bob"); // ChangeListener will NOT fire here!

        // Just so the window stays open
        VBox root = new VBox(new Label("Check the Output window below!"));
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");
        primaryStage.setTitle("Week 8 - ListenerDemo");
        primaryStage.setScene(new Scene(root, 400, 150));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}