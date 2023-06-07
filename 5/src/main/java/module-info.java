module com.lab5_remastered {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.json;

    opens com.lab5_remastered to javafx.fxml;
    exports com.lab5_remastered;
    exports com.lab5_remastered.ClientSide;
    opens com.lab5_remastered.ClientSide to javafx.fxml;
    exports com.lab5_remastered.ServerSide;
    opens com.lab5_remastered.ServerSide to javafx.fxml;
}