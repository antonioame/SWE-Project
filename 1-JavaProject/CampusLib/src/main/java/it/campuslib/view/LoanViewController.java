/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.campuslib.view;

import it.campuslib.domain.catalog.AdoptionStatus;
import it.campuslib.domain.catalog.Book;
import it.campuslib.domain.transactions.Loan;
import it.campuslib.domain.users.User;
import it.campuslib.collections.BookCatalog;
import it.campuslib.collections.UserRegistry;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author ecoll
 */
public class LoanViewController implements Initializable {

    private BookCatalog bookCatalog;
    private UserRegistry userRegistry;
    private ObservableList<Book> allBooks;
    private ObservableList<User> allUsers;
    private ObservableList<User> filteredUsers;
    private ObservableList<Loan> loans;

    @FXML
    private ComboBox<Book> bookCombo;
    @FXML
    private ComboBox<User> userCombo;
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
        bookCatalog = BookCatalog.getInstance();
        userRegistry = UserRegistry.getInstance();
        
        allBooks = bookCatalog.getAllBooks();
        bookCombo.setItems(allBooks);
        bookCombo.setConverter(new StringConverter<Book>() {
            @Override
            public String toString(Book book) {
                return book != null ? book.getDisplayString() : "";
            }

            @Override
            public Book fromString(String string) {
                return null;
            }
        });
        
        allUsers = FXCollections.observableArrayList();
        filteredUsers = FXCollections.observableArrayList(allUsers);
        userCombo.setItems(filteredUsers);
        
        loans = FXCollections.observableArrayList();
        tableLoan.setItems(loans);
    }    

    @FXML
    private void addLoan(ActionEvent event) {
        Book selectedBook = bookCombo.getValue();
        User selectedUser = userCombo.getValue();
        String startDateStr = startLoanField.getText().trim();
        String returnDateStr = returnLoanField.getText().trim();
        
        if (selectedBook == null || selectedUser == null || startDateStr.isEmpty() || returnDateStr.isEmpty()) {
            return;
        }
        
        try {
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate returnDate = LocalDate.parse(returnDateStr);
            
            bookCombo.setValue(null);
            userCombo.setValue(null);
            startLoanField.clear();
            returnLoanField.clear();
            
        } catch (Exception e) {
        }
    }
}
