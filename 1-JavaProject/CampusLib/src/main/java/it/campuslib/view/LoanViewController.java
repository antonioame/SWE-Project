/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.campuslib.view;

import it.campuslib.domain.catalog.Book;
import it.campuslib.domain.transactions.Loan;
import it.campuslib.domain.users.User;
import java.net.URL;
import java.time.LocalDate;
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
    private TableView<Loan> tableLoan;
    @FXML
    private TableColumn<Loan, Integer> clmIdLoan;
    @FXML
    private TableColumn<Loan, Book> clmBookLoan;
    @FXML
    private TableColumn<Loan, User> clmUserLoan;
    @FXML
    private TableColumn<Loan, LocalDate> clmStartLoan;
    @FXML
    private TableColumn<Loan, LocalDate> clmReturnLoan;

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
    private void updateExptGbLoan(TableColumn.CellEditEvent<Loan, LocalDate> event) {
    }
    
}
