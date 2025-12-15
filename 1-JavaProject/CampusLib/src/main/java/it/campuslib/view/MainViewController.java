package it.campuslib.view;

import it.campuslib.collections.BookCatalog;
import it.campuslib.collections.UserRegistry;
import it.campuslib.collections.LoanRegistry;
import it.campuslib.collections.GivebackRegistry;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import static javafx.application.Platform.exit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MainViewController implements Initializable {

    @FXML
    private Label lastSaveLabel;
    @FXML
    private Button btnReloadState;
    @FXML
    private Button btnSaveState;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        lastSaveLabel.setText("App avviata: " + now.format(formatter));
    }

    @FXML
    private void exitApp(ActionEvent event) {
        exit();
    }

    @FXML
    private void saveState(ActionEvent event) {
        try {
            BookCatalog.getInstance().exportOnFile("personal-files/io-binary-files/books.dat");
            UserRegistry.getInstance().exportOnFile("personal-files/io-binary-files/users.dat");
            LoanRegistry.getInstance().exportOnFile("personal-files/io-binary-files/loans.dat");
            GivebackRegistry.getInstance().exportOnFile("personal-files/io-binary-files/givebacks.dat");

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            lastSaveLabel.setText("Stato: Ultimo salvataggio " + now.format(formatter));
        } catch (Exception e) {
            lastSaveLabel.setText("Errore durante il salvataggio");
        }
    }

    @FXML
    private void reloadState(ActionEvent event) {
        try {
            BookCatalog reloadedBooks = BookCatalog.importFromFile("personal-files/io-binary-files/books.dat");
            UserRegistry reloadedUsers = UserRegistry.importFromFile("personal-files/io-binary-files/users.dat");
            LoanRegistry reloadedLoans = LoanRegistry.importFromFile("personal-files/io-binary-files/loans.dat");
            GivebackRegistry reloadedGivebacks = GivebackRegistry.importFromFile("personal-files/io-binary-files/givebacks.dat");

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            lastSaveLabel.setText("Stato: Ricaricato con successo " + now.format(formatter));
        } catch (Exception e) {
            lastSaveLabel.setText("Errore durante il ricaricamento");
        }
    }
}
