package com.busbooking.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class PassengerMain extends Application {
    private static Stage primaryStage;

   /* @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/register.fxml"));
        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Register Form");
        primaryStage.show();
    }*/

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        loadLoginScreen();
    }

    public static void loadLoginScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PassengerMain.class.getResource("/views/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(),600,500);

            primaryStage.setTitle("Passenger Login");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading login.fxml");
        }
    }

    public static void loadDashboardScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PassengerMain.class.getResource("/views/dashboard.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            primaryStage.setTitle("Passenger Dashboard");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading dashboard.fxml");
        }
    }

    public static void loadRegisterScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PassengerMain.class.getResource("/views/register.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            primaryStage.setTitle("Passenger Registration");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading register.fxml");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
//package com.busbooking.ui;
//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import java.io.IOException;
//
//public class PassengerMain extends Application {
//    private static Stage primaryStage;
//
//    @Override
//    public void start(Stage stage) {
//        primaryStage = stage;
//        loadBusSearchScreen();  // 👈 Temporarily loading bus_search.fxml
//    }
//
//    public static void loadBusSearchScreen() {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(PassengerMain.class.getResource("/views/bus_search.fxml"));
//            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
//
//            primaryStage.setTitle("Bus Search");
//            primaryStage.setScene(scene);
//            primaryStage.setResizable(false);
//            primaryStage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Error loading bus_search.fxml");
//        }
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
