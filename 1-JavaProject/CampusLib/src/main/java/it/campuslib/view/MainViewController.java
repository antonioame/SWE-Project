/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.campuslib.view;

import java.net.URL;
import java.util.ResourceBundle;
import static javafx.application.Platform.exit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * FXML Controller class
 *
 * @author ecoll
 */
public class MainViewController implements Initializable {

    @FXML
    private Label lastSaveLabel;
    @FXML
    private Button btnExit;
    @FXML
    private TabPane tabLoans;
    @FXML
    private Tab tabGiveback;
    @FXML
    private Tab tabBooks;
    @FXML
    private Tab tabUsers;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void exitApp(ActionEvent event) {
        exit();
    }



}
