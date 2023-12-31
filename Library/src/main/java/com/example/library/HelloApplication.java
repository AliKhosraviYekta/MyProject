package com.example.library;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SplashScreen.class.getResource("splashscreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        Thread delayThread = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                stage.close();
                try {
                    FXMLLoader fxmlLoader1 = new FXMLLoader(MainMenu.class.getResource("main.fxml"));
                    Scene scene1 = new Scene(fxmlLoader1.load());
                    Stage stage1 = new Stage();
                    stage1.setResizable(false);
                    Image icon = new Image(getClass().getResourceAsStream("/images/Mind2.jpg"));
                    stage1.getIcons().add(icon);
                    stage1.setTitle("Library");
                    stage1.setScene(scene1);
                    stage1.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

        delayThread.start();
    }

    public static void main(String[] args) {
        launch();
    }
}
