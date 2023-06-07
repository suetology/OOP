module com.minecraftwithcommands {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.antlr.antlr4.runtime;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires eu.hansolo.tilesfx;
    requires org.lwjgl.glfw;
    requires org.joml;
    requires org.lwjgl.opengl;
    requires org.lwjgl.stb;

    opens com.minecraftwithcommands.Engine to javafx.graphics;
    opens com.minecraftwithcommands.App to javafx.fxml;
    opens com.minecraftwithcommands to javafx.fxml;
    exports com.minecraftwithcommands;
}