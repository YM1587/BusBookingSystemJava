package com.busbooking.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class PassengerMain extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Dashboard.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setTitle("Passenger Dashboard");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading Dashboard.fxml");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
