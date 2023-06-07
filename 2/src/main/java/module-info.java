module com.loancalculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;

    opens com.loancalculator.Model to java.base;
    exports com.loancalculator.Model;
    opens com.loancalculator to javafx.fxml;
    exports com.loancalculator;
    exports com.loancalculator.View;
    opens com.loancalculator.View to javafx.fxml;
    exports com.loancalculator.Controller;
    opens com.loancalculator.Controller to javafx.fxml;
}