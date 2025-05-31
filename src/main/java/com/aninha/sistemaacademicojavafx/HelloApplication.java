package com.aninha.sistemaacademicojavafx;

import com.aninha.sistemaacademicojavafx.modelo.Professor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        String cssPath = getClass().getResource("visao/css/styles.css").toExternalForm();
        scene.getStylesheets().add(cssPath);

        stage.setTitle("sistema academico");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}