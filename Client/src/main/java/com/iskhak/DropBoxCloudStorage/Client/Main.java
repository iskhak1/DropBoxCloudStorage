package com.iskhak.DropBoxCloudStorage.Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/UI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),720,512);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
