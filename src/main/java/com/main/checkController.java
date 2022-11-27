package com.main;

import com.candy.Candy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.main.Main.worker;

public class checkController {

    @FXML
    private TextArea finalPrice;

    @FXML
    private TextArea finalWeight;

    @FXML
    private TableColumn<Candy, String> candyName;

    @FXML
    private Button findButton;

    @FXML
    private TextField giftName;

    @FXML
    private Button okButton;

    @FXML
    private TableView<Candy> table;

    ObservableList<Candy> list = FXCollections.observableArrayList();

    @FXML
    void initialize() throws SQLException {

        findButton.setOnAction(event ->{
            try {
                getGift();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            candyName.setCellValueFactory(new PropertyValueFactory<Candy, String>("name"));
            table.setItems(list);
            try {
                finalWeight.setText(getFinal("weight"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                finalPrice.setText(getFinal("price"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        okButton.setOnAction(event ->{
            Stage stage1 = (Stage) okButton.getScene().getWindow();
            stage1.close();
        });
    }

    public void getGift() throws SQLException {
        list.clear();
        Statement statement = null;
        try {
            statement = worker.connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet res = statement.executeQuery("select * from gifts where name = '" + giftName.getText() +"'");
        while (res.next()){
            list.add(new Candy(res.getString(2),0,0,0));
        }
    }

    private String getFinal(String param) throws SQLException {
        Statement statement = worker.connection.createStatement();
        ResultSet res = statement.executeQuery("select sum(candies." + param + ") from gifts inner join candies on gifts.candy = candies.Name where gifts.name = '" + giftName.getText() + "'");
        String weight = null;
        while (res.next()) {
            weight = res.getString(1);
        }
        return weight;
    }
}