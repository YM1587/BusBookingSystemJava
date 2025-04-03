package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class BusSearchController {

    @FXML
    private ComboBox<String> fromComboBox;

    @FXML
    private ComboBox<String> toComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button searchButton;

    private final List<String> cities = Arrays.asList("New York", "Los Angeles", "Chicago", "Houston", "Miami");

    @FXML
    public void initialize() {
        fromComboBox.getItems().addAll(cities);
        toComboBox.getItems().addAll(cities);

        // Ensure past dates are disabled
        datePicker.setValue(LocalDate.now());
        datePicker.setDayCellFactory(picker -> new javafx.scene.control.DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now()));
            }
        });

        searchButton.setOnAction(event -> handleSearch());
    }

    private void handleSearch() {
        String fromCity = fromComboBox.getValue();
        String toCity = toComboBox.getValue();
        LocalDate selectedDate = datePicker.getValue();

        if (fromCity == null || toCity == null || selectedDate == null) {
            System.out.println("Please select all fields before proceeding.");
            return;
        }

        System.out.println("Searching for buses from " + fromCity + " to " + toCity + " on " + selectedDate);
        navigateToBusSelection(fromCity, toCity, selectedDate);
    }

    private void navigateToBusSelection(String from, String to, LocalDate date) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/bus_selection.fxml"));
            Parent root = loader.load();

            // Pass data to BusSelectionController
            BusSelectionController controller = loader.getController();
            controller.setRouteDetails(from, to, date);

            Stage stage = (Stage) searchButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
