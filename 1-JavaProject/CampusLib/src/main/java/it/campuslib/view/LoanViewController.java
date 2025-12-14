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
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import it.campuslib.collections.LoanRegistry;
import it.campuslib.collections.GivebackRegistry;
import it.campuslib.domain.transactions.Giveback;
import javafx.collections.ObservableSet;

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
    private LoanRegistry loanRegistry;
    private GivebackRegistry givebackRegistry;
    @FXML
    private Button btnRegisterReturn;

    @FXML
    private ComboBox<Book> bookCombo;
    @FXML
    private ComboBox<User> userCombo;
    @FXML
    private Label startLoanLabel;
    @FXML
    private TextField returnLoanField;
    @FXML
    private Button btnAddLoan;
    @FXML
    private TableView<Loan> tableLoan;
    @FXML
    private TableColumn<Loan, Integer> clmIdLoan;
    @FXML
    private TableColumn<Loan, String> clmBookLoan;
    @FXML
    private TableColumn<Loan, String> clmUserLoan;
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
        
        allUsers = FXCollections.observableArrayList(userRegistry.getAllUsers());
        filteredUsers = allUsers;
        userCombo.setItems(allUsers);
        userCombo.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return user != null ? (user.getName() + " " + user.getSurname() + " - " + user.getEnrollmentID()) : "";
            }

            @Override
            public User fromString(String string) {
                return null;
            }
        });
        
        loans = FXCollections.observableArrayList();
        loanRegistry = LoanRegistry.getInstance();
        givebackRegistry = GivebackRegistry.getInstance();
        loans = FXCollections.observableArrayList(loanRegistry.getRegistry());
        tableLoan.setItems(loans);

        tableLoan.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            btnRegisterReturn.setDisable(newSel == null);
        });

        clmIdLoan.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));
        clmBookLoan.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
        cellData.getValue().getBorrowedBook().getIsbn() + " - " + cellData.getValue().getBorrowedBook().getTitle()));
        clmUserLoan.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
        cellData.getValue().getBorrowerUser().getEnrollmentID() + " - " +
        cellData.getValue().getBorrowerUser().getSurname() + " " + cellData.getValue().getBorrowerUser().getName()));
        clmStartLoan.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getStartDate()));
        clmReturnLoan.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getExpectedReturnDate()));

        java.time.LocalDate today = java.time.LocalDate.now();
        startLoanLabel.setText(today.toString());
        returnLoanField.setText(today.plusDays(20).toString());
    }    

    @FXML
    private void addLoan(ActionEvent event) {
        Book selectedBook = bookCombo.getValue();
        User selectedUser = userCombo.getValue();
        String startDateStr = startLoanLabel.getText().trim();
        String returnDateStr = returnLoanField.getText().trim();
        
        if (selectedBook == null || selectedUser == null || startDateStr.isEmpty() || returnDateStr.isEmpty()) {
            return;
        }
        
        try {
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate returnDate = LocalDate.parse(returnDateStr);

            Loan loan = new Loan(selectedBook, selectedUser, startDate, returnDate);
            if (loanRegistry.addLoan(loan)) {
                loans.add(loan);
                loanRegistry.exportOnFile("personal-files/io-binary-files/loans.dat");

                bookCombo.setValue(null);
                userCombo.setValue(null);
                java.time.LocalDate now = java.time.LocalDate.now();
                startLoanLabel.setText(now.toString());
                returnLoanField.setText(now.plusDays(20).toString());
            }
        } catch (Exception e) {
            return;
        }
    }

    @FXML
    private void registerReturn(ActionEvent event) {
        Loan selected = tableLoan.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        Giveback gb = loanRegistry.pullAsGiveback(selected);
        if (gb != null) {
            givebackRegistry.addGiveback(gb);
            loanRegistry.exportOnFile("personal-files/io-binary-files/loans.dat");
            givebackRegistry.exportOnFile("personal-files/io-binary-files/givebacks.dat");

            loans.remove(selected);
            tableLoan.refresh();

            btnRegisterReturn.setDisable(true);
        }
    }
}
