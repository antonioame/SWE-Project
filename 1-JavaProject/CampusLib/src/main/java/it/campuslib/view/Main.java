package it.campuslib.view;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Verificare che la cartella per i file di IO esista, altrimenti crearla
        File dataDir = new File("personal-files/io-binary-files");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AccessView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(500);
        primaryStage.setTitle("CampusLib");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
