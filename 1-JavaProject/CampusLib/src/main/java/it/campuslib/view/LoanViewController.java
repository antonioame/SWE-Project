package it.campuslib.view;

import it.campuslib.domain.catalog.AdoptionStatus;
import it.campuslib.domain.users.UserStatus;
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
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import java.util.Optional;
import it.campuslib.domain.users.InvalidUserInfoException;
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
    private ObservableList<Loan> allLoans;
    private LoanRegistry loanRegistry;
    private GivebackRegistry givebackRegistry;
    @FXML
    private TextField searchField;
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

    private void filterLoans() {
        String q = searchField.getText().trim().toLowerCase();
        if (q.isEmpty()) {
            tableLoan.setItems(allLoans);
            return;
        }

        ObservableList<Loan> filtered = FXCollections.observableArrayList();
        for (Loan l : allLoans) {
            if (l == null) continue;
            boolean match = false;
            
            if (String.valueOf(l.getId()).contains(q)) match = true;
            
            Book b = l.getBorrowedBook();
            if (!match && b != null) {
                if (b.getIsbn() != null && b.getIsbn().toLowerCase().contains(q)) match = true;
                if (!match && b.getTitle() != null && b.getTitle().toLowerCase().contains(q)) match = true;
            }
            
            User u = l.getBorrowerUser();
            if (!match && u != null) {
                if (u.getEnrollmentID() != null && u.getEnrollmentID().toLowerCase().contains(q)) match = true;
                if (!match && u.getName() != null && u.getName().toLowerCase().contains(q)) match = true;
                if (!match && u.getSurname() != null && u.getSurname().toLowerCase().contains(q)) match = true;
            }
            
            LocalDate sd = l.getStartDate();
            LocalDate ed = l.getExpectedReturnDate();
            if (!match && sd != null && sd.toString().contains(q)) match = true;
            if (!match && ed != null && ed.toString().contains(q)) match = true;

            if (match) filtered.add(l);
        }
        tableLoan.setItems(filtered);
    }

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
        
        allUsers = userRegistry.getAllUsersObservable();
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
        allLoans = FXCollections.observableArrayList(loanRegistry.getRegistry());
        loans = allLoans;

        SortedList<Loan> sortedLoans = new SortedList<>(allLoans);
        sortedLoans.comparatorProperty().bind(tableLoan.comparatorProperty());
        tableLoan.setItems(sortedLoans);

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

        clmStartLoan.setSortable(true);
        clmReturnLoan.setSortable(true);

        if (tableLoan.getSortOrder().isEmpty()) {
            tableLoan.getSortOrder().add(clmReturnLoan);
            tableLoan.sort();
        }

        java.time.LocalDate today = java.time.LocalDate.now();
        startLoanLabel.setText(today.toString());
        returnLoanField.setText(today.plusDays(20).toString());

        if (searchField != null) {
            searchField.textProperty().addListener((obs, oldV, newV) -> filterLoans());
        }

        if (loanRegistry != null && loanRegistry.getRegistry() != null) {
            loanRegistry.getRegistry().addListener((javafx.collections.SetChangeListener<Loan>) change -> {
                allLoans.setAll(loanRegistry.getRegistry());
                filterLoans();
            });
        }
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
        // Verificare che l'utente sia attivo
        if (selectedUser.getStatus() != UserStatus.ACTIVE) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Utente Inattivo");
            alert.setHeaderText("Impossibile registrare prestito");
            alert.setContentText("L'utente selezionato è contrassegnato come inattivo.");
            alert.showAndWait();
            return;
        }
        // Verificare che il libro sia in adozione
        if (!selectedBook.isAdopted()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Libro Non Disponibile");
            alert.setHeaderText("Impossibile registrare prestito");
            alert.setContentText("Il libro selezionato non è in adozione e non è possibile registrare nuovi presiti associati.");
            alert.showAndWait();
            return;
        }
        // Verificare che l'utente non abbia già raggiunto il numero massimo di prestiti attivi
        if (loanRegistry != null && !selectedUser.canBorrow(loanRegistry)) {
            int max = selectedUser.getMaxLoans();
            int current = selectedUser.getActiveLoans(loanRegistry).size();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Limite Prestiti Raggiunto");
            alert.setHeaderText("Impossibile registrare prestito");
            alert.setContentText("L'utente ha già " + current + " prestiti attivi (max personale: " + max + ") e non può prenderne altri.");
            alert.showAndWait();
            return;
        }
        
        try {
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate returnDate = LocalDate.parse(returnDateStr);

            Loan loan = new Loan(selectedBook, selectedUser, startDate, returnDate);
            if (loanRegistry.addLoan(loan)) {
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
        java.time.LocalDate expected = selected.getExpectedReturnDate();
        java.time.LocalDate today = java.time.LocalDate.now();

        if (expected != null && expected.isBefore(today)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Restituzione in Ritardo");
            alert.setHeaderText("Data prevista di restituzione antecedente alla data odierna");
            alert.setContentText("Vuoi ridurre di 1 il numero massimo di prestiti per l'utente?");

            ButtonType reduceBtn = new ButtonType("Riduci Numero di Prestiti per Utente", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelBtn = new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(reduceBtn, cancelBtn);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == reduceBtn) {
                User borrower = selected.getBorrowerUser();
                if (borrower != null) {
                    int cur = borrower.getMaxLoans();
                    int next = Math.max(0, cur - 1);
                    try {
                        borrower.setMaxLoans(next);
                        userRegistry.exportOnFile("personal-files/io-binary-files/users.dat");
                        allUsers.setAll(userRegistry.getAllUsers());
                    } catch (InvalidUserInfoException ex) {
                        // L'eccezione mostrerà il pop-up
                    }
                }
            }
        }

        Giveback gb = loanRegistry.pullAsGiveback(selected);
        if (gb != null) {
            givebackRegistry.addGiveback(gb);
            loanRegistry.exportOnFile("personal-files/io-binary-files/loans.dat");
            givebackRegistry.exportOnFile("personal-files/io-binary-files/givebacks.dat");

            allLoans.remove(selected);
            tableLoan.refresh();

            btnRegisterReturn.setDisable(true);
        }
    }
}
