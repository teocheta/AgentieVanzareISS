module com.example.agentievanzareiss {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.persistence;


    opens com.example.agentievanzareiss to javafx.fxml;
    exports com.example.agentievanzareiss;
    opens com.example.agentievanzareiss.controller;
    opens com.example.agentievanzareiss.model;
}