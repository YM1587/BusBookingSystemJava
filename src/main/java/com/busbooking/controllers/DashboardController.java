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
import java.io.File;

public class  DashboardController {
    @FXML private Label usernameLabel;
    @FXML private ImageView profilePicture;
    @FXML private Button searchBusBtn, bookingHistoryBtn, logoutBtn;

    public void initialize() {
        // Check if user is logged in
        if (!SessionManager.isLoggedIn()) {
            navigateTo("/views/login.fxml", "Login");
            return;
        }

        // Load user details
        loadUserData();
    }

    private void loadUserData() {
        Passenger loggedInUser = SessionManager.getLoggedInUser();
        if (loggedInUser != null) {
            usernameLabel.setText("Welcome, " + loggedInUser.getFirstName() + "!");
//            loadProfilePicture(loggedInUser.getProfileImagePath());
        }
    }

//    private void loadProfilePicture(String path) {
//        if (path == null || path.isEmpty()) {
//            path = "/assets/default_avatar.png"; // Ensure a default avatar is used
//        }
//        profilePicture.setImage(new Image(getClass().getResourceAsStream(path)));
//    }

    @FXML
    private void handleSearchBus() {
        navigateTo("/views/bus_search.fxml", "Bus Search");
    }

    @FXML
    private void handleBookingHistory() {
        navigateTo("/views/profile.fxml", "Booking History");
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
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
