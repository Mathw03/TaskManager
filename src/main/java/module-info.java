module com.mathieu.taskmanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires spring.security.core;

    opens com.mathieu.taskmanager to javafx.fxml;
    exports com.mathieu.taskmanager;
}