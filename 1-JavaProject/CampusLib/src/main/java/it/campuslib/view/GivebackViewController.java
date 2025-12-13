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
public class GivebackViewController implements Initializable {

    @FXML
    private TextField returnDateField;
    @FXML
    private Button btnAddGb;
    @FXML
    private TableView<?> tableGiveback;
    @FXML
    private TableColumn<?, ?> clmIdGb;
    @FXML
    private TableColumn<?, ?> clmBookGb;
    @FXML
    private TableColumn<?, ?> clmUserGb;
    @FXML
    private TableColumn<?, ?> clmStartGb;
    @FXML
    private TableColumn<?, ?> clmReturnGb;

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
