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

/**
 * FXML Controller class
 *
 * @author ecoll
 */
public class MainViewController implements Initializable {

    @FXML
    private Label lastSaveLabel;
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

    @FXML
    private void saveState(ActionEvent event) {
        try {
            // Salva tutti i catalog
            BookCatalog.getInstance().exportOnFile("personal-files/io-binary-files/books.dat");
            UserRegistry.getInstance().exportOnFile("personal-files/io-binary-files/users.dat");
            LoanRegistry.getInstance().exportOnFile("personal-files/io-binary-files/loans.dat");
            GivebackRegistry.getInstance().exportOnFile("personal-files/io-binary-files/givebacks.dat");
            
            // Aggiorna il label con l'ora del salvataggio
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            lastSaveLabel.setText("Stato: Ultimo salvataggio " + now.format(formatter));
        } catch (Exception e) {
            lastSaveLabel.setText("Errore durante il salvataggio");
        }
    }



}
