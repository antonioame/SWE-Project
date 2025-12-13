/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.campuslib.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ecoll
 */
public class UsersViewController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField idField;
    @FXML
    private TextField emailField;
    @FXML
    private Button btnAddUser;
    @FXML
    private Button btnUserStatus;
    @FXML
    private TableView<?> tableUsers;
    @FXML
    private TableColumn<?, ?> clmName;
    @FXML
    private TableColumn<?, ?> clmSurname;
    @FXML
    private TableColumn<?, ?> clmEmail;
    @FXML
    private TableColumn<?, ?> clmUserLoan;
    @FXML
    private TableColumn<?, ?> clmMaxLoans;
    @FXML
    private TableColumn<?, ?> clmStatus;
    @FXML
    private TextField searchUserField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addUser(ActionEvent event) {
    }

    @FXML
    private void updateUserStatus(ActionEvent event) {
    }

    @FXML
    private void updateName(TableColumn.CellEditEvent<S, T> event) {
    }

    @FXML
    private void updateSurname(TableColumn.CellEditEvent<S, T> event) {
    }

    @FXML
    private void updateEmail(TableColumn.CellEditEvent<S, T> event) {
    }

    @FXML
    private void searchUser(ActionEvent event) {
    }
    
}
