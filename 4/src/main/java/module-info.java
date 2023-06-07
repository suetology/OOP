module com.lab4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.apache.pdfbox;

    opens com.lab4 to javafx.fxml;
    exports com.lab4;
    exports com.lab4.FXControllers;
    opens com.lab4.FXControllers to javafx.fxml;
    exports com.lab4.University;
    opens com.lab4.University to javafx.fxml;
    exports com.lab4.Util;
    opens com.lab4.Util to javafx.fxml;
}