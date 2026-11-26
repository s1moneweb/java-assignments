module com.mycompany.week6 {
    requires javafx.controls;
    requires javafx.fxml;
    opens com.mycompany.week6 to javafx.fxml;
    exports com.mycompany.week6;
}