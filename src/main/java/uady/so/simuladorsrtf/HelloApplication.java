package uady.so.simuladorsrtf;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 604, 522);
        stage.setTitle("Simulador de algoritmo SRJF");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
