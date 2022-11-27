package com.main;

import com.candy.Candy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import static com.main.Main.worker;

public class createController {

    @FXML
    private TableView<Candy> table;

    @FXML
    private TextField candy;

    @FXML
    private TableColumn<Candy, String> candyName;

    @FXML
    private TextField giftName;

    @FXML
    private Button addButton;

    @FXML
    private TableColumn<Candy, Integer> priceCandy;

    @FXML
    private Button saveButton;

    ObservableList<Candy> list = FXCollections.observableArrayList();

    @FXML
    void initialize() throws SQLException {
        getStore();
        candyName.setCellValueFactory(new PropertyValueFactory<Candy, String>("name"));
        priceCandy.setCellValueFactory(new PropertyValueFactory<Candy, Integer>("price"));
        table.setItems(list);

        addButton.setOnAction(event ->{
            Statement statement = null;
            try {
                statement = worker.connection.createStatement();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String gift = giftName.getText();
            String candyName = candy.getText();
            Random random = new Random();
            int position = random.nextInt(1000000);
            String query = "insert into gifts(name, candy, position) values ('"+ gift + "','" + candyName +"'," + position+")";
            try {
                statement.execute(query);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        saveButton.setOnAction(event ->{
            Stage stage1 = (Stage) saveButton.getScene().getWindow();
            stage1.close();
        });
    }

    private void getStore() throws SQLException {
        Statement statement1 = null;
        try {
            statement1 = worker.connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet res = statement1.executeQuery("select * from candies");
        while (res.next()){
            list.add(new Candy(res.getString(1),0,0,res.getInt(4)));
        }
    }

}