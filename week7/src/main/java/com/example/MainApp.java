package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        TabPane tabs = new TabPane();

        // ---------- TAB 1: PROPAGATION ----------
        VBox propBox = new VBox(10);
        propBox.setStyle("-fx-padding: 20; -fx-alignment: center;");
        TextArea propLog = new TextArea();
        propLog.setPrefHeight(150);
        Button clickBtn = new Button("Click Me!");
        propBox.getChildren().addAll(new Label("1. Event Propagation"), clickBtn, propLog);

        // Capture phase - filter on root
        propBox.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            propLog.appendText("1. [FILTER] Root intercepted!\n");
        });
        // Target
        clickBtn.setOnAction(e -> propLog.appendText("2. [HANDLER] Button clicked!\n"));
        // Bubble phase
        propBox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            propLog.appendText("3. [HANDLER] Root saw bubble!\n");
        });
        tabs.getTabs().add(new Tab("Propagation", propBox));

        // ---------- TAB 2: MOUSE + KEY ----------
        Pane mousePane = new Pane();
        mousePane.setPrefSize(400, 250);
        Label mouseLabel = new Label("Mouse: Move over here | Use Arrow Keys");
        mouseLabel.setLayoutX(10); mouseLabel.setLayoutY(10);
        Circle circle = new Circle(200, 130, 30, Color.DODGERBLUE);
        mousePane.getChildren().addAll(mouseLabel, circle);
        mousePane.setFocusTraversable(true);
        mousePane.setOnMouseMoved(e ->
            mouseLabel.setText("Mouse: x=" + (int)e.getX() + ", y=" + (int)e.getY() + " | Use Arrow Keys"));
        mousePane.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP -> circle.setCenterY(circle.getCenterY() - 10);
                case DOWN -> circle.setCenterY(circle.getCenterY() + 10);
                case LEFT -> circle.setCenterX(circle.getCenterX() - 10);
                case RIGHT -> circle.setCenterX(circle.getCenterX() + 10);
                default -> {}
            }
        });
        tabs.getTabs().add(new Tab("Mouse + Key", mousePane));

        // ---------- TAB 3: DRAG & DROP ----------
        Pane dragPane = new Pane();
        dragPane.setPrefSize(400, 250);
        Label dragLabel = new Label("Drag the BLUE circle to the RED target");
        dragLabel.setLayoutX(10); dragLabel.setLayoutY(10);
        Circle dragCircle = new Circle(80, 130, 30, Color.DODGERBLUE);
        Circle target = new Circle(320, 130, 45, Color.LIGHTCORAL);
        target.setStroke(Color.RED);
        dragPane.getChildren().addAll(dragLabel, target, dragCircle);

        dragCircle.setOnDragDetected(e -> {
            Dragboard db = dragCircle.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString("circle");
            db.setContent(content);
            e.consume();
        });
        target.setOnDragOver(e -> {
            if (e.getDragboard().hasString()) e.acceptTransferModes(TransferMode.MOVE);
            e.consume();
        });
        target.setOnDragDropped(e -> {
            dragCircle.setCenterX(target.getCenterX());
            dragCircle.setCenterY(target.getCenterY());
            dragLabel.setText("Dropped! Drag & Drop worked!");
            e.setDropCompleted(true);
            e.consume();
        });
        tabs.getTabs().add(new Tab("Drag & Drop", dragPane));

        // ---------- TAB 4: CUSTOM EVENT ----------
        VBox customBox = new VBox(15);
        customBox.setStyle("-fx-padding: 20; -fx-alignment: center;");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        Button loginBtn = new Button("Login");
        Label statusLabel = new Label("Please log in.");
        customBox.getChildren().addAll(new Label("4. Custom Event"), usernameField, loginBtn, statusLabel);

        loginBtn.setOnAction(e -> {
            String username = usernameField.getText();
            if (!username.isEmpty()) {
                customBox.fireEvent(new LoginSuccessEvent(username));
            } else {
                statusLabel.setText("Username cannot be empty.");
            }
        });
        customBox.addEventHandler(LoginSuccessEvent.LOGIN_SUCCESS, event -> {
            System.out.println("Custom Event Received! User logged in: " + event.getUsername());
            statusLabel.setText("Welcome back, " + event.getUsername() + "!");
            usernameField.clear();
        });
        tabs.getTabs().add(new Tab("Custom Event", customBox));

        tabs.getTabs().forEach(t -> t.setClosable(false));
        Scene scene = new Scene(tabs, 450, 320);
        primaryStage.setTitle("Week 7 - All Demos In One");
        primaryStage.setScene(scene);
        primaryStage.show();

        // So arrow keys work on Tab 2
        mousePane.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}