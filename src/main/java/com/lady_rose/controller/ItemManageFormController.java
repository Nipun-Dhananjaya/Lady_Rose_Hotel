package com.lady_rose.controller;

import com.lady_rose.db.DBConnection;
import com.lady_rose.dto.Item;
import com.lady_rose.model.ItemModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ItemManageFormController {
    public AnchorPane itemRoot;
    public TextField idTxt;
    public TextField nameTxt;
    public TableView itmTbl;
    public TableColumn columnId;
    public TableColumn columnName;
    public Button addBtn;
    public Button updateBtn;
    public Button removeBtn;
    public TableColumn columnQty;
    public TextField qtyTxt;
    public Button addQtyBtn;
    public Label qtyOnHandLbl;

    public void initialize() throws SQLException {
        setCellValueFactory();
        getAllItems();
    }
    private void getAllItems() throws SQLException {
        ObservableList<Item> obList = FXCollections.observableArrayList();
        List<Item> itmList = ItemModel.getAll();

        for (Item itm : itmList) {
            obList.add(new Item(
                    itm.getItem_ID(),
                    itm.getName(),
                    itm.getQtyOnHand()
            ));
        }
        itmTbl.setItems(obList);
    }
    void setCellValueFactory() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("item_ID"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
    }
    public void idSearchOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            List<Item> itemList = ItemModel.searchItem(idTxt.getText());
            if (!itemList.isEmpty()){
                for (Item item : itemList) {
                    idTxt.setText(item.getItem_ID());
                    nameTxt.setText(item.getName());
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
            boolean isAdded = ItemModel.addItem(ItemModel.generateID(), nameTxt.getText(),Double.parseDouble(qtyOnHandLbl.getText()));
            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, "Item Added Successfully!").showAndWait();
                resetPage();
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
            boolean isAdded = ItemModel.updateItem(idTxt.getText(), nameTxt.getText(),Double.parseDouble(qtyOnHandLbl.getText()));
            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, "Item Updated Successfully!").showAndWait();
                DBConnection.getInstance().getConnection().commit();
                idTxt.setDisable(false);
                resetPage();
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
                    resetPage();
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

    public void addQtyOnAction(ActionEvent actionEvent) {
        qtyOnHandLbl.setText(String.valueOf(Double.parseDouble(qtyOnHandLbl.getText())+
                Double.parseDouble(qtyTxt.getText())));
        addBtn.setDisable(true);
    }


    public void resetPage() throws SQLException {
        idTxt.setText("");
        nameTxt.setText("");
        qtyOnHandLbl.setText("0.0");
        qtyTxt.setText("");
        addBtn.setDisable(false);
        setCellValueFactory();
        getAllItems();
    }
}
