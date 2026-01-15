package com.lady_rose.controller;

import com.lady_rose.db.DBConnection;
import com.lady_rose.dto.Item;
import com.lady_rose.dto.Restaurant_Item;
import com.lady_rose.model.ItemModel;
import com.lady_rose.model.RestaurantItemModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RestaurantItemManageFormController {
    public AnchorPane itemRoot;
    public TextField idTxt;
    public TextField nameTxt;
    public TableView itmTbl;
    public TableColumn columnId;
    public TableColumn columnName;
    public TableColumn columnUnitPrice;
    public Button addBtn;
    public Button removeBtn;
    public Button updateBtn;
    public TextField unitPriceTxt;

    public void initialize() throws SQLException {
        setCellValueFactory();
        getAllItems();
    }
    private void getAllItems() throws SQLException {
        ObservableList<Restaurant_Item> obList = FXCollections.observableArrayList();
        List<Restaurant_Item> itmList = RestaurantItemModel.getAll();

        for (Restaurant_Item itm : itmList) {
            obList.add(new Restaurant_Item(
                    itm.getItem_ID(),
                    itm.getName(),
                    itm.getItemUnitPrice()
            ));
        }
        itmTbl.setItems(obList);
    }
    void setCellValueFactory() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
    }
    public void idSearchOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            List<Item> itemList = ItemModel.searchItem(idTxt.getText());
            if (!itemList.isEmpty()){
                for (Item item : itemList) {
                    idTxt.setText(item.getItemCode());
                    nameTxt.setText(item.getItemDescription());
                    idTxt.setDisable(true);
                }
            }else{
                new Alert(Alert.AlertType.WARNING, "Item Not Found!").showAndWait();
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void addItemOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAdded = RestaurantItemModel.addRestaurantItem(RestaurantItemModel.generateID(), nameTxt.getText());
            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, "Item Added Successfully!").showAndWait();
                DBConnection.getInstance().getConnection().commit();
            } else {
                new Alert(Alert.AlertType.WARNING, "Re-Check Submitted Details!").showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void updateItemOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isAdded = ItemModel.updateItem(idTxt.getText(), nameTxt.getText());
            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, "Item Updated Successfully!").showAndWait();
                DBConnection.getInstance().getConnection().commit();
                idTxt.setDisable(false);
            } else {
                new Alert(Alert.AlertType.WARNING, "Re-Check Submitted Details!").showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public void removeItemOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            Optional<ButtonType> comfirm=new Alert(Alert.AlertType.CONFIRMATION, "Do you want to remove the room?").showAndWait();
            if (comfirm.isPresent()){
                boolean isAdded = ItemModel.removeItem(idTxt.getText());
                if (isAdded) {
                    new Alert(Alert.AlertType.INFORMATION, "Item Removed Successfully!").showAndWait();
                    DBConnection.getInstance().getConnection().commit();
                    idTxt.setDisable(false);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Re-Check Submitted Details!").showAndWait();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
        }finally{
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }
}
