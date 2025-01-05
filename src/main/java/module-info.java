module tereroxy.toylanguage {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.desktop;

    opens view.GUI to javafx.fxml;
    exports view.GUI;
}