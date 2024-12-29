package view.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        // A node of type Group is created
        Group group = new Group();
        // A node of type Rectangle is created
        Rectangle r = new Rectangle(25,25,50,50);
        r.setFill(Color.BLUE);
        group.getChildren().add(r);

        // A node of type Circle is created
        Circle c = new Circle(200,200,50, Color.web("blue", 0.5f));
        group.getChildren().add(c);

        // The group of nodes is added to the scene
        scene.setRoot(group);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}