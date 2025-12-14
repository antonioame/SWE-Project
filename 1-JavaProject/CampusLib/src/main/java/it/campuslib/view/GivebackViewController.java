/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.campuslib.view;

import it.campuslib.domain.catalog.Book;
import it.campuslib.domain.transactions.Giveback;
import it.campuslib.domain.users.User;
import java.net.URL;
import java.time.LocalDate;
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
public class GivebackViewController implements Initializable {

    @FXML
    private TextField returnDateField;
    @FXML
    private Button btnAddGb;
    @FXML
    private TableView<Giveback> tableGiveback;
    @FXML
    private TableColumn<Giveback, Integer> clmIdGb;
    @FXML
    private TableColumn<Giveback, Book> clmBookGb;
    @FXML
    private TableColumn<Giveback, User> clmUserGb;
    @FXML
    private TableColumn<Giveback, LocalDate> clmStartGb;
    @FXML
    private TableColumn<Giveback, LocalDate> clmReturnGb;
    
    private ObservableList<Giveback> gbList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    

    @FXML
    private void addGiveBack(ActionEvent event) {
    }
    
}
