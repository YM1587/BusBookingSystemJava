package com.busbooking.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import com.busbooking.models.Passenger;
import com.busbooking.utils.SessionManager;

public class DashboardController {
    @FXML private Label usernameLabel;
    @FXML private ImageView profilePicture;
    @FXML private Button searchBusBtn, logoutBtn;

    public void initialize() {
        if (!SessionManager.isLoggedIn()) {
            navigateTo("/views/login.fxml", "Login");
            return;
        }

        loadUserData();
    }

    private void loadUserData() {
        Passenger loggedInUser = SessionManager.getLoggedInUser();
        if (loggedInUser != null) {
            usernameLabel.setText("Welcome, " + loggedInUser.getFirstName() + "!");
//            loadProfilePicture(loggedInUser.getProfileImagePath());
        }
    }

    @FXML
    private void handleSearchBus() {
        navigateTo("/views/bus_search.fxml", "Bus Search");
    }

    @FXML
    private void handleLogout() {
        SessionManager.logout();
        navigateTo("/views/login.fxml", "Login");
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            Stage stage = (Stage) searchBusBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load(), 600, 500);
            stage.setScene(scene);
            stage.setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
