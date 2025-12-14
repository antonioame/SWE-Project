/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.campuslib.view;

import it.campuslib.domain.transactions.Loan;
import it.campuslib.domain.users.User;
import it.campuslib.domain.users.UserStatus;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class UserViewController implements Initializable {

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
    private TextField searchUserField;
    @FXML
    private TableView<User> tableUsers;
    @FXML
    private TableColumn<User, String> clmName;
    @FXML
    private TableColumn<User, String> clmSurname;
    @FXML
    private TableColumn<User, String> clmEmail;
    @FXML
    private TableColumn<User, Loan> clmUserLoan;
    @FXML
    private TableColumn<User, Integer> clmMaxLoans;
    @FXML
    private TableColumn<User, UserStatus> clmStatus;
    
    private ObservableList<User> userList;

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
    private void searchUser(ActionEvent event) {
    }

    @FXML
    private void updateName(TableColumn.CellEditEvent<User, String> event) {
    }

    @FXML
    private void updateSurname(TableColumn.CellEditEvent<User, String> event) {
    }

    @FXML
    private void updateEmail(TableColumn.CellEditEvent<User, String> event) {
    }
    
}
