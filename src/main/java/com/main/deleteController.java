package com.main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.sql.Statement;

import static com.main.Main.worker;

public class deleteController {

    @FXML
    private Button deleteButton;

    @FXML
    private TextField giftName;

    @FXML
    private Button okButton;

    @FXML
    void initialize() throws SQLException{
        deleteButton.setOnAction(event -> {
            Statement statement = null;
            try {
                statement = worker.connection.createStatement();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String query = "delete from gifts where name = '"+giftName.getText() +"'";
            try {
                statement.execute(query);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });
        okButton.setOnAction(event ->{
            Stage stage1 = (Stage) okButton.getScene().getWindow();
            stage1.close();
        });
    }
}
