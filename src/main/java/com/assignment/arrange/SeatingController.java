package com.assignment.arrange;




import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;

public class SeatingController {
    @FXML
    private Rectangle seat00;
    @FXML
    private Rectangle seat01;
    @FXML
    private Rectangle seat02;
    @FXML
    private Rectangle seat10;
    @FXML
    private Rectangle seat11;
    @FXML
    private Rectangle seat12;
    @FXML
    private Rectangle seat20;
    @FXML
    private Rectangle seat21;
    @FXML
    private Rectangle seat22;

    @FXML
    private Label label00;
    @FXML
    private Label label01;
    @FXML
    private Label label02;
    @FXML
    private Label label10;
    @FXML
    private Label label11;
    @FXML
    private Label label12;
    @FXML
    private Label label20;
    @FXML
    private Label label21;
    @FXML
    private Label label22;

    @FXML
    private TextField nameInput;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Button addButton;

    private Map<Rectangle, String> assignedSeats = new HashMap<>(); // Track assigned seats and student names
    private Set<Color> assignedColors = new HashSet<>();


    @FXML
    public void initialize() {
        addButton.setOnAction(event -> addStudent());
    }

    @FXML
    private void addStudent() {
        String studentName = nameInput.getText();
        Color studentColor = colorPicker.getValue();

        if (studentName.isEmpty()) {
            showErrorAlert("Please enter a student name.");
            return;
        }

        if (assignedSeats.containsValue(studentName)) {
            showErrorAlert("Student name already assigned.");
            return;
        }

        if (assignedColors.contains(studentColor)) {
            showErrorAlert("Color already chosen.");
            return;
        }

        Rectangle selectedSeat = getAvailableSeat();
        if (selectedSeat == null) {
            showErrorAlert("All seats are occupied.");
            return;
        }

        selectedSeat.setFill(studentColor);
        assignedSeats.put(selectedSeat, studentName);
        assignedColors.add(studentColor);

        Label seatLabel = getLabelForSeat(selectedSeat);
        if (seatLabel != null) {
            seatLabel.setText(studentName);
        }

        nameInput.clear();
        colorPicker.setValue(Color.WHITE);
    }

    private Rectangle getAvailableSeat() {
        Rectangle[] seatRectangles = { seat00, seat01, seat02, seat10, seat11, seat12, seat20, seat21, seat22 };
        List<Rectangle> availableSeats = new ArrayList<>();

        for (Rectangle seat : seatRectangles) {
            if (!assignedSeats.containsKey(seat)) {
                availableSeats.add(seat);
            }
        }

        if (availableSeats.isEmpty()) {
            return null;
        }

        if (availableSeats.size() == 1) {
            return availableSeats.get(0);
        }

        List<Rectangle> unchosenSeats =  new ArrayList<>(availableSeats);
        unchosenSeats.removeAll(assignedSeats.keySet());

        Rectangle randomlyChosenSeat = unchosenSeats.get(new Random().nextInt(unchosenSeats.size()));
        return randomlyChosenSeat;
    }

    private Label getLabelForSeat(Rectangle seat) {
        if (seat == seat00) return label00;
        if (seat == seat01) return label01;
        if (seat == seat02) return label02;
        if (seat == seat10) return label10;
        if (seat == seat11) return label11;
        if (seat == seat12) return label12;
        if (seat == seat20) return label20;
        if (seat == seat21) return label21;
        if (seat == seat22) return label22;
        return null;
    }
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


