package com.main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class infoController {

    @FXML
    private Button okButton;

    @FXML
    void initialize() {
        okButton.setOnAction(event ->{
            Stage stage1 = (Stage) okButton.getScene().getWindow();
            // do what you have to do
            stage1.close();
        });
    }
}
