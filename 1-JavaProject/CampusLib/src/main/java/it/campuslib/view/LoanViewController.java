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
public class LoanViewController implements Initializable {

    @FXML
    private TextField startLoanField;
    @FXML
    private TextField returnLoanField;
    @FXML
    private Button btnAddLoan;
    @FXML
    private TableView<?> tableLoan;
    @FXML
    private TableColumn<?, ?> clmIdLoan;
    @FXML
    private TableColumn<?, ?> clmBookLoan;
    @FXML
    private TableColumn<?, ?> clmUserLoan;
    @FXML
    private TableColumn<?, ?> clmStartLoan;
    @FXML
    private TableColumn<?, ?> clmReturnLoan;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addLoan(ActionEvent event) {
    }

    @FXML
    private void updateExptGbLoan(TableColumn.CellEditEvent<S, T> event) {
    }
    
}
